package com.bondex.ysl.battledore.plan

import android.databinding.BaseObservable
import android.view.View
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.util.ToastUtils

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */

class PlanViewModel : BaseViewModle() {

    var user: User = User("aa", "asda", 12)

    override fun onCreate() {

    }


    fun planClick(view: View) {

        ToastUtils.showShort("点击")
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