package com.bondex.ysl.battledore.history


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentHistoryBinding
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : BaseFragment<HistoryViewModel,FragmentHistoryBinding>() {
    private val list = mutableListOf<String>()

    private var adapter:HistoryAdapter? = null


    override fun initView() {

        binding.historyViewModel = viewModel


        val manager = LinearLayoutManager(context)

        history_recycler_view.layoutManager = manager

        activity?.let { adapter =  HistoryAdapter(list, it) }

        adapter?.let { history_recycler_view.adapter = adapter }
        history_recycler_view.addItemDecoration(TextItemDecoration())

        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        adapter?.updataList(list)


        initList()
    }

    private fun initList() {

        val rankList = listOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")


        history_sp_sort.setList(rankList)

        history_sp_sort.setSelectListener { item, position ->
            {

                Logger.i("list " + rankList.get(position))
            }
        }


    }

    override fun getResourceId(): Int {

        return R.layout.fragment_history
    }

    override fun myClick(view: View?) {
    }


}
