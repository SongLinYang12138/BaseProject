package com.bondex.ysl.battledore.login

import android.arch.lifecycle.MutableLiveData
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.ModelCallback
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

    protected val loginBeanData: MutableLiveData<LoginBean> = MutableLiveData()

    val loginModel: LoginModel = LoginModel()

    override fun onCreate() {

        loginModel.readData(context, object : ModelCallback<LoginBean> {

            override fun onSuccess(data: LoginBean?) {

                if (data != null){

                    setLoginLiveData(data)
                }
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

                }else{
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

    override fun onResume() {
    }

    override fun onStart() {


    }


    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }

}