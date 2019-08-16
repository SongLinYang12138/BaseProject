package com.bondex.ysl.battledore.addhawb

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.CompoundButton
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityAddHawbBinding
import com.bondex.ysl.battledore.plan.PlanBean
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_add_hawb.*

class AddHawbActivity : BaseActivity<AddHawbViewModel, ActivityAddHawbBinding>(), AddHawbItemListener {


    private val list: ArrayList<HAWBBean> = arrayListOf()
    val rankList = mutableListOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")
    val activity = AddHawbActivity@ this

    var deliverPlanBean: PlanBean? = null


    override fun getReourceId(): Int {

        return R.layout.activity_add_hawb
    }


    override fun initData() {

        val bundle = intent.extras

        if (bundle != null) deliverPlanBean = bundle.getParcelable(Constant.PLAN_BEAN_KEY)

    }


    override fun initView() {


        if (intent.hasExtra("title")) {
            val title = intent.getStringExtra("title")
            showTitle(title)
        }
        showLeft(true, object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        add_hawb_add.setOnClickListener(listener)
        add_hawb_ck.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                viewModel?.selectAll(isChecked)
            }
        })

        val list = arrayListOf<MainHawbBean>(
            MainHawbBean("789-6487788", "HWR878454"),
            MainHawbBean("789-648571w", "HRR878454"),
            MainHawbBean("789-6489657", "HWG878454"),
            MainHawbBean("789-6483432", "HWJ888454")
        )
        showRight(true, R.string.camera, object : View.OnClickListener {
            override fun onClick(v: View?) {

                val config: ISCameraConfig = ISCameraConfig.Builder().setHawbBeans(list).build()
                ISNav.getInstance().toCamera(this@AddHawbActivity, config, Constant.CAMERA_REQUEST)
            }
        })


        add_hawb_menu.addData(rankList)
        add_hawb_menu.setOnMenuClick(object : MenuList.MenuClick {
            override fun menuChoice(position: Int, menu: String) {

            }
        })
        val plan_manager: LinearLayoutManager = LinearLayoutManager(AddHawbActivity@ this)

        add_hawb_plan.layoutManager = plan_manager
        add_hawb_plan.adapter = viewModel?.getPlanAdapter()

        add_hawb_plan.addItemDecoration(TextItemDecoration())

        val list_manager: LinearLayoutManager = LinearLayoutManager(AddHawbActivity@ this)


        add_hawb_list.layoutManager = list_manager
        add_hawb_list.addItemDecoration(TextItemDecoration())
        add_hawb_list.adapter = viewModel.getHawbAdapter()


        add_hawb_edit.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                if (viewModel?.getPlanAdapter()?.mylist?.size == 0) {
                    ToastUtils.showShort("请选择分单号")
                    return
                }
                val bundle = Bundle()

                /*
                * 确保workBetchActivity中的数据一致，和PlanFragment中一样传递Arrlist<PlanBean>
                * */
                val planBeans = arrayListOf<PlanBean>()
                val planBean = PlanBean()
                planBean.hawbs = viewModel?.getPlanAdapter()?.mylist
                planBeans.add(planBean)

                bundle.putParcelableArrayList(Constant.PLAN_BEAN_KEY, planBeans)

                val intent = Intent(activity, WorkBetchActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }
        })

        if (deliverPlanBean != null) {

            viewModel?.addDeliverBean(deliverPlanBean!!)
        }


    }

    override fun handleMessage(msg: Int?) {

        when (msg) {

//            1 -> {
//
//                list_adapter.updateList(viewModel?.getHawb())
//            }
//
//            2 -> {
//
//                planAdapter.updateList(viewModel?.getPlan())
//            }
        }

    }


    override fun onMyClick(view: View?) {


        when (view?.id) {

            R.id.add_hawb_add -> {

                viewModel?.addPlanList()

            }

        }

    }

    override fun onListItemClick(position: Int) {


    }

    override fun onPlanItemClick(position: Int) {
    }

}
