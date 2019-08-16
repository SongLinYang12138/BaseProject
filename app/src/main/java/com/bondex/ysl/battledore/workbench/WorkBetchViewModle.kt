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


    private var isMission: Boolean = false
    private var planBeans = arrayListOf<PlanBean>()
    var hawbBeans = arrayListOf<HAWBBean>()

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


    val saveList = arrayListOf<PlanBean>() //用于保存在打板任务中本次工作缓存下来的数据
    /*
    * 用于viewmodel和activity中进行通讯*/
    private val planData = MutableLiveData<PlanBean>()

    private var currentPosition: Int = 0 //代表当前页数

    val adapter = WorkBetchAdapter(hawbBeans)

    val protectTypeAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(protectTypeList)
    val subPlateAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(subPlateList)


    fun setDatas(flag: Boolean, planBeans: ArrayList<PlanBean>, hawbBean: ArrayList<HAWBBean>) {

        this.isMission = flag
        this.planBeans = planBeans
        this.hawbBeans = hawbBean

        if (isMission) adapter.updateList(hawbBeans)
        else {

            adapter.updateList(planBeans.get(0).hawbs)
            setPlanLiveData(planBeans.get(0))
        }
    }


    fun last() {

        if (currentPosition == 0 || isMission) {
            ToastUtils.showShort("当前已是第一页")
            return
        }

        --currentPosition
        setPlanLiveData(planBeans.get(currentPosition))
    }

    fun next() {

        if (currentPosition == planBeans.size - 1) {
            ToastUtils.showShort("当前已是最后一页")
            return
        }
        ++currentPosition

        if (isMission) { //当数据来源于打板任务，上一页就是新建页面
            setMsgLiveDataValue(1)
        } else setPlanLiveData(planBeans.get(currentPosition))


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

        saveList.clear()
    }

    override fun onStop() {
    }

    override fun onDestroy() {

        saveList.clear()
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }

}