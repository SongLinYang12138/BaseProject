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
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_plan.*
import java.util.ArrayList


/**
 *
 */
class PlanFragment : BaseFragment<PlanViewModel, FragmentPlanBinding>() {


    private val list = mutableListOf<String>()
    private val menuList = mutableListOf<String>("航班日期", "航班号", "件数")

    private var adapter: PlanAdapter? = null
    val mainHawbs: ArrayList<MainHawbBean> = arrayListOf(
        MainHawbBean("756-45442145", "HWS73289656"),
        MainHawbBean("756-454756445", "HWS789656"),
        MainHawbBean("756-454554865", "HWS785656"),
        MainHawbBean("756-454512445", "HWS789456")
    )
    private val cameraConfig: ISCameraConfig = ISCameraConfig.Builder().setHawbBeans(mainHawbs).build()

    val searchWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }

        override fun afterTextChanged(s: Editable?) {

            if (s?.length!! >= 2) {

                search(s.toString())
            } else if (s?.length == 0) {

                adapter?.updataList(viewModel.getList())
            }

        }
    }

    override fun getResourceId(): Int {

        return R.layout.fragment_plan
    }


    override fun initView() {

        binding.planViewModel = viewModel

        plan_camera.setOnClickListener(listener)

        val manager = LinearLayoutManager(context)

        plan_recycler_view.layoutManager = manager

        activity?.let {
            adapter = PlanAdapter(viewModel.getList(), it)

        }
        adapter?.let {
            plan_recycler_view.adapter = adapter
        }

        plan_bt.setOnClickListener(listener)
        plan_bt_create.setOnClickListener(listener)

        plan_recycler_view.addItemDecoration(TextItemDecoration())

        plan_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        plan_search.edit.setHint("主单号\\分单号\\航班号\\目的港")



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
                viewModel.sortPlanBeans(position)
            }
        })

        plan_ck_all.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {


                adapter?.isAll(isChecked)
            }
        })


    }

    private fun search(str: String) {

        plan_search.edit.removeTextChangedListener(searchWatcher)
        viewModel.filterList(str)
    }

    override fun stopLoading() {
        adapter?.let {
            adapter?.updataList(viewModel.getList())
        }

    }

    override fun showLoading() {

    }

    override fun handleMessage(msg: Int?) {

        when (msg) {
            1 -> {

                adapter?.updataList(viewModel.getSearch())
                plan_search.edit.addTextChangedListener(searchWatcher)

                Log.i("aaa", "count " + adapter?.itemCount)
            }

        }


    }

    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.plan_bt -> {

                adapter?.let {
                    val selectList = adapter!!.getSelected()

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

                    adapter?.isAll(false)

                }

            }

            R.id.plan_bt_create -> {

                val intent = Intent(context, AddHawbActivity::class.java)
                intent.putExtra("title", "打板任务")
                context?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)
            }
            R.id.plan_camera -> {

                ISNav.getInstance().toCamera(PlanFragment@ this, cameraConfig, Constant.CAMERA_REQUEST)
            }

        }

    }


}
