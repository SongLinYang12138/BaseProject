package com.bondex.ysl.battledore.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.liblibrary.utils.Tools
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/**
 * date: 2019/8/20
 * Author: ysl
 * description:
 */
class PlanBeansUtils {


    companion object search {

        fun search(str: String, planBeans: ArrayList<PlanBean>, callBack: ModelCallback<Any>) {


            val searBeans = arrayListOf<PlanBean>()
            val observable = Observable.create(ObservableOnSubscribe<String> { emitter ->
                val str = str.toLowerCase()

                for (item in planBeans) {


                    if (!TextUtils.isEmpty(item.flight) && item.flight.toLowerCase().contains(str)) {

                        searBeans.add(item)
                    } else if (!TextUtils.isEmpty(item.destination) && item.destination.toLowerCase().contains(str)) {

                        searBeans.add(item)
                    } else if (item.hawbs != null) {

                        for (it in item.hawbs) {

                            if (it.hawb.toLowerCase().contains(str) || it.getmBillCode().toLowerCase().contains(str)) {
                                searBeans.add(item)
                                break
                            }
                        }
                    }
                }

                emitter.onNext("Y")
            })
            val observer: Consumer<String> = Consumer<String> {
                //                setMsgLiveDataValue(1)
                callBack.onSuccess(searBeans)
            }

            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        }
    }


    object sort {
        fun sortPlanBeans(type: Int, planBeans: ArrayList<PlanBean>) {

            when (type) {

                0 -> { //对航班时间进行排序

                    Collections.sort(planBeans, object : Comparator<PlanBean> {
                        override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                            if (o1 != null && o2 != null) {

                                return (Tools.getTimeMilles(o1.flghtDate) - Tools.getTimeMilles(o2.flghtDate)).toInt()

                            }
                            return -1
                        }
                    })


                }

                1 -> { //对航班号进行排序

                    Collections.sort(planBeans, object : Comparator<PlanBean> {
                        override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                            if (o1 != null && o2 != null) {
                                return o1.flight.compareTo(o2.flight)
                            }
                            return -1
                        }
                    })
                }

                2 -> { //对总件数进行排序

                    Collections.sort(planBeans, object : Comparator<PlanBean> {
                        override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                            if (o1 != null && o2 != null) {

                                return o1.qtyTotal - o2.qtyTotal
                            }
                            return -1
                        }
                    })


                }

            }
        }
    }


}