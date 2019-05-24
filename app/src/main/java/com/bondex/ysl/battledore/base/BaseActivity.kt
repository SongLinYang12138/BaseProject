package com.bondex.ysl.battledore.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    protected val listener = MyClickListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        initData()
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

    open fun showLeft(isShow: Boolean, listener: View.OnClickListener) {

        base_back?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null) base_back?.setOnClickListener(listener)
    }

    open fun showTitle(isShow: Boolean, title: String) {

        base_title?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (!TextUtils.isEmpty(title)) base_title?.setText(title)

    }


    open fun showRight(isShow: Boolean, listener: View.OnClickListener, resourceId: Int) {

        base_right?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE

        if (listener != null)
            base_right?.setOnClickListener(listener)
        if (resourceId != 0) base_right.setText(resourceId)
    }

   open abstract fun onCLick(v:View)
    open abstract fun  initData()

  protected class MyClickListener : NoDoubleClickListener(){
        override fun click(v: View?) {

            onClick(v)
        }

    }

}
