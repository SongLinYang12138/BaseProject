package com.bondex.ysl.battledore.history


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentHistoryBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.FragmentMsg
import com.bondex.ysl.battledore.util.PlanBeansUtils
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.databaselibrary.plan.PlanBean
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : BaseFragment<HistoryViewModel,FragmentHistoryBinding>(),FragmentMsg {

    private val menuList = mutableListOf<String>("航班日期", "航班号", "件数")




    val searchWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }

        override fun afterTextChanged(s: Editable?) {

            if (s?.length!! >= 2) {

                search(s.toString())
            } else if (s?.length == 0) {

                viewModel.adapter?.updataList(viewModel.planBeans)
            }

        }
    }

    private fun search(str: String) {

        history_search.edit.removeTextChangedListener(searchWatcher)

        PlanBeansUtils.search(str, viewModel.planBeans, object : ModelCallback<Any> {
            override fun onFaile(msg: String?) {

            }

            override fun onSuccess(data: Any?) {

                val tmpList: ArrayList<PlanBean> = data as ArrayList<PlanBean>
                viewModel.adapter.updataList(tmpList)
                history_search.edit.addTextChangedListener(searchWatcher)

            }
        })

    }
    override fun notifyHistory(msg: Int) {

        viewModel.initData()

    }


    override fun stopLoading() {

    }

    override fun showLoading() {

    }

    override fun handleMessage(msg: Int?) {

    }


    override fun getResourceId(): Int {

        return R.layout.fragment_history

    }


    override fun initView() {



        val manager = LinearLayoutManager(context)

        history_recycler_view.layoutManager = manager
        history_recycler_view.adapter = viewModel.adapter
        history_recycler_view.addItemDecoration(TextItemDecoration())




        initList()
    }

    private fun initList() {


        history_menulist.addData(menuList)
        history_menulist.setOnMenuClick(object : MenuList.MenuClick {
            override fun menuChoice(position: Int, menu: String) {


                PlanBeansUtils.sort.sortPlanBeans(position, viewModel.planBeans)
                viewModel.adapter.updataList(viewModel.planBeans)

            }
        })

    }

    override fun onStart() {
        super.onStart()

    }


    override fun myClick(view: View?) {

        when (view?.id) {




        }

    }

}
