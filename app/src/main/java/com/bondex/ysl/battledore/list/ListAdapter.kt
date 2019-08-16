package com.bondex.ysl.battledore.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.bondex.ysl.battledore.R

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class ListAdapter(lis: MutableList<ListBean>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    protected var context: Context? = null

    protected var list: MutableList<ListBean>? = lis


    fun updataList(list: MutableList<ListBean>) {

        this.list = list

        notifyDataSetChanged()
    }

    fun checkAll(isSelect: Boolean) {


        for (i in list?.indices ?: mutableListOf<ListBean>()) {

            list?.get(i as Int)?.isSelect = isSelect
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_list_layout, p0, false)
        val holder =ViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {


        val flag = list?.get(p1)?.isSelect ?: false
        holder.checkBox.isChecked = flag

        if(p1 == 0){
            holder.tvStatus.setText("已完成")
        }else if(p1 == 1){
            holder.tvStatus.setText("提交失败")
        }else{
            holder.tvStatus.setText("未完成")

        }


    }


   inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val checkBox: CheckBox = view.findViewById(R.id.item_list_ck)

       val tvStatus:TextView = view.findViewById(R.id.item_list_board_status)
//        private val tvDate:TextView = view.findViewById(R.id.item_plan_date)
//        private val tvRemain:TextView = view.findViewById(R.id.item_plan_remain)
//        private  val tvTotal:TextView = view.findViewById(R.id.item_plan_total)
//        private  val tvFlight:TextView = view.findViewById(R.id.item_plan_flight)
//        private  val tvPurpose:TextView = view.findViewById(R.id.item_plan_purpose)
//        private  val ivRight:ImageView = view.findViewById(R.id.item_plan_arrow_right)
//        private  val btList:Button = view.findViewById(R.id.item_plan_bt_goodslist)


    }

}