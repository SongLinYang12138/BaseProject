package com.bondex.ysl.battledore.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.battledoredetail.BattleDoreDetailActivity
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class HistoryAdapter(lis: ArrayList<PlanBean>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    protected var context: Context? = null

    protected var list: ArrayList<PlanBean> = lis
    private var selectAll: Boolean = false
    fun updataList(list: ArrayList<PlanBean>) {

        this.list = list

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_history_layout, p0, false)
        val holder = ViewHolder(view)


        holder.content.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val posi = v?.getTag() as Int
                val intent = Intent(context, BattleDoreDetailActivity::class.java)


                intent.putExtra(Constant.PLAN_BEAN_KEY,list.get(posi))
                context?.startActivity(intent)

            }
        })

        return holder
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        holder.content.setTag(p1)

        holder.tvStatus.setText("提交成功")

        list?.get(p1)?.let {

            val planBean = list!!.get(p1)
            holder.tvBoardNum.text = planBean.boardNum
            holder.tvTotal.text = planBean.getmBillTotal() + "" + planBean.hawbTotal
            holder.tvQty.text = "(" + planBean.qtyTotal.toString() + ")"
            holder.tvDestination.text = "(${planBean.destination})"
            holder.flight.text = planBean.flight
            holder.date.text = "打板日期： " + planBean.battleDate
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvStatus: TextView = view.findViewById(R.id.item_list_board_status)
        val tvBoardNum: TextView = view.findViewById(R.id.item_list_board)
        val tvTotal: TextView = view.findViewById(R.id.item_list_count)
        val tvQty: TextView = view.findViewById(R.id.item_list_qty)
        val tvDestination: TextView = view.findViewById(R.id.item_list_purpose)
        val flight: TextView = view.findViewById(R.id.item_list_flight)
        val date: TextView = view.findViewById(R.id.item_list_board_date)
        val content: ConstraintLayout = view.findViewById(R.id.item_list_content)


    }

}