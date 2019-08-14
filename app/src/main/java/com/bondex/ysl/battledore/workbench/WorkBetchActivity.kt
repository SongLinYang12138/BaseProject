package com.bondex.ysl.battledore.workbench

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityWorkBetchBinding
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import com.bondex.ysl.liblibrary.utils.*
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_work_betch.*
import java.util.*

class WorkBetchActivity : BaseActivity<WorkBetchViewModle, ActivityWorkBetchBinding>() {
    override fun handleMessage(msg: Int?) {

    }

    override fun initData() {

    }

    val list = mutableListOf<String>()
    val adapter = WorkBetchAdapter(list)

    val protectTypeList: MutableList<WorkBentchChoiceBean> = mutableListOf(
        WorkBentchChoiceBean("单件保护", 1, 0),
        WorkBentchChoiceBean("整体保护", 2, 0)
    )

    val subPlateList: MutableList<WorkBentchChoiceBean> = mutableListOf(
        WorkBentchChoiceBean("老件", 1, 0),
        WorkBentchChoiceBean("新件", 2, 0)
    )

    val protectTypeAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(protectTypeList)
    val subPlateAdapter: WorkBentchChoiceAdapter = WorkBentchChoiceAdapter(subPlateList)


    val scanIntent = IntentIntegrator(WorkBetchActivity@ this)
    var mainCode: String? = null
    val IMAGE_REQUEST: Int = 111

    override fun getReourceId(): Int {

        return R.layout.activity_work_betch
    }

    override fun initView() {

        showLeft(true, object : NoDoubleClickListener() {
            override fun click(v: View?) {
                finish()
            }
        })
        showTitle("打板工作台")

        val mainHawbs: ArrayList<MainHawbBean> = arrayListOf(
            MainHawbBean("756-454512445", "HWS789656"),
            MainHawbBean("756-454512445", "HWS789656"),
            MainHawbBean("756-454512445", "HWS789656"),
            MainHawbBean("756-454512445", "HWS789656")
        )
        val config = ISCameraConfig.Builder().setHawbBeans(mainHawbs).build()
        showRight(true, R.string.camera, {

            ISNav.getInstance().toCamera(WorkBetchActivity@ this, config, IMAGE_REQUEST)
        }
        )

        work_it_main.setOnClickListener(listener)
        work_battle_date.setOnClickListener(listener)
        work_it_hawb.setOnClickListener(listener)
        work_battle_flight_date.setOnClickListener(listener)


        val calendar: Calendar = Calendar.getInstance()

        val currenDate: String = String.format("%04d", calendar.get(Calendar.YEAR)) + "-" + String.format(
            "%02d",
            (calendar.get(Calendar.MONTH) + 1)
        ) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

        work_battle_date.setText(currenDate)
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        adapter.updateList(list)

        val manager = LinearLayoutManager(this)
        work_recycleview.layoutManager = manager
        work_recycleview.adapter = adapter

        work_recycleview.isNestedScrollingEnabled = false


        val protectManager = LinearLayoutManager(this)
        protectManager.orientation = LinearLayoutManager.HORIZONTAL

        val subplateManager = LinearLayoutManager(this)
        subplateManager.orientation = LinearLayoutManager.HORIZONTAL

        work_protect_type_recyclerView.layoutManager = protectManager
        work_subplate_recyclerView.layoutManager = subplateManager

        work_protect_type_recyclerView.adapter = protectTypeAdapter
        work_subplate_recyclerView.adapter = subPlateAdapter

    }

    override fun onMyClick(view: View?) {

        when (view?.id) {


            R.id.work_it_hawb -> {
                scanIntent.setRequestCode(Constant.SCAN_HAWB_REQUEST_CODE).initiateScan()

            }
            R.id.work_it_main -> {
                scanIntent.setRequestCode(Constant.SCAN_MAIN_REQUEST_CODE).initiateScan()
            }
            R.id.work_battle_date -> {

                DateUtils.showDate(WorkBetchActivity@ this, { it ->
                    work_battle_date.setText(it)

                })
            }
            R.id.work_battle_flight_date ->{

                DateUtils.showDate(WorkBetchActivity@this,{it ->
                    work_battle_flight_date.setText(it)
                })
            }


        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                Constant.SCAN_MAIN_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)

                }
                Constant.SCAN_HAWB_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)

                }


            }


        }


    }
}


