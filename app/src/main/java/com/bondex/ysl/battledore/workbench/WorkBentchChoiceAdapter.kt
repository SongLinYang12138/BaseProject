package com.bondex.ysl.battledore.workbench

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.liblibrary.ui.ReduceAddEditText

/**
 * date: 2019/7/10
 * Author: ysl
 * description:
 */
class WorkBentchChoiceAdapter(list: MutableList<WorkBentchChoiceBean>) : RecyclerView.Adapter<WorkBentchChoiceAdapter.ViewHolder>() {

    var list = list

    var context: Context? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_workbench_layout, p0, false)

        val holder = ViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val bean:WorkBentchChoiceBean = list.get(p1)

        p0.tv.setText(bean.name)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv = itemView.findViewById<TextView>(R.id.item_work_tv)
        val rde: ReduceAddEditText = itemView.findViewById(R.id.item_work_rde)

    }

}