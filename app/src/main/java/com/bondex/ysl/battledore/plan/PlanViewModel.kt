package com.bondex.ysl.battledore.plan

import android.os.Build
import android.view.View
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.base.bean.PlateType
import com.bondex.ysl.databaselibrary.base.bean.ProtectType
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.liblibrary.utils.Tools
import java.util.*
import java.util.stream.Collectors

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */

class PlanViewModel : BaseViewModle() {

    val planBeans = arrayListOf<PlanBean>()

    override fun onCreate() {

        val hawbBean = HAWBBean()
        hawbBean.date = "2019-08-03"
        hawbBean.detination = "LUX"
        hawbBean.flight = "SC45457"
        hawbBean.hawb = "FTC78947"
        hawbBean.qty = 12
        hawbBean.volume = "14.0"
        hawbBean.weight = "87.6"
        hawbBean.setmBillCode("784-965448778")
        hawbBean.id = SHA.Bit16(hawbBean.getmBillCode() + hawbBean.hawb)

        val subplate = SubPlateType()

        subplate.cityId = 101
        subplate.subplateId = "01"
        subplate.subplateName = "老大垫"
        subplate.id = SHA.Bit16(subplate.cityId.toString() + subplate.subplateId)

        val protectType = ProtectType()

        protectType.cityId = 101
        protectType.protectId = "01"
        protectType.protectName = "新品保护"
        protectType.id = SHA.Bit16(protectType.cityId.toString() + protectType.protectId)

        val plateType = PlateType()

        plateType.cityId = 101
        plateType.plateId = "01"
        plateType.plateName = "大"
        plateType.id = SHA.Bit16(plateType.cityId.toString() + plateType.plateId)


        val hawbs = arrayListOf<HAWBBean>(hawbBean)
        hawbBean.hawb = "RTE52344646"
        hawbBean.setmBillCode("528-36646467")
        hawbs.add(hawbBean)

        val subplates = arrayListOf<SubPlateType>(subplate)

        subplate.subplateName = "新大垫"
        subplate.subplateId = "02"
        subplate.id = SHA.Bit16(subplate.cityId.toString() + subplate.subplateId)
        subplates.add(subplate)

        val protectTypes = arrayListOf<ProtectType>(protectType)
        val plateTypes = arrayListOf<PlateType>(plateType)

        val planBean = PlanBean()
        planBean.hawbs = hawbs
        planBean.subPlateTypel = subplates
        planBean.protectType = protectTypes
        planBean.plateType = plateTypes
        planBean.destination = "LUX"
        planBean.flghtDate = "2019-08-13"
        planBean.flight = "SC4568"
        planBean.hawbTotal = "8H"
        planBean.qtyTotal = 12
        planBean.volumeTotal = "18"
        planBean.weightTotal = "14"
        planBean.setmBillTotal("5M")

        planBeans.add(planBean)

        val planBean1 = PlanBean()

        planBean1.subPlateTypel = subplates
        planBean1.protectType = protectTypes
        planBean1.plateType = plateTypes
        planBean1.destination = "CDU"
        planBean1.flghtDate = "2019-08-14"
        planBean1.flight = "SC4875"
        planBean1.hawbTotal = "8H"
        planBean1.qtyTotal = 8
        planBean1.volumeTotal = "18"
        planBean1.weightTotal = "14"
        planBean1.setmBillTotal("5M")
        planBeans.add(planBean1)

        val planBean2 = PlanBean()
        planBean2.hawbs = hawbs
        planBean2.subPlateTypel = subplates
        planBean2.protectType = protectTypes
        planBean2.plateType = plateTypes
        planBean2.destination = "HK"
        planBean2.flghtDate = "2019-07-14"
        planBean2.flight = "HK4875"
        planBean2.hawbTotal = "8H"
        planBean2.qtyTotal = 3
        planBean2.volumeTotal = "18"
        planBean2.weightTotal = "14"
        planBean2.setmBillTotal("5M")


        planBeans.add(planBean2)
        planBeans.add(planBean)
        planBeans.add(planBean2)
        planBeans.add(planBean)

        setRefresh(true)
    }

    fun getList() = planBeans

    fun planClick(view: View) {

        ToastUtils.showShort("点击")
    }

    fun sortPlanBeans(type :Int){

        when(type){

            0 ->{

                Collections.sort(planBeans,object :Comparator<PlanBean>{
                    override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                        if(o1 != null && o2 != null){

                            return (Tools.getTimeMilles(o1.flghtDate) - Tools.getTimeMilles(o2.flghtDate)).toInt()

                        }
                        return -1
                    }
                })


            }

            1 ->{

                Collections.sort(planBeans,object :Comparator<PlanBean>{
                    override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                        if( o1 != null && o2 != null){
                            return o1.flight.compareTo(o2.flight)
                        }
                        return -1
                    }
                })
            }

            2 -> {

                Collections.sort(planBeans,object :Comparator<PlanBean>{
                    override fun compare(o1: PlanBean?, o2: PlanBean?): Int {

                        if(o1 != null && o2 != null){

                            return o1.qtyTotal - o2.qtyTotal
                        }
                        return  -1
                    }
                })


            }

        }
        setRefresh(false)


    }

    fun filterList(b2:PlanBean){

//     val result:   List<PlanBean> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//         planBeans.stream().filter { (p:PlanBean) -> planBeans.contains(b2) }.collect(Collectors.toList())
//     } else {
//         TODO("VERSION.SDK_INT < N")
//     }

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