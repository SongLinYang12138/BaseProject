package com.bondex.ysl.battledore.history

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.battledoredetail.BattleDoreDetailActivity

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class HistoryAdapter(lis: MutableList<String>, activity: FragmentActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var context: Context? = null

    protected var list: MutableList<String>? = lis
    private val activity = activity


    fun updataList(list: MutableList<String>?) {

        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_history_layout, p0, false)

        val holder = ViewHodler(view)

        holder.clContent.setOnClickListener { v ->

            val intent = Intent(context, BattleDoreDetailActivity::class.java)
            context?.startActivity(intent)
            activity.overridePendingTransition(R.anim.window_in, R.anim.window_out)
        }

        return holder
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
    }


    protected inner class ViewHodler(view: View) : RecyclerView.ViewHolder(view) {

        val clContent: ConstraintLayout = view.findViewById(R.id.item_list_content)


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