package com.bondex.ysl.battledore.plan

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.base.bean.PlateType
import com.bondex.ysl.databaselibrary.base.bean.ProtectType
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.liblibrary.utils.Tools
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */

class PlanViewModel : BaseViewModle() {

    val planBeans = arrayListOf<PlanBean>()
    var searBeans = arrayListOf<PlanBean>()

    override fun onCreate() {


        for (i in 0..10) {


            val hawbs = arrayListOf<HAWBBean>()
            val hawbBean = HAWBBean()
            hawbBean.date = "2019-08-0$i"
            hawbBean.detination = toAZ(i) + toAZ(10 - i)
            hawbBean.flight = toAZ(i) + "C4545" + i
            hawbBean.hawb = toAZ(10 - i) + "TC7894" + (10 - i)
            hawbBean.qty = i
            hawbBean.volume = "14." + i
            hawbBean.weight = (10 - i).toString() + "7." + i
            hawbBean.setmBillCode("784-965448778")
            hawbBean.id = SHA.Bit16(hawbBean.getmBillCode() + hawbBean.hawb)
            hawbs.add(hawbBean)

            val subplate = SubPlateType()

            subplate.cityId = 101
            subplate.subplateId = "0" + i
            subplate.subplateName = "老大垫" + toAZ(i)
            subplate.id = SHA.Bit16(subplate.cityId.toString() + subplate.subplateId)


            val protectType = ProtectType()

            protectType.cityId = 101
            protectType.protectId = "01" + i
            protectType.protectName = "新品保护" + toAZ(i)
            protectType.id = SHA.Bit16(protectType.cityId.toString() + protectType.protectId)

            val plateType = PlateType()

            plateType.cityId = 101
            plateType.plateId = "01" + i
            plateType.plateName = "大" + toAZ(i)
            plateType.id = SHA.Bit16(plateType.cityId.toString() + plateType.plateId)


            val subplates = arrayListOf<SubPlateType>(subplate)
            val protectTypes = arrayListOf<ProtectType>(protectType)


            val planBean = PlanBean()
            planBean.hawbs = hawbs
            planBean.subPlateTypel = subplates
            planBean.protectType = protectTypes
            planBean.plateType = plateType
            planBean.destination = toAZ(i) + toAZ(13 - i)
            planBean.flghtDate = "2019-08-" + i + "" + (i - 1)
            planBean.flight = "SC456" + i
            planBean.hawbTotal = "" + (10 - i) + "H"
            planBean.qtyTotal = i
            planBean.volumeTotal = "18"
            planBean.weightTotal = "14"
            planBean.boardNum = toAZ(26 - i) + "1" + toAZ(20 - i) + "72"

            planBean.lockNum = i.toString() + "" + toAZ(18 - i) + i + "2" + toAZ(i)
            planBean.setmBillTotal(i.toString() + "M")
            planBean.id = ""+i
            planBeans.add(planBean)
        }

        val gson = Gson()

        val jsonStr = gson.toJson(planBeans)


//        Log.i("aaa", jsonStr)
        setRefresh(true)
    }

    fun toAZ(num: Int): String {

        val letter = (num % 26 + 'A'.toInt()).toChar().toString()
        return letter
    }

    //数字转字母 1-26 ： A-Z
    private fun numberToLetter(num: Int): String? {
        var num = num
        if (num <= 0) {
            return null
        }
        var letter = ""
        num--
        do {
            if (letter.length > 0) {
                num--
            }
            num = (num - num % 26) / 26
        } while (num > 0)

        return letter
    }

    fun getList() = planBeans

    fun planClick(view: View) {

        ToastUtils.showShort("点击")
    }

    fun sortPlanBeans(type: Int) {

        when (type) {

            0 -> {

                Collections.sort(planBeans, object : Comparator<PlanBean> {
                    override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                        if (o1 != null && o2 != null) {

                            return (Tools.getTimeMilles(o1.flghtDate) - Tools.getTimeMilles(o2.flghtDate)).toInt()

                        }
                        return -1
                    }
                })


            }

            1 -> {

                Collections.sort(planBeans, object : Comparator<PlanBean> {
                    override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                        if (o1 != null && o2 != null) {
                            return o1.flight.compareTo(o2.flight)
                        }
                        return -1
                    }
                })
            }

            2 -> {

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
        setRefresh(false)


    }

    /*
    * 筛选符合条件的对象出来
    * */
    fun filterList(str: String) {

        searBeans.clear()
        val observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {

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

            }
        })
        val observer: Consumer<String> = object : Consumer<String> {
            override fun accept(t: String?) {
                setMsgLiveDataValue(1)

                Log.i("aaa", " searc  " + searBeans.size)

            }


        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)


    }

    fun getSearch(): ArrayList<PlanBean> = searBeans


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

