package com.bondex.ysl.battledore.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.ui.IconText
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {
    protected val listener = MyClickListener()

    var left: ImageView? = null
    var titles: TextView? = null
    var right: IconText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomActionBar()

        initData()
    }

    private fun setCustomActionBar() {
        val lp: ActionBar.LayoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        val mActionBarView = LayoutInflater.from(this).inflate(R.layout.base_title, null)
        val actionBar = supportActionBar
        actionBar?.setCustomView(mActionBarView, lp)
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setDisplayShowTitleEnabled(false)

        left = actionBar?.customView?.findViewById(R.id.base_back)
        titles = actionBar?.customView?.findViewById(R.id.base_title)
        right = actionBar?.customView?.findViewById(R.id.base_right)

        left?.visibility = View.INVISIBLE
        titles?.visibility = View.INVISIBLE
        right?.visibility = View.INVISIBLE

    }


    open fun showLeft(isShow: Boolean, listener: View.OnClickListener) {

        left?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null) left?.setOnClickListener(listener)
    }

    open fun showTitle(isShow: Boolean, title: String) {

        titles?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (!TextUtils.isEmpty(title)) titles?.setText(title)

    }


    open fun showRight(isShow: Boolean, listener: View.OnClickListener, resourceId: Int) {

        right?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null)
            right?.setOnClickListener(listener)
        if (resourceId != 0) right?.setText(resourceId)
    }

    open abstract fun onCLick(v: View)
    open abstract fun initData()

    protected class MyClickListener : NoDoubleClickListener() {
        override fun click(v: View?) {

            onClick(v)
        }

    }

}
