package com.bondex.ysl.battledore.list

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.hawb.img.HAWBImgBean
import com.bondex.ysl.databaselibrary.hawb.img.HAWBImgDataBase
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.databaselibrary.plan.PlanBeanDao
import com.bondex.ysl.liblibrary.utils.Tools
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */

class ListViewModel : BaseViewModle() {

    var planBeans = arrayListOf<PlanBean>()
    val adapter = ListAdapter(planBeans)
    val mDisposable = CompositeDisposable()
    var failedName = StringBuffer()


    fun initData() {

        failedName = StringBuffer()
        val observable = Observable.create(ObservableOnSubscribe<ArrayList<PlanBean>> { emitter ->
            val dao = PlanBeanDao.getInstance(context)

            planBeans = dao.notSuccess
            emitter.onNext(planBeans)
        })

        val observer =
            Consumer<ArrayList<PlanBean>> { t -> t?.let { adapter.updataList(t) } }

        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        )
    }

    fun commitPlans() {

        val observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {

                try {


                    val select = adapter.selectList
                    val dao = PlanBeanDao.getInstance(context)

                    val imgDao = HAWBImgDataBase.getInstance(context).dao

                    val gson = Gson()


                    for (item in select) {

                        if (item.status == 3 || item.status == 1) {

                            failedName.append(item.boardNum + ",")
                        } else {

                            for (hawb in item.hawbs) {//获取分单对应的图片，每个分单可能对应多张图片

                                val imgBean: List<HAWBImgBean>? = imgDao.getHAWBImage(hawb.getmBillCode(), hawb.hawb)
                                val fileList = arrayListOf<String>()

                                imgBean?.let {
                                    for (it in imgBean) {
                                        fileList.add(it.imgName)
                                    }
                                    hawb.files = fileList
                                }

                            }
                            item.status = 1

                            dao.update(item)

                        }

                    }

                    val json = gson.toJson(select.get(0))
                    Log.i(Constant.TAG, "提交: " + json)
                    emitter.onNext("Y")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })
        val observer = Consumer<String> {


            if (!TextUtils.isEmpty(failedName))
                ToastUtils.showShort(failedName.toString() + "未完成不能提交")
            setMsgLiveDataValue(1)
        }

        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        )

    }

    fun delete() {

        val observable = Observable.create(ObservableOnSubscribe<String> {


            val select = adapter.selectList
            val dao = PlanBeanDao.getInstance(context)
            val imgDao = HAWBImgDataBase.getInstance(context).dao

            for (item in select) {

                item.status = 1

                for (hawb in item.hawbs) {//获取分单对应的图片，每个分单可能对应多张图片

                    val imgBean: List<HAWBImgBean>? = imgDao.getHAWBImage(hawb.getmBillCode(), hawb.hawb)

                    imgBean?.let {
                        for (it in imgBean) {

                            val file = File(it.path)
                            if (file.exists()) file.delete()
                        }
                        imgDao.delteAll(imgBean)
                    }
                }

                dao.delete(item.id)
            }

            it.onNext("Y")


        })
        val observer = Consumer<String> {

            initData()

        }

        mDisposable.add(
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                observer
            )
        )


    }


    override fun onCreate() {
        initData()

    }

    override fun onResume() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {


        if (!mDisposable.isDisposed) mDisposable.dispose()

        mDisposable.clear()
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }
}