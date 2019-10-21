package com.bondex.ysl.battledore.login

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import android.util.Log
import com.baidu.location.*
import com.bondex.ysl.battledore.application.BattleApplication
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.HttpConnection
import com.bondex.ysl.battledore.util.SharedPreferecneUtils
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.HttpResultBack
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.databaselibrary.airport.AirPort
import com.bondex.ysl.databaselibrary.login.LoginBean
import com.bondex.ysl.databaselibrary.login.LoginDao
import com.bondex.ysl.databaselibrary.login.LoginDataBase
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import org.json.JSONArray
import org.json.JSONObject

/**
 * date: 2019/7/19
 * Author: ysl
 * description:
 */

class LoginViewModel : BaseViewModle() {

    var mLocationClient: LocationClient? = null
    val loginBeanData: MutableLiveData<LoginBean> = MutableLiveData()
    val loginModel: LoginModel = LoginModel()
    val airportLiveData = MutableLiveData<List<AirPort>>()

    override fun onCreate() {

        loginModel.readData(context, object : ModelCallback<LoginBean> {

            override fun onSuccess(data: LoginBean?) {

                Constant.CITY = SharedPreferecneUtils.getValue(context, Constant.CITY_KEY)
                Constant.AIR_CODE = SharedPreferecneUtils.getValue(context, Constant.AIR_CODE_KEY)

                if (data != null) {
                    setLoginLiveData(data)
                }


            }

            override fun onFaile(msg: String?) {

            }
        })

    }

    private var isLocated = false
    fun getAirPortOld(city: String) {


        loginModel.getAirport(context, city, object : ModelCallback<List<AirPort>> {

            override fun onSuccess(data: List<AirPort>?) {

                airportLiveData.value = data
            }

            override fun onFaile(msg: String?) {

            }

        })
    }


    fun login(name: String, psw: String, shouldInsert: Boolean) {

        val loginParam: LoginParam = LoginParam(name, psw)

        setRefresh(true)
        loginModel.iniData(loginParam, object : ModelCallback<String> {
            override fun onSuccess(data: String?) {


                val ob = JSONObject(data)
                var msg: String? = null
                var data: String? = null

                val isSuccess = ob.getBoolean("success")
                if (ob.has("errormsg")) msg = ob.getString("errormsg")
                if (ob.has("message")) data = ob.getString("message")

                if (isSuccess) {

                    var bean: LoginBean? = null
                    val array = JSONArray(data)

                    val gson = Gson()

                    for (i in 0 until array.length()) {

                        val child = array.getJSONObject(i)


                        if (child.has("opids")) {

                            val opids = child.getJSONObject("opids")
                            val opid = opids.keys().next()

                            bean = gson.fromJson<Any>(child.toString(), LoginBean::class.java) as LoginBean?

                            bean?.opid = opid


                            if (bean != null) {

                                bean.isLogin = true
                                bean.isShouldRember = shouldInsert
                                if (shouldInsert) bean.psw = psw
                                bean.loginDate = System.currentTimeMillis()
                                insert(bean)
                                setLoginLiveData(bean)
                            }

                        }
                    }

                } else {
                    ToastUtils.showShort(msg)
                }

                setRefresh(false)
            }

            override fun onFaile(msg: String?) {
                setRefresh(false)

                ToastUtils.showShort(msg)
            }

        })
    }

    fun insert(bean: LoginBean) {


        val thread: Thread = Thread(object : Runnable {
            override fun run() {

                val loginDao: LoginDao = LoginDataBase.getInstance(context).dao

                if (loginDao.check(bean.psncode) != null) {

                    loginDao.update(bean)
                } else {
                    loginDao.insert(bean)
                }

                Logger.i("login insert " + bean.psnname)
            }
        })

        thread.isDaemon = true
        thread.start()
    }


    fun setLoginLiveData(bean: LoginBean) {


        loginBeanData.postValue(bean)
    }

    fun getLoginLiveData(): MutableLiveData<LoginBean> {

        return loginBeanData
    }

    fun getLocation() {

        mLocationClient = LocationClient(BattleApplication.CONTEXT)

        mLocationClient?.registerLocationListener(mListener)

        val option = LocationClientOption()
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy)

        //可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll")
//可选，设置返回经纬度坐标类型，默认GCJ02
//GCJ02：国测局坐标；
//BD09ll：百度经纬度坐标；
//BD09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(30000)
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true)
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setLocationNotify(true)
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIsNeedAddress(true)

        option.setIgnoreKillProcess(true);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(true)
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000)
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false)
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient?.setLocOption(option)
        mLocationClient?.start()

    }

    override fun onResume() {
    }

    override fun onStart() {

    }


    override fun onStop() {
    }

    override fun onDestroy() {
        mLocationClient?.stop()
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private val mListener = object : BDAbstractLocationListener() {

        override fun onReceiveLocation(location: BDLocation?) {


            // TODO Auto-generated method stub
            if (null != location && location.locType != BDLocation.TypeServerError) {
                val sb = StringBuffer(256)
                sb.append("time : ")
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.time)
                sb.append("\nlocType : ")// 定位类型
                sb.append(location.locType)
                sb.append("\nlocType description : ")// *****对应的定位类型说明*****
                sb.append(location.locTypeDescription)
                sb.append("\nlatitude : ")// 纬度
                sb.append(location.latitude)
                sb.append("\nlontitude : ")// 经度
                sb.append(location.longitude)
                sb.append("\nradius : ")// 半径
                sb.append(location.radius)
                sb.append("\nCountryCode : ")// 国家码
                sb.append(location.countryCode)
                sb.append("\nCountry : ")// 国家名称
                sb.append(location.country)
                sb.append("\ncitycode : ")// 城市编码
                sb.append(location.cityCode)
                sb.append("\ncity : ")// 城市
                sb.append(location.city)
                sb.append("\nDistrict : ")// 区
                sb.append(location.district)
                sb.append("\nStreet : ")// 街道
                sb.append(location.street)
                sb.append("\naddr : ")// 地址信息
                sb.append(location.addrStr)
                sb.append("\nUserIndoorState: ")// *****返回用户室内外判断结果*****
                sb.append(location.userIndoorState)
                sb.append("\nDirection(not all devices have value): ")
                sb.append(location.direction)// 方向
                sb.append("\nlocationdescribe: ")
                sb.append(location.locationDescribe)// 位置语义化信息
                sb.append("\nPoi: ")// POI信息
                if (location.poiList != null && !location.poiList.isEmpty()) {
                    for (i in 0 until location.poiList.size) {
                        val poi = location.poiList[i] as Poi
                        sb.append(poi.name + ";")
                    }
                }
                if (location.locType == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ")
                    sb.append(location.speed)// 速度 单位：km/h
                    sb.append("\nsatellite : ")
                    sb.append(location.satelliteNumber)// 卫星数目
                    sb.append("\nheight : ")
                    sb.append(location.altitude)// 海拔高度 单位：米
                    sb.append("\ngps status : ")
                    sb.append(location.gpsAccuracyStatus)// *****gps质量判断*****
                    sb.append("\ndescribe : ")
                    sb.append("gps定位成功")
                } else if (location.locType == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ")
                        sb.append(location.altitude)// 单位：米
                    }
                    sb.append("\noperationers : ")// 运营商信息
                    sb.append(location.operators)
                    sb.append("\ndescribe : ")
                    sb.append("网络定位成功")
                } else if (location.locType == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ")
                    sb.append("离线定位成功，离线定位结果也是有效的")
                } else if (location.locType == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ")
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因")
                } else if (location.locType == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ")
                    sb.append("网络不同导致定位失败，请检查网络是否通畅")
                } else if (location.locType == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ")
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机")
                }
                val str =
                    sb.toString() + "\ntype " + location.locType + "   \n" + location.latitude + "     " + location.longitude
                Logger.i("  $str")
                var cit: String = location.city
                if (!TextUtils.isEmpty(cit)) {

                    cit = cit.replace("市", "")

                    isLocated = true

                    Constant.CITY = cit

                    SharedPreferecneUtils.saveValue(context, Constant.CITY_KEY, cit)
                    mLocationClient?.stop()

                }

                Logger.i("定位 :" + cit + "\n" + sb.toString())


            }
        }

    }
}