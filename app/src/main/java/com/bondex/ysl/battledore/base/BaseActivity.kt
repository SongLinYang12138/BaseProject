package com.bondex.ysl.battledore.base

import android.databinding.ViewDataBinding
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.ui.IconText
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<A:AppCompatActivity,B:ViewDataBinding> : AppCompatActivity() {

    protected val listener = MyClickListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.setContentView(R.layout.activity_base)

        setToolBar()

        initData()
    }


    protected fun titleHide(isShow: Boolean){
        base_rl_title?.visibility = if(isShow)View.GONE else View.VISIBLE

    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        val view = layoutInflater.inflate(layoutResID, null)
        val lp: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        lp.addRule(RelativeLayout.BELOW, R.id.ll_basetitle_root)

        ll_basetitle_root?.addView(view, lp)
    }

    protected fun setToolBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            val flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                val attributes = window.attributes
                attributes.flags = attributes.flags or flagTranslucentNavigation

                window.attributes = attributes

                getWindow().statusBarColor = resources.getColor(R.color.rect_red)
            } else {
                val window = window
                val attributes = window.attributes
                attributes.flags = attributes.flags or (flagTranslucentStatus or flagTranslucentNavigation)
                window.attributes = attributes
            }
        }

    }



    open fun showLeft(isShow: Boolean, listener: View.OnClickListener?) {

        base_back?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null) base_back.setOnClickListener(listener)

    }

    open fun showTitle(isShow: Boolean, titl: String?) {

        base_title?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (!TextUtils.isEmpty(titl)) base_title?.setText(titl)

    }


    open fun showRight(isShow: Boolean, listener: View.OnClickListener?, resourceId: Int) {

        base_right?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null)
            base_right?.setOnClickListener(listener)
        if (resourceId != 0) base_right?.setText(resourceId)
    }

    open abstract fun onCLick(v: View)
    open abstract fun initData()

    protected class MyClickListener : NoDoubleClickListener() {
        override fun click(v: View?) {

            onClick(v)
        }

    }

}
