package com.bondex.ysl.battledore.list


import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentListBinding
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {


    private val list = mutableListOf<String>()

    private val adapter = ListAdapter(list)


    override fun initView() {

        binding.listViewModel = viewModel

        list_edit.setOnClickListener(listener)


        val manager = LinearLayoutManager(context)

        list_recycler_view.layoutManager = manager
        list_recycler_view.adapter = adapter
        list_recycler_view.addItemDecoration(TextItemDecoration())

        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        adapter.updataList(list)

        initList()
    }

    private fun initList() {

        val rankList = listOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")


        list_sp_sort.setList(rankList)

        list_sp_sort.setSelectListener { item, position ->
            {

                Logger.i("list " + rankList.get(position))
            }
        }


    }


    override fun getResourceId(): Int {

        return R.layout.fragment_list

    }

    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.list_edit -> {

                val intent = Intent(activity, WorkBetchActivity::class.java)
                startActivity(intent)
            }


        }

    }


}
