package com.bondex.ysl.battledore.main

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityMainBinding
import com.bondex.ysl.battledore.history.HistoryFragment
import com.bondex.ysl.battledore.list.ListFragment
import com.bondex.ysl.battledore.login.LoginActivity
import com.bondex.ysl.battledore.plan.PlanFragment
import com.bondex.ysl.battledore.util.AirPortDialogUtil
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.util.HttpConnection
import com.bondex.ysl.battledore.util.SharedPreferecneUtils
import com.bondex.ysl.battledore.util.interf.HttpResultBack
import com.bondex.ysl.battledore.util.interf.ModelCallback
import com.bondex.ysl.databaselibrary.airport.AirPort
import com.bondex.ysl.databaselibrary.contact.ContactBean
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.hawb.HAWBDao
import com.bondex.ysl.databaselibrary.login.LoginBean
import com.bondex.ysl.liblibrary.utils.CommonUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_title.*
import org.json.JSONObject


class MainActivity : BaseActivity<MainViewModle, ActivityMainBinding>() {


    val REQUEST_PERMISSION: Int = 1212

    var activity: MainActivity = MainActivity@ this

    val contactObserver: Observer<ContactBean> = object : Observer<ContactBean> {
        override fun onChanged(t: ContactBean?) {

            main_left_name.setText(t?.name + "     " + t?.position)
            main_left_company.setText(t?.company)
            main_left_department.setText(t?.department)
            main_left_tv_code.setText(t?.code)

        }
    }
    val airPortDialogUtil = AirPortDialogUtil(this@MainActivity)
    val airportObserver = Observer<List<AirPort>> {

        airPortDialogUtil.showAirPort(it) { airport ->

            main_left_tv_airport.text = airport.code
        }
    }


    override fun initData() {

        checkpermission()
        getAirPort()
    }

//    新接口，暂时不能连接api

    private fun getAirPort() {

        val head = JSONObject()

        head.put("SenderID", "1012")
        head.put("SenderName", "空运打板")
        head.put("ReciverID", "40")
        head.put("ReciverName", "基础数据")
        head.put("Version", "1.0")
        head.put("Action", "query")

        val main = JSONObject()
        main.put("token", Constant.LOGIN_BEAN.cstoken)
        main.put("limit", 100)
        main.put("countryCode", "CHN")

        HttpConnection.connect(
            HttpConnection.getNetApi().getAirPort(head.toString(), main.toString()),
            object : HttpResultBack {
                override fun onFaile(error: String) {
                    Logger.i("faile " + error)

                }

                override fun onSuccess(data: String) {

                    Logger.i("success " + data)
                }
            })


    }


    var selected: ColorStateList? = null
    var normal: ColorStateList? = null

    var pagerAdapter: MainPagerAdapter? = null


    override fun getReourceId(): Int {

        return R.layout.activity_main
    }


    override fun handleMessage(msg: Int?) {

        when (msg) {
            1 -> {
                loginOut()
            }
        }

    }

    private fun checkpermission() {


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ) {

                val array: Array<String> = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )

                requestPermissions(array, REQUEST_PERMISSION)

            }
        } else {


        }


    }

    override fun initView() {
        main_left_tv_version.setText(CommonUtils.getVersionName(MainActivity@ this))


        binding.maniViewModle = viewModel

        selected = resources.getColorStateList(R.color.rect_red)
        normal = resources.getColorStateList(R.color.text_gray)


        plan.setOnClickListener(listener)
        list.setOnClickListener(listener)
        history.setOnClickListener(listener)
        main_left_it_close.setOnClickListener(listener)

        main_setting.setOnClickListener(listener)
        tv_plane.setOnClickListener(listener)
        tv_list.setOnClickListener(listener)
        tv_history.setOnClickListener(listener)
        main_left_login_out.setOnClickListener(listener)
        main_left_airport.setOnClickListener(listener)


        viewModel.getContactLiveData().observe(this, contactObserver)


        val list: MutableList<Fragment> = mutableListOf()

        val planFragment = PlanFragment()
        val listFragment = ListFragment()
        val historyFragment = HistoryFragment()
        listFragment.historyListener(historyFragment)

        list.add(planFragment)
        list.add(listFragment)
        list.add(historyFragment)

        pagerAdapter = MainPagerAdapter(supportFragmentManager, list)

        viewpager.adapter = pagerAdapter

        viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {

                changeBack(p0 + 1)
            }
        })

        changeBack(0)

        viewModel.airPortLiveData.observe(this, airportObserver)

    }

    private fun iniMainLeft() {

//        main_left_name.setText(Constant.LOGIN_BEAN.psnname+"  "+Constant.LOGIN_BEAN.)


    }


    override fun onStart() {
        super.onStart()
        main_left_tv_airport.text = Constant.AIR_CODE

    }

    override fun onMyClick(view: View?) {

        when (view?.id) {

            R.id.plan,
            R.id.tv_plane -> {
                changeBack(1)
                viewpager.setCurrentItem(0)
            }

//            R.id.mission,
//            R.id.tv_mission -> {
//                changeBack(2)
//                viewpager.setCurrentItem(1)
//
//            }
            R.id.list,
            R.id.tv_list -> {
                changeBack(2)
                viewpager.setCurrentItem(1)

            }

            R.id.history,
            R.id.tv_history -> {
                changeBack(3)
                viewpager.setCurrentItem(2)

            }
            R.id.main_setting -> {

                if (!main_drawer.isDrawerOpen(main_drawer_left)) {

                    main_drawer.openDrawer(main_drawer_left)
                } else main_drawer.closeDrawer(main_drawer_left)
            }
            R.id.main_left_login_out -> {

                loginOut()
            }

            R.id.main_left_it_close -> {

                main_drawer.closeDrawer(main_drawer_left)
            }
            R.id.main_left_airport -> {


                viewModel.getAirport()
            }

        }
    }

    private fun loginOut() {

        viewModel.loginOut(object : ModelCallback<LoginBean> {
            override fun onFaile(msg: String?) {

            }

            override fun onSuccess(data: LoginBean?) {

                val intent: Intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


    }


    private fun changeBack(position: Int) {

        when (position) {

            1 -> {

                line_plan.visibility = View.VISIBLE
//                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.INVISIBLE
                line_history.visibility = View.INVISIBLE

                tv_plane.setTextColor(selected)
//                tv_mission.setTextColor(normal)
                tv_list.setTextColor(normal)
                tv_history.setTextColor(normal)

                main_title.setText("打板计划")


//                plan.setTextColor(selected)
//                mission.setTextColor(normal)
//                list.setTextColor(normal)
//                history.setTextColor(normal)
            }

//            2 -> {
//
//
//                line_plan.visibility = View.INVISIBLE
//                line_mission.visibility = View.VISIBLE
//                line_list.visibility = View.INVISIBLE
//                line_history.visibility = View.INVISIBLE
//
//                tv_plane.setTextColor(normal)
//                tv_mission.setTextColor(selected)
//                tv_list.setTextColor(normal)
//                tv_history.setTextColor(normal)
//                main_title.setText("打板任务")
//
//
////                plan.setTextColor(normal)
////                mission.setTextColor(selected)
////                list.setTextColor(normal)
////                history.setTextColor(normal)
//            }
            2 -> {


                line_plan.visibility = View.INVISIBLE
//                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.VISIBLE
                line_history.visibility = View.INVISIBLE

                tv_plane.setTextColor(normal)
//                tv_mission.setTextColor(normal)
                tv_list.setTextColor(selected)
                tv_history.setTextColor(normal)
                main_title.setText("打板列表")


//                plan.setTextColor(normal)
//                mission.setTextColor(normal)
//                list.setTextColor(selected)
//                history.setTextColor(normal)
            }

            3 -> {


                line_plan.visibility = View.INVISIBLE
//                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.INVISIBLE
                line_history.visibility = View.VISIBLE

                tv_plane.setTextColor(normal)
//                tv_mission.setTextColor(normal)
                tv_list.setTextColor(normal)
                tv_history.setTextColor(selected)
                main_title.setText("打板历史")


//                plan.setTextColor(normal)
//                mission.setTextColor(normal)
//                list.setTextColor(normal)
//                history.setTextColor(selected)

            }


        }


    }


    override fun onDestroy() {
        super.onDestroy()

        viewModel.getContactLiveData().removeObserver(contactObserver)
        viewModel.airPortLiveData.removeObserver(airportObserver)
    }

}
