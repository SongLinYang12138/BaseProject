package com.bondex.ysl.battledore.plan

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentPlanBinding
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_plan.*


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, FragmentPlanBinding>() {


    private val list = mutableListOf<String>()

    private var adapter : PlanAdapter?  = null
    override fun myClick(view: View?) {

        when (view?.id) {


        }

    }

    override fun initView() {

        binding.planViewModel = viewModel


        val manager = LinearLayoutManager(context)

        plan_recycler_view.layoutManager = manager

        activity?.let {
        adapter = PlanAdapter(list, it)

        }
        adapter?.let {
            plan_recycler_view.adapter = adapter }

        plan_recycler_view.addItemDecoration(TextItemDecoration())

        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        adapter?.let {
            adapter?.updataList(list)
        }

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
