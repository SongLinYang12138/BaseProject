package com.bondex.ysl.battledore.list


import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentListBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    override fun initView() {

    }

    override fun getResourceId(): Int {

        return R.layout.fragment_list

    }

    override fun myClick(view: View?) {

    }


}
