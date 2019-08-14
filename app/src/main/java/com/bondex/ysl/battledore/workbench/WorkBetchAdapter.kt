package com.bondex.ysl.battledore.workbench

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondex.ysl.battledore.R

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


//        if(position != 0){
//
//
//            holder.unitMain.visibility = View.GONE
//            holder.unitHawb.visibility = View.GONE
//            holder.unitQty.visibility = View.GONE
//            holder.unitAllQty.visibility = View.GONE
//            holder.unitFlight.visibility = View.GONE
//
//        }else{
//
//            holder.unitMain.visibility = View.VISIBLE
//            holder.unitHawb.visibility = View.VISIBLE
//            holder.unitQty.visibility = View.VISIBLE
//            holder.unitAllQty.visibility = View.VISIBLE
//            holder.unitFlight.visibility = View.VISIBLE
//
//        }




    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


//        val unitMain:TextView = view.findViewById(R.id.work_unit_main)
//        val unitHawb:TextView = view.findViewById(R.id.work_unit_hawb)
//        val unitQty:TextView = view.findViewById(R.id.work_unit_qty)
//        val unitAllQty:TextView = view.findViewById(R.id.work_unit_all_qty)
//        val unitFlight:TextView = view.findViewById(R.id.work_unit_flight)

    }

}