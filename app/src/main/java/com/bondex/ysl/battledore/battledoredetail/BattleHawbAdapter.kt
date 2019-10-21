package com.bondex.ysl.battledore.battledoredetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.databaselibrary.hawb.HAWBBean

/**
 * date: 2019/6/13
 * Author: ysl
 * description:
 */

class BattleHawbAdapter(list: ArrayList<HAWBBean>) : RecyclerView.Adapter<BattleHawbAdapter.ViewHolder>() {

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
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_detail_hawb_layout, parent, false)

        val holder = ViewHolder(view)


        return holder
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val hawbBean = list.get(position)

        holder.tvMhawb.setText(hawbBean.getmBillCode())
        holder.tvhwab.setText(hawbBean.hawb)
        holder.tvQty.setText(hawbBean.qty.toString())
        holder.etLoad.text = hawbBean.loadQty.toString()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvMhawb: TextView = view.findViewById(R.id.item_detail_main)
        val tvhwab: TextView = view.findViewById(R.id.item_detail_hawb)
        val etLoad: TextView = view.findViewById(R.id.item_detail_qty)
        val tvQty: TextView = view.findViewById(R.id.item_detail_all_qty)


    }

}