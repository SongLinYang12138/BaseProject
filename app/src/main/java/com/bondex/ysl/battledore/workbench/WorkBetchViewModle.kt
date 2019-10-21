package com.bondex.ysl.battledore.workbench

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.ArrayAdapter
import com.bondex.ysl.battledore.NotifyListObserver
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.databaselibrary.hawb.HAWBDao
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.databaselibrary.plan.PlanBeanDao
import com.bondex.ysl.databaselibrary.plan.WorkBentchChoiceBean
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * date: 2019/6/12
 * Author: ysl
 * description:
 */
class WorkBetchViewModle : BaseViewModle() {


    var planBeans = arrayListOf<PlanBean>()


    val plateTypeList = mutableListOf<WorkBentchChoiceBean>(
        WorkBentchChoiceBean("大", 0, 0),
        WorkBentchChoiceBean("中", 1, 0),
        WorkBentchChoiceBean("小", 2, 0)

    )


    val protectTypeAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(getProtectList()) //保护类型
    val subPlateAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(getSubPlateLists())//垫板类型
    val adapter = WorkBetchAdapter(arrayListOf())//分单

    val mDisposable = CompositeDisposable()

    var currentPosition: Int = 0 //代表当前页数
    /*
    * 用于viewmodel和activity中进行通讯*/
    private val planData = MutableLiveData<PlanBean>()


    fun setDatas(planBeans: ArrayList<PlanBean>) {

        this.planBeans = planBeans

        adapter.updateList(planBeans.get(0).hawbs)
        setPlanLiveData(planBeans.get(0))
    }

    fun getProtectList(): MutableList<WorkBentchChoiceBean> {

        val protectTypeList: MutableList<WorkBentchChoiceBean> = mutableListOf(
            WorkBentchChoiceBean("单件保护", 1, 0),
            WorkBentchChoiceBean("整体保护", 2, 0)
        )
        return protectTypeList

    }

    fun getSubPlateLists(): MutableList<WorkBentchChoiceBean> {

        val subPlateList: MutableList<WorkBentchChoiceBean> = mutableListOf(
            WorkBentchChoiceBean("老件", 1, 0),
            WorkBentchChoiceBean("新件", 2, 0)
        )

        return subPlateList
    }


    fun last() {

        if (currentPosition == 0) {
            ToastUtils.showShort("当前已是第一页")
            return
        }

        --currentPosition
        setPlanLiveData(planBeans.get(currentPosition))
    }

    fun next() {

        setMsgLiveDataValue(1) //调用save方法，然后判断是否需要新建页面，或者切换到下一页

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

    private fun insert(planBean: PlanBean) {

        val observable = Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {

                val dao = PlanBeanDao.getInstance(context)

                var row = -1

                if (dao.getPlanBean(planBean.id) == null) {
                    row = dao.intsert(planBean).toInt()
                    Log.i(Constant.TAG, "添加 " + planBean.id)
                } else {
                    row = dao.update(planBean).toInt()
                    Log.i(Constant.TAG, "更新  " + planBean.id)

                }


                emitter.onNext(row)

            }
        })
        val observer = object : Consumer<Int> {
            override fun accept(t: Int?) {

                t?.let {
                    if (t > 0) {

                        NotifyListObserver.getIntstace().notifyList(1)
                        ToastUtils.showShort("保存成功")
                    }
                }
            }
        }

        mDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        )
    }

    //    当currentPostion等于最后一个元素时，新增空白页面否则切换到下一个元素
    fun newPage(planBean: PlanBean) {

        planBeans.set(currentPosition, planBean)//更改当前页面的信息

        if (currentPosition == planBeans.size - 1) {

            currentPosition = planBeans.size

            val planBean2 = PlanBean()

            planBeans.add(planBean2)

        } else {

            ++currentPosition;
        }

        setPlanLiveData(planBeans.get(currentPosition))
        insert(planBean)

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
        mDisposable.clear()

    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }

}