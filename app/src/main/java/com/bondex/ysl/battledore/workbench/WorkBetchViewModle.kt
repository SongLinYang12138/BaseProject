package com.bondex.ysl.battledore.workbench

import android.arch.lifecycle.MutableLiveData
import android.widget.ArrayAdapter
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.plan.PlanBean
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.databaselibrary.hawb.HAWBBean

/**
 * date: 2019/6/12
 * Author: ysl
 * description:
 */
class WorkBetchViewModle : BaseViewModle() {


    var planBeans = arrayListOf<PlanBean>()


    val plateTypeList = mutableListOf<WorkBentchChoiceBean>(
        WorkBentchChoiceBean("大", 1, 0),
        WorkBentchChoiceBean("中", 2, 0),
        WorkBentchChoiceBean("小", 2, 0)

    )

    val protectTypeList: MutableList<WorkBentchChoiceBean> = mutableListOf(
        WorkBentchChoiceBean("单件保护", 1, 0),
        WorkBentchChoiceBean("整体保护", 2, 0)
    )

    val subPlateList: MutableList<WorkBentchChoiceBean> = mutableListOf(
        WorkBentchChoiceBean("老件", 1, 0),
        WorkBentchChoiceBean("新件", 2, 0)
    )
    val protectTypeAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(protectTypeList) //保护类型
    val subPlateAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(subPlateList)//垫板类型
    val adapter = WorkBetchAdapter(arrayListOf())//分单


    private var currentPosition: Int = 0 //代表当前页数
    /*
    * 用于viewmodel和activity中进行通讯*/
    private val planData = MutableLiveData<PlanBean>()


    fun setDatas(planBeans: ArrayList<PlanBean>) {

        this.planBeans = planBeans

        adapter.updateList(planBeans.get(0).hawbs)
        setPlanLiveData(planBeans.get(0))
    }


    fun last() {

        if (currentPosition == 0) {
            ToastUtils.showShort("当前已是第一页")
            return
        }

        --currentPosition
        setPlanLiveData(planBeans.get(currentPosition))
        adapter.updateList(planBeans.get(currentPosition).hawbs)
    }

    fun next() {

        if (currentPosition == planBeans.size - 1) {

            setMsgLiveDataValue(1)
        } else {

            ++currentPosition
            setPlanLiveData(planBeans.get(currentPosition))
            adapter.updateList(planBeans.get(currentPosition).hawbs)
        }

    }

    fun getPlanLiveData(): MutableLiveData<PlanBean> {

        return planData
    }

    fun setPlanLiveData(plan: PlanBean) {

        planData.value = plan
    }

    fun getPlateTypeAdapter(): ArrayAdapter<WorkBentchChoiceBean> {


        val palteTypeAdapter: ArrayAdapter<WorkBentchChoiceBean> =
            ArrayAdapter(context, R.layout.item_spinner_layout, R.id.spinner_tv, plateTypeList)


        return palteTypeAdapter
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