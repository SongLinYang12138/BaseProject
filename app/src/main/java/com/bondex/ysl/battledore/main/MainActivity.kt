package com.bondex.ysl.battledore.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.battledore.databinding.ActivityMainBinding
import com.orhanobut.logger.Logger


class MainActivity : BaseActivity<MainViewModle, ActivityMainBinding>() {

    override fun startLoading() {

    }

    override fun stopLoading() {
    }


    override fun getReourceId(): Int {

        return R.layout.activity_main
    }


}
