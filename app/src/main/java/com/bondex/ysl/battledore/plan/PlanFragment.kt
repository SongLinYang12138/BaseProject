package com.bondex.ysl.battledore.plan

import android.content.Context
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, ViewDataBinding>() {
    override fun getResourceId(): Int {

        return R.layout.fragment_plan
    }

}
