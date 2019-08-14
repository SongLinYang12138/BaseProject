package com.bondex.ysl.battledore.history


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentHistoryBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : BaseFragment<HistoryViewModel,FragmentHistoryBinding>() {
    override fun showLoading() {
    }

    override fun stopLoading() {
    }

    override fun handleMessage(msg: Int?) {

    }

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

        history_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        initList()
    }

    private fun initList() {

        val rankList = mutableListOf<String>("打板日期","航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")

        history_menulist.addData(rankList)


        history_menulist.setOnMenuClick(object :MenuList.MenuClick{
            override fun menuChoice(position: Int, menu: String) {

            }
        })



    }

    override fun getResourceId(): Int {

        return R.layout.fragment_history
    }

    override fun myClick(view: View?) {
    }


}
