package com.bondex.ysl.battledore.photo

import android.text.TextUtils
import android.util.Log
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.hawb.img.HAWBImgBean
import com.bondex.ysl.databaselibrary.hawb.img.HAWBImgDataBase
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * date: 2019/8/23
 * Author: ysl
 * description:
 */
class PhotoViewModel : BaseViewModle() {

    val imgList = arrayListOf<HAWBImgBean>()
    val adapter = PhotoAdapter(imgList)

    fun getHwabImg(hawbs: ArrayList<HAWBBean>) {


        val observable = Observable.create(ObservableOnSubscribe<String> {

            imgList.clear()
            val dao = HAWBImgDataBase.getInstance(context).dao

            for (item in hawbs) {

                var bean: List<HAWBImgBean>? = null
                bean = dao.getHAWBImage(item.getmBillCode(), item.hawb)

                bean?.let {

                    if (bean.isNotEmpty()) {
                        imgList.addAll(bean)
                    }
                }
            }

            it.onNext("Y")

        })
        val observer = Consumer<String> {

            adapter.updateList(imgList)

        }
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }

    fun delete() {
        val list = adapter.selectList

        if (list.size == 0) {

            ToastUtils.showShort("请选择分单")
            return
        }

        val observable = Observable.create(ObservableOnSubscribe<String> {

            val dao = HAWBImgDataBase.getInstance(context).dao

            for (item in list) {
                val file = File(item.path)
                if (file.exists()) file.delete()
            }


            dao.delteAll(list)
            imgList.removeAll(list)

            it.onNext("Y")

        })

        val consumer = Consumer<String> {


            adapter.updateList(imgList)

        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)

    }


    override fun onCreate() {

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