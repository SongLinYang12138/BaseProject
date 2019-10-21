package com.bondex.ysl.battledore.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class ListAdapter(lis: ArrayList<PlanBean>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    protected var context: Context? = null

    protected var list: ArrayList<PlanBean> = lis
    val selectMap = mutableMapOf<Int, Boolean>()
    val selectList = arrayListOf<PlanBean>()
    private var selectAll: Boolean = false
    fun updataList(list: ArrayList<PlanBean>) {

        this.list = list
        selectMap.clear()
        selectList.clear()

        notifyDataSetChanged()
    }

    fun checkAll(isSelect: Boolean) {

        this.selectAll = isSelect

        if (!isSelect) {
            selectList.clear()

            for (i in list.indices) {
                selectMap.put(i, false)
            }
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_list_layout, p0, false)
        val holder = ViewHolder(view)

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            val posi = buttonView.getTag() as Int

            selectMap.put(posi, isChecked)

            if (isChecked) {

                selectList.add(list.get(posi))
            } else {

                selectList.remove(list.get(posi))
            }

        }

        holder.content.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val posi = v?.getTag() as Int
                val intent = Intent(context, WorkBetchActivity::class.java)

                val bundle = Bundle()

                val tmpList = arrayListOf<PlanBean>(list.get(posi))
                bundle.putParcelableArrayList(Constant.PLAN_BEAN_KEY, tmpList)
                intent.putExtras(bundle)
                context?.startActivity(intent)

            }
        })

        return holder
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        holder.content.setTag(p1)
        holder.checkBox.setTag(p1)
        if (selectAll) {

            holder.checkBox.isChecked = true
        } else if (selectMap.containsKey(p1)) {
            holder.checkBox.isChecked = selectMap.get(p1)!!
        } else holder.checkBox.isChecked = false



        list?.get(p1)?.let {

            val planBean = list!!.get(p1)
            holder.tvBoardNum.text = planBean.boardNum
            holder.tvTotal.text = planBean.getmBillTotal() + "M" + planBean.hawbTotal + "H"
            holder.tvQty.text = "(" + planBean.qtyTotal.toString() + ")"
            holder.tvDestination.text = "(${planBean.destination})"
            holder.flight.text = planBean.flight
            holder.date.text = "打板日期： " + planBean.battleDate
//            0, 提交失败 1 提交成功 2 未提交 3未完成的单子

            if (it.status == 0) {
                holder.tvStatus.setText("提交失败")

            } else if (it.status == 2) {
                holder.tvStatus.setText("已完成")

            } else if (it.status == 3) {
                holder.tvStatus.setText("未完成")


            }
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val checkBox: CheckBox = view.findViewById(R.id.item_list_ck)

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