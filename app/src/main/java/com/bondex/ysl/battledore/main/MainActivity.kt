package com.bondex.ysl.battledore.main

import android.content.res.ColorStateList
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityMainBinding
import com.bondex.ysl.battledore.history.HistoryFragment
import com.bondex.ysl.battledore.list.ListFragment
import com.bondex.ysl.battledore.misssion.MissionFragment
import com.bondex.ysl.battledore.plan.PlanFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_title.*


class MainActivity : BaseActivity<MainViewModle, ActivityMainBinding>() {


    var selected: ColorStateList? = null
    var normal: ColorStateList? = null

    var pagerAdapter: MainPagerAdapter? = null


    override fun getReourceId(): Int {

        return R.layout.activity_main
    }

    override fun initView() {


        binding.maniViewModle = viewModel

        selected = resources.getColorStateList(R.color.rect_red)
        normal = resources.getColorStateList(R.color.text_gray)

        plan.setOnClickListener(listener)
        mission.setOnClickListener(listener)
        list.setOnClickListener(listener)
        history.setOnClickListener(listener)

        tv_plane.setOnClickListener(listener)
        tv_mission.setOnClickListener(listener)
        tv_list.setOnClickListener(listener)
        tv_history.setOnClickListener(listener)


        val list: MutableList<Fragment> = mutableListOf()

        val planFragment = PlanFragment()
        val missionFragment = MissionFragment()
        val listFragment = ListFragment()
        val historyFragment = HistoryFragment()

        list.add(planFragment)
        list.add(missionFragment)
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
    }


    override fun onMyClick(view: View?) {

        when (view?.id) {

            R.id.plan,
            R.id.tv_plane -> {
                changeBack(1)
                viewpager.setCurrentItem(0)
            }

            R.id.mission,
            R.id.tv_mission -> {
                changeBack(2)
                viewpager.setCurrentItem(1)

            }
            R.id.list,
            R.id.tv_list -> {
                changeBack(3)
                viewpager.setCurrentItem(2)

            }

            R.id.history,
            R.id.tv_history -> {
                changeBack(4)
                viewpager.setCurrentItem(3)

            }
        }
    }


    private fun changeBack(position: Int) {

        when (position) {

            1 -> {

                line_plan.visibility = View.VISIBLE
                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.INVISIBLE
                line_history.visibility = View.INVISIBLE

                tv_plane.setTextColor(selected)
                tv_mission.setTextColor(normal)
                tv_list.setTextColor(normal)
                tv_history.setTextColor(normal)

//                plan.setTextColor(selected)
//                mission.setTextColor(normal)
//                list.setTextColor(normal)
//                history.setTextColor(normal)
            }

            2 -> {


                line_plan.visibility = View.INVISIBLE
                line_mission.visibility = View.VISIBLE
                line_list.visibility = View.INVISIBLE
                line_history.visibility = View.INVISIBLE

                tv_plane.setTextColor(normal)
                tv_mission.setTextColor(selected)
                tv_list.setTextColor(normal)
                tv_history.setTextColor(normal)


//                plan.setTextColor(normal)
//                mission.setTextColor(selected)
//                list.setTextColor(normal)
//                history.setTextColor(normal)
            }
            3 -> {


                line_plan.visibility = View.INVISIBLE
                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.VISIBLE
                line_history.visibility = View.INVISIBLE

                tv_plane.setTextColor(normal)
                tv_mission.setTextColor(normal)
                tv_list.setTextColor(selected)
                tv_history.setTextColor(normal)


//                plan.setTextColor(normal)
//                mission.setTextColor(normal)
//                list.setTextColor(selected)
//                history.setTextColor(normal)
            }

            4 -> {


                line_plan.visibility = View.INVISIBLE
                line_mission.visibility = View.INVISIBLE
                line_list.visibility = View.INVISIBLE
                line_history.visibility = View.VISIBLE

                tv_plane.setTextColor(normal)
                tv_mission.setTextColor(normal)
                tv_list.setTextColor(normal)
                tv_history.setTextColor(selected)


//                plan.setTextColor(normal)
//                mission.setTextColor(normal)
//                list.setTextColor(normal)
//                history.setTextColor(selected)

            }


        }


    }


}
