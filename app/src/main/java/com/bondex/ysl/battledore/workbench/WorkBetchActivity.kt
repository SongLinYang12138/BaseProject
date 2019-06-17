package com.bondex.ysl.battledore.workbench

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityWorkBetchBinding
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_work_betch.*

class WorkBetchActivity : BaseActivity<WorkBetchViewModle, ActivityWorkBetchBinding>() {

    val list = mutableListOf<String>()
    val adapter = WorkBetchAdapter(list)
    override fun initView() {

        showLeft(true, object : NoDoubleClickListener() {
            override fun click(v: View?) {
                finish()
            }

        })
        showTitle("打板工作台")


        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        adapter.updateList(list)

        val manager = LinearLayoutManager(this)
        work_recycleview.layoutManager = manager
        work_recycleview.adapter = adapter
    }

    override fun onMyClick(view: View?) {
    }

    override fun getReourceId(): Int {

        return R.layout.activity_work_betch
    }

}


