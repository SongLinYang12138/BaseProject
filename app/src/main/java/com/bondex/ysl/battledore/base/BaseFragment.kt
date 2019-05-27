package com.bondex.ysl.battledore.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondex.ysl.battledore.util.NoDoubleClickListener

/**
 * date: 2019/5/24
 * Author: ysl
 * description:
 */
open  abstract class BaseFragment : Fragment(){

      protected val listener = MyClickListener()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutId(),container,false)
    }

    open abstract fun layoutId():Int




    open abstract  fun onClick(v:View)

     protected class MyClickListener : NoDoubleClickListener(){
        override fun click(v: View?) {
        onClick(v)
        }

    }

}