package com.bondex.ysl.battledore.history


import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentHistoryBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : BaseFragment<HistoryViewModel,FragmentHistoryBinding>() {
    override fun initView() {

    }

    override fun getResourceId(): Int {

        return R.layout.fragment_history
    }

    override fun myClick(view: View?) {
    }


}
