package com.bondex.ysl.battledore.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityMainBinding
import com.orhanobut.logger.Logger


class MainActivity : BaseActivity() {
//    var plan: IconText? = null
//    var mission: IconText? = null
//    var list: IconText? = null
//    var history: IconText? = null
//    var line: View? = null
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       val data = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        setContentView(R.layout.activity_main)

        showLeft(false, null)

        titleHide(true)
//        supportActionBar?.hide()
    }

    private fun setCutomActionBar() {

//        val actionBar = supportActionBar
//        actionBar?.setCustomView(R.layout.main_title)

//        plan = actionBar?.customView?.findViewById(R.id.plan)
//        mission = actionBar?.customView?.findViewById(R.id.mission)
//        list = actionBar?.customView?.findViewById(R.id.list)
//        history = actionBar?.customView?.findViewById(R.id.history)
//        line = actionBar?.customView?.findViewById(R.id.line)


//        plan?.setOnClickListener({
//            setLineAnmation()
//        })
    }


//    private fun setLineAnmation() {
//
//
//        val planX = list?.left?.toFloat() ?: 4F
////        val planWidth = mission?.width?.toFloat() ?:4F
//
//        val tanslate = planX-line_plan.width/2-list.width/2
//
//
//        val startX = line_plan?.left?.toFloat() ?: 5F
//
//        Logger.i("planx "+planX+"  startX "+startX)
//
////        val animator1 = ObjectAnimator.ofFloat(line, "translationX", startX,planX)
//
//        val animator1:ObjectAnimator = ObjectAnimator.ofFloat(line_plan,"translationX",startX,tanslate)
//        animator1.duration = 1000
//        animator1.start()
//
//
//    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        Logger.i("itemselected " + item?.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onCLick(v: View) {

    }

    override fun initData() {


    }

}
