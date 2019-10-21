package com.bondex.ysl.battledore.battledoredetail

import android.widget.ArrayAdapter
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.plan.WorkBentchChoiceBean

/**
 * date: 2019/6/14
 * Author: ysl
 * description: 打板详情展示，数据共用一个bean
 */

class BattleDoreDetailViewModel : BaseViewModle() {
    val plateTypeList = mutableListOf<WorkBentchChoiceBean>(
        WorkBentchChoiceBean("大", 1, 0),
        WorkBentchChoiceBean("中", 2, 0),
        WorkBentchChoiceBean("小", 2, 0)

    )
    val list = arrayListOf<HAWBBean>()
    val protectList = arrayListOf<WorkBentchChoiceBean>()

    val hawbAdapter = BattleHawbAdapter(list)
    val protectAdapter = BattleDoreDetailAdapter(protectList)
    val subplateAdapter = BattleDoreDetailAdapter(protectList)


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
