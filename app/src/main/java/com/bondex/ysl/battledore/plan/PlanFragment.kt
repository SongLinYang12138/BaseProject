package com.bondex.ysl.battledore.plan

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.addhawb.AddHawbActivity
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentPlanBinding
import com.bondex.ysl.battledore.goodslist.GoodsListActivity
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
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

        initList()
    }

    override fun stopLoading() {
        adapter?.let {
            adapter?.updataList(viewModel.getList())
        }

    }

    override fun showLoading() {

    }

    override fun handleMessage(msg: Int?) {

    }

    override fun myClick(view: View?) {

        when (view?.id) {

            R.id.plan_bt -> {

                val intent = Intent(context, WorkBetchActivity::class.java)
                context?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)
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

    private fun initList() {


    }


}
