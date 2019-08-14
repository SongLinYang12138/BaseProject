package com.bondex.ysl.battledore.addhawb

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityAddHawbBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_add_hawb.*

class AddHawbActivity : BaseActivity<AddHawbViewModel, ActivityAddHawbBinding>() {
    override fun handleMessage(msg: Int?) {

    }

    private val list: List<String> = listOf("1", "2", "3", "4", "5", "6")
    val rankList = mutableListOf<String>("航班日期", "航班号", "版型", "数量", "分单号", "板总重排序")
    val activity = AddHawbActivity@ this

    private var plan_adapter: AddHawbAdapter = AddHawbAdapter(list)
    private val planAdapter: AddHawbPlanAdapter = AddHawbPlanAdapter(list)
    private var list_adapter: AddHawbAdapter = AddHawbAdapter(list)


    override fun getReourceId(): Int {

        return R.layout.activity_add_hawb
    }


    override fun initData() {

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
        add_hawb_plan.adapter = planAdapter
        add_hawb_plan.addItemDecoration(TextItemDecoration())

        val list_manager: LinearLayoutManager = LinearLayoutManager(AddHawbActivity@ this)


        add_hawb_list.layoutManager = list_manager
        add_hawb_list.addItemDecoration(TextItemDecoration())
        add_hawb_list.adapter = list_adapter


        add_hawb_edit.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val intent = Intent(activity, WorkBetchActivity::class.java)
                startActivity(intent)
            }
        })


    }

    override fun onMyClick(view: View?) {

    }

}
