package com.bondex.ysl.battledore.list


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.View

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentListBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    override fun stopLoading() {

    }

    override fun showLoading() {

    }

    override fun handleMessage(msg: Int?) {

    }


    override fun getResourceId(): Int {

        return R.layout.fragment_list

    }
    private val list = mutableListOf<ListBean>(
        ListBean(),
        ListBean(),
        ListBean(),
        ListBean(),
        ListBean(),
        ListBean(),
        ListBean(),
        ListBean()
    )

    private val adapter = ListAdapter(list)


    override fun initView() {

        binding.listViewModel = viewModel

        list_edit.setOnClickListener(listener)
        list_chck_all.setOnCheckedChangeListener { buttonView, isChecked ->
            adapter.checkAll(isChecked)
        }


        val manager = LinearLayoutManager(context)

        list_recycler_view.layoutManager = manager
        list_recycler_view.adapter = adapter
        list_recycler_view.addItemDecoration(TextItemDecoration())

        adapter.updataList(list)
        list_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        list_search.edit.setHint("板号\\目的港\\航班号")

        initList()
    }

    private fun initList() {

        val rankList = mutableListOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")

        list_menulist.addData(rankList)
        list_menulist.setOnMenuClick(object :MenuList.MenuClick{
            override fun menuChoice(position: Int, menu: String) {

            }
        })

//
//        list_sp_sort.setList(rankList)
//
//        list_sp_sort.setSelectListener { item, position ->
//            {
//
//                Logger.i("list " + rankList.get(position))
//            }
//        }


    }



    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.list_edit -> {

                val intent = Intent(activity, WorkBetchActivity::class.java)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)
            }


        }

    }


}
