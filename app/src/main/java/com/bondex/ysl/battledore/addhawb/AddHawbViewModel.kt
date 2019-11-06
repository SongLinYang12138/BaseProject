package com.bondex.ysl.battledore.addhawb

import android.util.Log
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.hawb.HAWBDao
import com.bondex.ysl.databaselibrary.plan.PlanBean
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * date: 2019/7/25
 * Author: ysl
 * description:
 */
class AddHawbViewModel : BaseViewModle(), AddHawbItemListener {


    var hawbBeans = arrayListOf<HAWBBean>()
    var planHawbBeans = arrayListOf<HAWBBean>()

    private val plan_Adapter: AddHawbPlanAdapter = AddHawbPlanAdapter(planHawbBeans, this@AddHawbViewModel)
    private var list_adapter: AddHawbAdapter = AddHawbAdapter(hawbBeans, this@AddHawbViewModel)
    private var deliverBean: PlanBean? = null


    override fun onCreate() {

        initData()
    }

/*
* 获取可打板的分单号
* */
    fun initData() {

        val observable = Observable.create(object : ObservableOnSubscribe<List<HAWBBean>> {
            override fun subscribe(emitter: ObservableEmitter<List<HAWBBean>>) {

                val dao = HAWBDao.getInstance(context)

                val list = dao.all
                Log.i("aaa", " hawb size " + list.size)

                hawbBeans.addAll(list)
                emitter.onNext(list)
            }
        })

        val consumer = object : Consumer<List<HAWBBean>> {
            override fun accept(t: List<HAWBBean>?) {

                if (t?.size!! > 0) {

                    getHawbAdapter().updateList(hawbBeans)
                }
            }

        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)


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
/*
* 获取添加的分单号
* */
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

    override fun onListItemClick(position: Int) {

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