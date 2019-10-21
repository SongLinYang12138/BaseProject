package com.bondex.ysl.battledore.battledoredetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.databaselibrary.plan.WorkBentchChoiceBean
import com.bondex.ysl.liblibrary.ui.ReduceAddEditText
import java.lang.Exception

/**
 * date: 2019/7/10
 * Author: ysl
 * description:
 */
class BattleDoreDetailAdapter(list: MutableList<WorkBentchChoiceBean>) :

    RecyclerView.Adapter<BattleDoreDetailAdapter.ViewHolder>() {

    private var list = list

    private var context: Context? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_detail_protect_layout, p0, false)

        val holder = ViewHolder(view)




        return holder
    }

    fun updateList(list: MutableList<WorkBentchChoiceBean>) {

        this.list = list
        notifyDataSetChanged()
    }

    fun clear() {

        this.list.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val bean: WorkBentchChoiceBean = list.get(p1)


        p0.name.text = bean.name
        p0.count.text = bean.count.toString()


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.item_detail_tv_name)
        val count: TextView = itemView.findViewById(R.id.item_detail_tv_count)

    }

}