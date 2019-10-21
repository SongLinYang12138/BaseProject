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
import com.bondex.ysl.battledore.photo.PhotoActivity
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.hawb.HAWBDao
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.databaselibrary.plan.WorkBentchChoiceBean
import com.bondex.ysl.liblibrary.utils.*
import com.google.gson.Gson
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_work_betch.*
import java.util.*

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

            if (t.plateType == null || TextUtils.isEmpty(t.plateType.name) || viewModel.plateTypeList.indexOf(t.plateType) < 0)
                work_plate_type.setSelection(0)
            else work_plate_type.setSelection(viewModel.plateTypeList.indexOf(t.plateType))
            if (t.protectType != null) {

                val list = arrayListOf<WorkBentchChoiceBean>()
                list.addAll(viewModel.getProtectList())

                for (index in t.protectType.indices) {

                    val posi = list.indexOf(t.protectType.get(index))

                    if (posi != -1) {
                        list.set(posi, t.protectType.get(index))
                    } else list.add(t.protectType.get(index))

                }

                viewModel.protectTypeAdapter.updateList(list)
            } else {

                viewModel.protectTypeAdapter.selectList.clear()
                viewModel.protectTypeAdapter.updateList(viewModel.getProtectList())

            }
            if (t.subPlateTypel != null) {

                val list = arrayListOf<WorkBentchChoiceBean>()
                list.addAll(viewModel.getSubPlateLists())
                for (i in t.subPlateTypel.indices) {

                    val posi = list.indexOf(t.subPlateTypel.get(i))

                    if (posi >= 0) {
                        list.set(posi, t.subPlateTypel.get(i))
                    } else {
                        list.add(t.subPlateTypel.get(i))
                    }
                }

                viewModel.subPlateAdapter.updateList(list)
            } else {

                viewModel.subPlateAdapter.selectList.clear()
                viewModel.subPlateAdapter.updateList(viewModel.getSubPlateLists())
            }

            if (t.hawbs != null) {

                viewModel.adapter.updateList(t.hawbs)
            }
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


        showRight(true, R.string.camera) {

            val hawbs = viewModel.planBeans.get(viewModel.currentPosition).hawbs

            if (hawbs == null || hawbs.size == 0) {
                ToastUtils.showShort("请先添加分单号")
                return@showRight
            }


            val config = ISCameraConfig.Builder().setHawbBeans(hawbs).build()

            ISNav.getInstance().toCamera(WorkBetchActivity@ this, config, IMAGE_REQUEST)
        }

        work_it_main.setOnClickListener(listener)
//        work_battle_date.setOnClickListener(listener)
        work_it_hawb.setOnClickListener(listener)
        work_battle_flight_date.setOnClickListener(listener)
        work_bt_last.setOnClickListener(listener)
        work_bt_next.setOnClickListener(listener)
        work_bt_save.setOnClickListener(listener)
        work_it_photos.setOnClickListener(listener)

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

//                newPage()

                shouldSave()
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
//                })
            }
            R.id.work_battle_flight_date -> {

                DateUtils.showDate(WorkBetchActivity@ this) { it ->
                    work_battle_flight_date.setText(it)
                }
            }

            R.id.work_bt_last -> {

                viewModel.last()
            }
            R.id.work_bt_next -> {

                viewModel.next()
            }
            R.id.work_bt_save -> {

                shouldSave()
            }
            R.id.work_it_photos -> {


                val list = viewModel.adapter.list

                if (list == null || list.size == 0) {
                    ToastUtils.showShort("请添加分单号")
                    return
                }
                val intent = Intent(this@WorkBetchActivity, PhotoActivity::class.java)

                intent.putParcelableArrayListExtra(Constant.HAWB_BEAN_KEY, list)
                startActivity(intent)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                Constant.SCAN_MAIN_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)

                    code?.let {
                        searchMHwabs(code)
                    }

                }
                Constant.SCAN_HAWB_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)

                    code?.let {
                        searchHawbs(code)
                    }
                }


            }


        }


    }

    private fun searchHawbs(code: String) {

        val observable = Observable.create(object : ObservableOnSubscribe<HAWBBean> {
            override fun subscribe(emitter: ObservableEmitter<HAWBBean>) {

                val hawbDao = HAWBDao.getInstance(this@WorkBetchActivity)

                val hawbBean = HAWBBean()

                if (hawbDao.selectByHawb(code) != null) {


                } else {

                    hawbBean.setmBillCode("mbill_" + code)
                    hawbBean.hawb = code
                }

                emitter.onNext(hawbBean)


            }
        })
        val observer = object : Consumer<HAWBBean> {
            override fun accept(t: HAWBBean?) {

                t?.let {

                    if (!TextUtils.isEmpty(t.hawb)) {

                        planBeans.get(viewModel.currentPosition).hawbs.add(t)

                        viewModel.adapter.updateList(viewModel.planBeans.get(viewModel.currentPosition).hawbs)
                    } else {

                        ToastUtils.showShort("该分单号已经打板")
                    }

                }

            }
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)


    }

    private fun searchMHwabs(code: String) {


        val observable = Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {

                val hawbDao = HAWBDao.getInstance(this@WorkBetchActivity)

                val list = hawbDao.selectByMhawb(code)

                if (list != null && list.size > 0) {

                    viewModel.planBeans.get(viewModel.currentPosition).hawbs.addAll(list)

                    emitter.onNext(1)
                } else emitter.onNext(0)

            }
        })
        val observer = object : Consumer<Int> {
            override fun accept(t: Int?) {

                t?.let {

                    if (t == 1) {

                        viewModel.adapter.updateList(viewModel.planBeans.get(viewModel.currentPosition).hawbs)
                    } else {

                        ToastUtils.showShort("该主单号没有搜索到")
                    }

                }

            }
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)


    }


    private fun shouldSave(): Boolean {

        val planBean = viewModel.planBeans.get(viewModel.currentPosition)

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

        var isComplete = true

        val plateType = viewModel.plateTypeList.get(plateTypePostion)
        val protectList = viewModel.protectTypeAdapter.selectList
        val subPlateList = viewModel.subPlateAdapter.selectList
        Log.i(Constant.TAG, "protectSize " + protectList.size + "  subplate " + subPlateList.size)

        if (protectList.size == 0) {
//            ToastUtils.showShort("请选择保护类型")
//            return false
            isComplete = false
        }


        if (subPlateList.size == 0) {
//            ToastUtils.showShort("请选择垫板类型")
//            return false
            isComplete = false
        }


        val hawbs = viewModel.adapter.list

        planBean.protectType.clear()
        planBean.subPlateTypel.clear()

        planBean.flight = flgiht
        planBean.destination = destination
        planBean.flghtDate = flight_date
        planBean.boardNum = boardNum
        planBean.lockNum = boardLock
        planBean.battleDate = battlerDate
        planBean.plateType = plateType
        planBean.protectType.addAll(protectList)
        planBean.subPlateTypel.addAll(subPlateList)
        planBean.hawbs = hawbs

        if (isComplete) {
            planBean.status = 2
        } else {
            planBean.status = 3
        }

        var loadQtyTotal = 0
        val mHawbList = arrayListOf<String>()
        for (it in hawbs) {
            loadQtyTotal += it.loadQty
            if (!mHawbList.contains(it.getmBillCode())) {
                mHawbList.add(it.getmBillCode())
            }
        }

        planBean.setmBillTotal(mHawbList.size.toString())
        planBean.qtyTotal = loadQtyTotal
        planBean.hawbTotal = hawbs.size.toString()

        val gson = Gson()

        val json = gson.toJson(planBean)
        Logger.i("json  " + json)
        viewModel.newPage(planBean)

        return true
    }
}
