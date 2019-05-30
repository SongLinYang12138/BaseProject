package com.bondex.ysl.battledore.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * date: 2019/5/29
 * Author: ysl
 * description:
 */
class MainPagerAdapter(fm: FragmentManager?, list: List<Fragment>) : FragmentPagerAdapter(fm) {

    private var list: List<Fragment> = list


    override fun getItem(p0: Int): Fragment {


        return list.get(p0)
    }

    override fun getCount(): Int {

        return list.size
    }

}