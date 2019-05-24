package com.bondex.ysl.battledore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.bondex.ysl.battledore.base.BaseActivity
import com.orhanobut.logger.Logger
import android.support.v7.app.ActionBar
import android.view.Gravity




class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showLeft(true,object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        Logger.i("itemselected "+item?.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onCLick(v: View) {

    }

    override fun initData() {


    }

}
