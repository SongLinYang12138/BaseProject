package com.bondex.ysl.battledore.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.util.NoDoubleClickListener

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class ListAdapter(lis: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var context: Context? = null

    protected var list: MutableList<String>? = lis


    fun updataList(list: MutableList<String>?){

        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_list_layout, p0, false)

        val holder = ViewHodler(view)

        return holder
    }

    override fun getItemCount(): Int {

    return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
    }


    protected class ViewHodler(view: View) : RecyclerView.ViewHolder(view) {

//        private val tvDate:TextView = view.findViewById(R.id.item_plan_date)
//        private val tvRemain:TextView = view.findViewById(R.id.item_plan_remain)
//        private  val tvTotal:TextView = view.findViewById(R.id.item_plan_total)
//        private  val tvFlight:TextView = view.findViewById(R.id.item_plan_flight)
//        private  val tvPurpose:TextView = view.findViewById(R.id.item_plan_purpose)
//        private  val ivRight:ImageView = view.findViewById(R.id.item_plan_arrow_right)
//        private  val btList:Button = view.findViewById(R.id.item_plan_bt_goodslist)

        init {



        }


    }

}