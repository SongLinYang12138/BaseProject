package com.bondex.ysl.battledore.list


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.NotifyListObserver

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentListBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.FragmentMsg
import com.bondex.ysl.battledore.util.PlanBeansUtils
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.main_title.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    private val menuList = mutableListOf<String>("航班日期", "航班号", "件数")

    private var fragmentMsg: FragmentMsg? = null

    private val notifyObserver = Observer<Int> {

        viewModel.initData()

    }

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

    fun historyListener(fragmentMsg: FragmentMsg) {
        this.fragmentMsg = fragmentMsg
    }

    private fun search(str: String) {

        plan_search.edit.removeTextChangedListener(searchWatcher)

        PlanBeansUtils.search(str, viewModel.planBeans, object : ModelCallback<Any> {
            override fun onFaile(msg: String?) {

            }

            override fun onSuccess(data: Any?) {

                val tmpList: ArrayList<PlanBean> = data as ArrayList<PlanBean>
                viewModel.adapter.updataList(tmpList)
                plan_search.edit.addTextChangedListener(searchWatcher)

            }
        })

    }

    override fun stopLoading() {

    }

    override fun showLoading() {

    }

    override fun handleMessage(msg: Int?) {

        when (msg) {

            1 -> { //提交后更新打板清单

                viewModel.initData()

                fragmentMsg?.let { fragmentMsg?.notifyHistory(1) }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotifyListObserver.getIntstace().notify.observe(this, notifyObserver)

    }

    override fun onDestroy() {
        super.onDestroy()

        NotifyListObserver.getIntstace().notify.removeObserver(notifyObserver)
    }

    override fun getResourceId(): Int {

        return R.layout.fragment_list

    }


    override fun initView() {

        binding.listViewModel = viewModel

        list_edit.setOnClickListener(listener)
        list_chck_all.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.adapter.checkAll(isChecked)
        }


        val manager = LinearLayoutManager(context)

        list_recycler_view.layoutManager = manager
        list_recycler_view.adapter = viewModel.adapter
        list_recycler_view.addItemDecoration(TextItemDecoration())



        list_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        list_search.edit.setHint("板号\\目的港\\航班号")
        list_search.edit.addTextChangedListener(searchWatcher)

        list_commit.setOnClickListener(listener)
        list_cancel.setOnClickListener(listener)

        initList()
    }

    private fun initList() {


        list_menulist.addData(menuList)
        list_menulist.setOnMenuClick(object : MenuList.MenuClick {
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

            R.id.list_edit -> {

                val selectList = viewModel.adapter.selectList

                if (selectList.size == 0) {

                    ToastUtils.showShort("请选择打板计划")
                    return
                }

                val bundle = Bundle()
                bundle.putParcelableArrayList(Constant.PLAN_BEAN_KEY, selectList)


                val intent = Intent(activity, WorkBetchActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)
            }

            R.id.list_commit -> {

                val selectList = viewModel.adapter.selectList

                if (selectList.size == 0) {

                    ToastUtils.showShort("请选择打板计划")
                    return
                }
                viewModel.commitPlans()

            }

            R.id.list_cancel -> {

                viewModel.delete()

            }


        }

    }


}
