package com.bondex.ysl.battledore.workbench

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityWorkBetchBinding
import com.bondex.ysl.battledore.plan.PlanBean
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import com.bondex.ysl.liblibrary.utils.*
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_work_betch.*
import java.util.*
import kotlin.collections.ArrayList

class WorkBetchActivity : BaseActivity<WorkBetchViewModle, ActivityWorkBetchBinding>() {


    val scanIntent = IntentIntegrator(WorkBetchActivity@ this)
    var mainCode: String? = null
    val IMAGE_REQUEST: Int = 111
    var planBeans = arrayListOf<PlanBean>() //打板计划界面传递的数据

    var plateTypePostion = 0

    //    监听从viewModel传递过来的数据，并在页面展示
    val planObserver: Observer<PlanBean> = object : Observer<PlanBean> {
        override fun onChanged(t: PlanBean?) {

            if (t == null) return

            work_battle_flight.setText(t.flight)
            work_battle_detination.setText(t.destination)
            work_battle_flight_date.setText(t.flghtDate)
            work_plate_num.setText(t.boardNum)
            work_plate_lock.setText(t.lockNum)

            if (t.plateType == null || TextUtils.isEmpty(t.plateType.name))
                work_plate_type.prompt = viewModel.plateTypeList.get(0).name
            else work_plate_type.prompt = t.plateType.name

        }
    }


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
//        work_battle_date.setOnClickListener(listener)
        work_it_hawb.setOnClickListener(listener)
        work_battle_flight_date.setOnClickListener(listener)
        work_bt_last.setOnClickListener(listener)
        work_bt_next.setOnClickListener(listener)
        work_bt_save.setOnClickListener(listener)

        val calendar: Calendar = Calendar.getInstance()

        val currenDate: String = String.format("%04d", calendar.get(Calendar.YEAR)) + "-" + String.format(
            "%02d",
            (calendar.get(Calendar.MONTH) + 1)
        ) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

        work_battle_date.setText(currenDate)

        viewModel.setDatas(planBeans)

        val manager = LinearLayoutManager(this)
        work_recycleview.layoutManager = manager
        work_recycleview.adapter = viewModel.adapter

        work_recycleview.isNestedScrollingEnabled = false


        val protectManager = LinearLayoutManager(this)
        protectManager.orientation = LinearLayoutManager.HORIZONTAL

        val subplateManager = LinearLayoutManager(this)
        subplateManager.orientation = LinearLayoutManager.HORIZONTAL

        work_protect_type_recyclerView.layoutManager = protectManager
        work_subplate_recyclerView.layoutManager = subplateManager

        work_protect_type_recyclerView.adapter = viewModel.protectTypeAdapter
        work_subplate_recyclerView.adapter = viewModel.subPlateAdapter

        work_plate_type.adapter = viewModel.getPlateTypeAdapter()

        work_plate_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

                plateTypePostion = 0
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                plateTypePostion = id.toInt()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel?.getPlanLiveData()?.observe(this@WorkBetchActivity, planObserver)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel?.getPlanLiveData()?.removeObserver(planObserver)
    }


    override fun handleMessage(msg: Int?) {

        when (msg) {

            1 -> { //新建页面

                newPage()
            }

        }

    }

    override fun initData() {

        val bundle = intent.extras

        planBeans = bundle.getParcelableArrayList(Constant.PLAN_BEAN_KEY)

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
//打板时间直接调用当前时间，不用选择
//                DateUtils.showDate(WorkBetchActivity@ this, { it ->
//                    work_battle_date.setText(it)
//
//                })
            }
            R.id.work_battle_flight_date -> {

                DateUtils.showDate(WorkBetchActivity@ this, { it ->
                    work_battle_flight_date.setText(it)
                })
            }

            R.id.work_bt_last -> {
                viewModel.last()
            }
            R.id.work_bt_next -> {
                viewModel.next()
            }
            R.id.work_bt_save -> {

                val planBean: PlanBean = PlanBean()
                shouldSave(planBean)

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

    fun newPage() {

        val planBean = PlanBean()

        if (!shouldSave(planBean)) {
            return
        }

        viewModel.planBeans.add(planBean)


        work_battle_flight.text.clear()
        work_battle_detination.text.clear()
        work_plate_num.text.clear()
        work_plate_lock.text.clear()
        val calendar: Calendar = Calendar.getInstance()

        val currenDate: String = String.format("%04d", calendar.get(Calendar.YEAR)) + "-" + String.format(
            "%02d",
            (calendar.get(Calendar.MONTH) + 1)
        ) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

        work_battle_date.setText(currenDate)
        work_battle_flight_date.setText(currenDate)
        viewModel.adapter.clear()
        viewModel.protectTypeAdapter.clear()
        viewModel.subPlateAdapter.clear()
        viewModel.protectTypeAdapter.updateList(viewModel.protectTypeList)
        viewModel.subPlateAdapter.updateList(viewModel.subPlateList)

    }

    private fun shouldSave(planBean: PlanBean): Boolean {

        val flgiht = work_battle_flight.text.toString()
        val destination = work_battle_detination.text.toString()
        val flight_date = work_battle_flight_date.text.toString()
        val boardNum = work_plate_num.text.toString()
        val boardLock = work_plate_lock.text.toString()
        val battlerDate = work_battle_date.text.toString()


        if (TextUtils.isEmpty(flgiht)) {
            ToastUtils.showShort("请输入航班号")
            return false
        }

        if (TextUtils.isEmpty(destination)) {
            ToastUtils.showShort("请输入目的港")
            return false
        }
        if (TextUtils.isEmpty(boardNum)) {
            ToastUtils.showShort("请输入板号")
            return false
        }


        val plateType = viewModel.plateTypeList.get(plateTypePostion)
        val protectList = viewModel.protectTypeAdapter.selectList
        val subPlateList = viewModel.subPlateAdapter.selectList
        Log.i(Constant.TAG, "protectSize " + protectList.size + "  subplate " + subPlateList.size)

        if (protectList.size == 0) {
            ToastUtils.showShort("请选择保护类型")
            return false
        }


        if (subPlateList.size == 0) {
            ToastUtils.showShort("请选择垫板类型")
            return false
        }


        val hawbs = viewModel.adapter.list


        planBean.flight = flgiht
        planBean.destination = destination
        planBean.flghtDate = flight_date
        planBean.boardNum = boardNum
        planBean.lockNum = boardLock
        planBean.battleDate = battlerDate
        planBean.plateType = plateType
        planBean.protectType = protectList
        planBean.subPlateTypel = subPlateList
        planBean.hawbs = hawbs

        return true
    }
}


