package com.bondex.ysl.battledore.plan

import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentPlanBinding
import com.bondex.ysl.battledore.util.ToastUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_plan.*
import java.util.ArrayList


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, FragmentPlanBinding>() {


    override fun myClick(view: View?) {

        when (view?.id) {


        }

    }

    override fun initView() {

        binding.planViewModel = viewModel

        initList()
    }

    private fun initList() {

        val rankList = listOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")


        plan_sp_sort.setList(rankList)

        plan_sp_sort.setSelectListener { item, position ->
            {

                Logger.i("list " + rankList.get(position))
            }
        }

    }


    override fun getResourceId(): Int {

        return R.layout.fragment_plan
    }


}
