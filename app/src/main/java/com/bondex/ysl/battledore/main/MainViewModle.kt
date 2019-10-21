package com.bondex.ysl.battledore.main

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import android.util.Log
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.HttpConnection
import com.bondex.ysl.battledore.util.SharedPreferecneUtils
import com.bondex.ysl.battledore.util.interf.HttpResultBack
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.databaselibrary.airport.AirPort
import com.bondex.ysl.databaselibrary.airport.AirPortDatabase
import com.bondex.ysl.databaselibrary.contact.ContactBean
import com.bondex.ysl.databaselibrary.contact.ContactDao
import com.bondex.ysl.databaselibrary.contact.ContactDataBase
import com.bondex.ysl.databaselibrary.login.LoginBean
import com.bondex.ysl.databaselibrary.login.LoginDao
import com.bondex.ysl.databaselibrary.login.LoginDataBase
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
class MainViewModle : BaseViewModle() {


    private val contactLiveData: MutableLiveData<ContactBean> = MutableLiveData()
    internal val airPortLiveData = MutableLiveData<List<AirPort>>()
    internal val mDisposable = CompositeDisposable()


    fun setContectLiveData(bean: ContactBean) {

        contactLiveData.postValue(bean)

    }

    fun getContactLiveData(): MutableLiveData<ContactBean> {

        return contactLiveData
    }

    override fun onCreate() {


        if (Constant.LOGIN_BEAN == null)
            readLogin()
        if (TextUtils.isEmpty(Constant.AIR_CODE))
            Constant.AIR_CODE = SharedPreferecneUtils.getValue(context, Constant.AIR_CODE_KEY)


        if (TextUtils.isEmpty(Constant.CITY))
            Constant.CITY = SharedPreferecneUtils.getValue(context, Constant.CITY_KEY)

        getPersonal()
        checkToken()

    }

    fun getAirport() {


        val observable = Observable.create(ObservableOnSubscribe<List<AirPort>> {
            val dao = AirPortDatabase.getInstance(context).dao

            val list = dao.selectByCity(Constant.CITY)

            it.onNext(list)
        })
        val observer = Consumer<List<AirPort>> {

            airPortLiveData.value = it

        }


        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer)
        )
    }

    fun getPersonal() {

        val map = HashMap<String, String>()
        map["token"] = if (Constant.LOGIN_BEAN == null) "" else Constant.LOGIN_BEAN.getToken()
        map["search"] = ""
        map["searchType"] = "E"
        map["category"] = "person"
        map["id"] = Constant.LOGIN_BEAN.getPsncode()
        HttpConnection.connect(HttpConnection.getNetApi().getPhone(map), object : HttpResultBack {
            override fun onFaile(error: String) {


                readPersonal()
            }

            override fun onSuccess(data: String) {


                try {

                    val ob = JSONObject(data)

                    val rowCount = ob.getInt("rowCount")

                    if (rowCount > 0) {

                        val array = ob.getJSONArray("rows")
                        val gson = Gson()

                        var bean: ContactBean? = null
                        for (i in 0 until array.length()) {

                            bean = gson.fromJson<Any>(
                                array.getJSONObject(i).toString(),
                                ContactBean::class.java
                            ) as ContactBean
                        }

                        if (bean != null) {

                            setContectLiveData(bean)
                            inserPersonal(bean)
                        }


                    } else {

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }
        })
    }


    fun inserPersonal(bean: ContactBean) {


        val thread: Thread = Thread(object : Runnable {
            override fun run() {

                val dao: ContactDao = ContactDataBase.getInstance(context).contactDao

                if (dao.getContact(bean.code) != null) {
                    dao.updateContact(bean)
                } else {
                    dao.inster(bean)
                }


            }
        })

        thread.isDaemon = true
        thread.start()


    }

    fun readPersonal() {

        val observable: Observable<ContactBean> = Observable.create(object : ObservableOnSubscribe<ContactBean> {
            override fun subscribe(emitter: ObservableEmitter<ContactBean>) {

                val dao: ContactDao = ContactDataBase.getInstance(context).contactDao

                var bean: ContactBean = dao.getContact(Constant.LOGIN_BEAN.psncode)

                if (bean == null) {
                    bean = ContactBean()
                }

                emitter.onNext(bean)
                emitter.onComplete()


            }
        })
        val consumer: Consumer<ContactBean> = object : Consumer<ContactBean> {
            override fun accept(t: ContactBean?) {

                if (t != null) setContectLiveData(t)
            }
        }

        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
        )

    }


    fun readLogin() {

        val observer: Observable<LoginBean> = Observable.create(object : ObservableOnSubscribe<LoginBean> {
            override fun subscribe(emitter: ObservableEmitter<LoginBean>) {

                val dao: LoginDao = LoginDataBase.getInstance(context).dao

                val list: List<LoginBean> = dao.byLoginDate

                if (list != null && list.size > 0) {
                    Constant.LOGIN_BEAN = list[0]
                } else {
                    Constant.LOGIN_BEAN = LoginBean()
                }

            }
        })

        val consumer: Consumer<LoginBean> = object : Consumer<LoginBean> {
            override fun accept(t: LoginBean?) {

            }
        }


        mDisposable.add(
            observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
        )
    }

    fun loginOut(callBack: ModelCallback<LoginBean>) {

        val observer: Observable<LoginBean> = Observable.create(object : ObservableOnSubscribe<LoginBean> {
            override fun subscribe(emitter: ObservableEmitter<LoginBean>) {

                val dao: LoginDao = LoginDataBase.getInstance(context).dao

                Constant.LOGIN_BEAN.isLogin = false

                dao.update(Constant.LOGIN_BEAN)
                emitter.onNext(Constant.LOGIN_BEAN)
                emitter.onComplete()
            }
        })

        val consumer: Consumer<LoginBean> = object : Consumer<LoginBean> {
            override fun accept(t: LoginBean?) {

                callBack.onSuccess(t)
            }
        }

        mDisposable.add(
            observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
        )
    }

    private fun checkToken() {

        val observable = Observable.create(object : ObservableOnSubscribe<Boolean> {
            override fun subscribe(emitter: ObservableEmitter<Boolean>) {

                HttpConnection.connect(HttpConnection.getNetApi().isTokenValid(Constant.LOGIN_BEAN.token),
                    object : HttpResultBack {
                        override fun onSuccess(data: String) {

                            val jsonObject = JSONObject(data)

                            if (jsonObject.has("success")) {

                                val success = jsonObject.getBoolean("success")

                                emitter.onNext(success)
                            }
                        }

                        override fun onFaile(error: String) {

                        }
                    })


            }
        })
        val observer = object : Consumer<Boolean> {
            override fun accept(t: Boolean?) {

                if (t != null && !t) {

                    setMsgLiveDataValue(1)
                }
            }
        }
        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        )
    }

    override fun onResume() {


    }

    override fun onStart() {


    }

    override fun onStop() {


    }

    override fun onDestroy() {

        mDisposable.clear()
    }

    override fun registerRxBus() {


    }

    override fun removeRxBus() {
    }

}