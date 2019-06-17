package com.bondex.ysl.battledore.workbench

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondex.ysl.battledore.R
import kotlinx.android.synthetic.main.listview_item_work_betch_layout.*

/**
 * date: 2019/6/13
 * Author: ysl
 * description:
 */

class WorkBetchAdapter(list: MutableList<String>) : RecyclerView.Adapter<WorkBetchAdapter.ViewHolder>() {

    var context: Context? = null
    var list = list

    fun updateList(list: MutableList<String>) {

        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_work_betch_layout, parent, false)

        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {




    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

}