package com.bondex.ysl.battledore.history

import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.list.ListAdapter
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.databaselibrary.plan.PlanBeanDao
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */
class HistoryViewModel : BaseViewModle() {
    var planBeans = arrayListOf<PlanBean>()
    val adapter = HistoryAdapter(planBeans)


    fun initData() {

        val observable = Observable.create(ObservableOnSubscribe<ArrayList<PlanBean>> { emitter ->
            val dao = PlanBeanDao.getInstance(context)

            planBeans = dao.success
            emitter.onNext(planBeans)
        })

        val observer =
            Consumer<ArrayList<PlanBean>> { t -> t?.let { adapter.updataList(t) } }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)


    }


    override fun onCreate() {
        initData()

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