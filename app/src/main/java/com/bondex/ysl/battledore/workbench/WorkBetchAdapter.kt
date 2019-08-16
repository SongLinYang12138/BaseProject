package com.bondex.ysl.battledore.workbench

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.liblibrary.ui.IconText
import com.bondex.ysl.liblibrary.ui.ReduceAddEditText
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener

/**
 * date: 2019/6/13
 * Author: ysl
 * description:
 */

class WorkBetchAdapter(list: ArrayList<HAWBBean>) : RecyclerView.Adapter<WorkBetchAdapter.ViewHolder>() {

    private var context: Context? = null
    var list = list

    fun updateList(list: ArrayList<HAWBBean>) {

        this.list = list
        notifyDataSetChanged()
    }

    fun clear() {

        this.list?.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_work_betch_layout, parent, false)

        val holder = ViewHolder(view)

        holder.delete.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val position = v?.let { v.getTag() } as Int
                list.removeAt(position)

                notifyDataSetChanged()

            }
        })
        return holder
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.delete.setTag(position)

        val hawbBean = list.get(position)

        holder.tvMhawb.setText(hawbBean.getmBillCode())
        holder.tvhwab.setText(hawbBean.hawb)
        holder.tvQty.setText(hawbBean.qty.toString())
        if (hawbBean.qty > 0) holder.etLoad.editText.setText(hawbBean.qty.toString())
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvMhawb: TextView = view.findViewById(R.id.item_workbetch_main)
        val tvhwab: TextView = view.findViewById(R.id.item_workbetch_hawb)
        val etLoad: ReduceAddEditText = view.findViewById(R.id.item_workbetch_qty)
        val tvQty: TextView = view.findViewById(R.id.item_workbetch_all_qty)
        val delete: IconText = view.findViewById(R.id.item_workbetach_delete)


    }

}