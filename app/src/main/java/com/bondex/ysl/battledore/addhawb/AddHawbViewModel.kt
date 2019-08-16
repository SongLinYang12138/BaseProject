package com.bondex.ysl.battledore.addhawb

import android.util.ArrayMap
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.plan.PlanBean
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.adapter.HawbAdapter
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.hawb.HAWBBean

/**
 * date: 2019/7/25
 * Author: ysl
 * description:
 */
class AddHawbViewModel : BaseViewModle(), AddHawbItemListener {


    private var hawbBeans = arrayListOf<HAWBBean>()
    private var planHawbBeans = arrayListOf<HAWBBean>()

    private val plan_Adapter: AddHawbPlanAdapter = AddHawbPlanAdapter(planHawbBeans, this@AddHawbViewModel)
    private var list_adapter: AddHawbAdapter = AddHawbAdapter(hawbBeans, this@AddHawbViewModel)
    private var deliverBean: PlanBean? = null


    override fun onCreate() {

        initData()
    }


    fun initData() {

        for (i in 0..6) {

            val bean = HAWBBean()
            bean.date = "2019-08-0" + i
            bean.detination = "LUX"
            bean.flight = "SC4545$i"
            bean.hawb = "FTC7879" + i + "78"
            bean.qty = 12 + i
            bean.volume = "14.0"
            bean.weight = "87.6"
            bean.setmBillCode("784-" + i + "86544877$i")
            bean.id = SHA.Bit16(bean.getmBillCode() + bean.hawb)
            hawbBeans.add(bean)
        }

        getHawbAdapter().updateList(hawbBeans)
    }

    fun getHawbAdapter(): AddHawbAdapter {
        return list_adapter
    }

    fun getPlanAdapter(): AddHawbPlanAdapter {

        return plan_Adapter
    }

    fun addDeliverBean(deliverBean: PlanBean) {

        this.deliverBean = deliverBean
        planHawbBeans.addAll(deliverBean.hawbs)

        plan_Adapter.updateList(planHawbBeans)
    }

    //  list中的数据  单个加入plan
    fun addPlanSingle(position: Int) {


        planHawbBeans.add(0, hawbBeans.get(position))

        plan_Adapter.updateList(planHawbBeans)

        hawbBeans.removeAt(position)
        getHawbAdapter().updateList(hawbBeans)
    }

    fun addPlanList() {

        if (list_adapter.selectList.size == 0) {

            ToastUtils.showShort("请先选择分单")
            return
        }

        val selected = list_adapter.selectList

        val tmpList = arrayListOf<HAWBBean>() //用于缓存hawbBeans中的数据，并remove选中的部分

        tmpList.addAll(hawbBeans)
        for (item in selected) {

            if (item.value) {

                planHawbBeans.add(0, hawbBeans.get(item.key))
                tmpList.remove(hawbBeans.get(item.key))
            }
        }
        hawbBeans.clear()
        hawbBeans.addAll(tmpList)
        list_adapter.updateList(hawbBeans)
        plan_Adapter.updateList(planHawbBeans)


    }


        addPlanSingle(position)
    }

    override fun onPlanItemClick(position: Int) {

        hawbBeans.add(0, planHawbBeans.get(position))
        planHawbBeans.removeAt(position)

        list_adapter.updateList(hawbBeans)
        plan_Adapter.updateList(planHawbBeans)

    }

    fun selectAll(isCheck: Boolean) {

        list_adapter.setSelect(isCheck)
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