package com.bondex.ysl.battledore.plan

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.goodslist.GoodsListActivity
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import com.bondex.ysl.battledore.workbench.WorkBetchActivity

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class PlanAdapter(lis: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var context: Context? = null

    protected var list: MutableList<String>? = lis


    fun updataList(list: MutableList<String>?){

        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_plan_layout, p0, false)

        val holder = ViewHodler(view)

        holder.ivRight.setOnClickListener { v ->

            val  intent = Intent(context,WorkBetchActivity::class.java)
            context?.startActivity(intent)
        }
        holder.btList.setOnClickListener { v ->


            val  intent = Intent(context,GoodsListActivity::class.java)
            context?.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount(): Int {

    return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
    }


    inner class ViewHodler(view: View) : RecyclerView.ViewHolder(view) {

         val tvDate:TextView = view.findViewById(R.id.item_plan_date)
         val tvRemain:TextView = view.findViewById(R.id.item_plan_remain)
        protected  val tvTotal:TextView = view.findViewById(R.id.item_plan_total)
        protected  val tvFlight:TextView = view.findViewById(R.id.item_plan_flight)
        protected  val tvPurpose:TextView = view.findViewById(R.id.item_plan_purpose)
          val ivRight:ImageView = view.findViewById(R.id.item_plan_arrow_right)
          val btList:Button = view.findViewById(R.id.item_plan_bt_goodslist)




    }

}