package com.bondex.ysl.battledore.plan

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentPlanBinding
import com.bondex.ysl.battledore.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_plan.*


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, FragmentPlanBinding>() {


    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.button -> {

                ToastUtils.showShort("点击")
                viewModel.user = User("asassssd","change ", 12)
            }


        }

    }

    override fun initView() {

        binding.planViewModel = viewModel


//        button?.setOnClickListener(listener)
    }


    override fun getResourceId(): Int {

        return R.layout.fragment_plan
    }


}
