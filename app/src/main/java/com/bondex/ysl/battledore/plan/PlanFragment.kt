package com.bondex.ysl.battledore.plan

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.addhawb.AddHawbActivity
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentPlanBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.PlanBeansUtils
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_plan.*
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, FragmentPlanBinding>() {


    private val list = mutableListOf<String>()
    private val menuList = mutableListOf("航班日期", "航班号", "件数")
    private var pageSize = 1


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

    override fun onStart() {
        super.onStart()

        viewModel.refresh()
        Log.i("aaa", " plan_start")
    }

    override fun onStop() {
        super.onStop()
        Log.i("aaa", " plan_stop")

    }

    override fun getResourceId(): Int {

        return R.layout.fragment_plan
    }


    override fun initView() {

        binding.planViewModel = viewModel

        plan_camera.setOnClickListener(listener)

        val manager = LinearLayoutManager(context)

        plan_recycler_view.layoutManager = manager


        plan_recycler_view.adapter = viewModel.adapter

        plan_bt.setOnClickListener(listener)
        plan_bt_create.setOnClickListener(listener)

        plan_recycler_view.addItemDecoration(TextItemDecoration())

        plan_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        plan_search.edit.setHint("主单号\\分单号\\航班号\\目的港")

        plan_smart_refresh.setEnableRefresh(false)

        plan_smart_refresh.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                Log.i("aaa", "loadmore")
                ++pageSize
                viewModel.getData(pageSize)
            }
        })

//        plan_smart_refresh.setOnLoadMoreListener {
//            object : OnLoadMoreListener {
//                override fun onLoadMore(refreshLayout: RefreshLayout) {
//

//
//                }
//            }
//        }


        plan_search.edit.addTextChangedListener(searchWatcher)

        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")


        plan_menu.addData(menuList)
        plan_menu.setOnMenuClick(object : MenuList.MenuClick {
            override fun menuChoice(position: Int, menu: String) {

                Logger.i("planMenu  " + position)
                PlanBeansUtils.sort.sortPlanBeans(position, viewModel.planBeans)
                viewModel.adapter.updataList(viewModel.planBeans)

            }
        })

        plan_ck_all.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                viewModel.adapter?.isAll(isChecked)
            }
        })


    }

    private fun search(str: String) {

        plan_search.edit.removeTextChangedListener(searchWatcher)
        PlanBeansUtils.search(str, viewModel.planBeans, object : ModelCallback<Any> {
            override fun onSuccess(data: Any?) {

                val list = data as ArrayList<PlanBean>
                viewModel.adapter.updataList(list)
                plan_search.edit.addTextChangedListener(searchWatcher)

            }

            override fun onFaile(msg: String?) {
            }

        })

    }

    override fun stopLoading() {

        Log.i("aaa", "stop")
        plan_smart_refresh.finishRefresh(true)
        plan_smart_refresh.finishLoadMore(true)
    }

    override fun showLoading() {
        Log.i("aaa", "show")

        plan_smart_refresh.finishRefresh(true)
        plan_smart_refresh.finishLoadMore(true)
    }

    override fun handleMessage(msg: Int?) {

        when (msg) {
            1 -> {


            }

        }


    }


    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.plan_bt -> {


                viewModel.adapter?.let {
                    val selectList = viewModel.adapter!!.getSelected()

                    if (selectList.size == 0) {
                        ToastUtils.showShort("请选择打板计划")
                        return
                    }


                    val bundle = Bundle()
                    bundle.putParcelableArrayList(Constant.PLAN_BEAN_KEY, selectList)
                    val intent = Intent(context, WorkBetchActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                    activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)

                    viewModel.adapter?.isAll(false)

                }

            }

            R.id.plan_bt_create -> {

                val intent = Intent(context, AddHawbActivity::class.java)
                intent.putExtra("title", "打板任务")
                context?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)
            }
            R.id.plan_camera -> {

                val planBeans = viewModel.adapter?.getSelected()

                if (planBeans == null || planBeans.size == 0) {
                    ToastUtils.showShort("请选择打板计划")
                    return
                }
                val hawbs = arrayListOf<HAWBBean>()

                for (item in planBeans) {

                    hawbs.addAll(item.hawbs)
                }



                if (hawbs.size == 0) {
                    ToastUtils.showShort("当前没有可以拍照的分单")
                    return
                }

                val cameraConfig: ISCameraConfig = ISCameraConfig.Builder().setHawbBeans(hawbs).build()

                ISNav.getInstance().toCamera(PlanFragment@ this, cameraConfig, Constant.CAMERA_REQUEST)
            }

        }

    }


}
