package com.bondex.ysl.battledore.plan

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.addhawb.AddHawbActivity
import com.bondex.ysl.battledore.goodslist.GoodsListActivity
import com.bondex.ysl.battledore.util.ToastUtils
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.liblibrary.ui.IconText
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener
import org.w3c.dom.Text

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class PlanAdapter(lis: ArrayList<PlanBean>, activity: FragmentActivity) :
    RecyclerView.Adapter<PlanAdapter.ViewHodler>() {


    protected var context: Context? = null

    protected var list: ArrayList<PlanBean>? = lis
    private val activity = activity


    fun updataList(list: ArrayList<PlanBean>) {

        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHodler {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_plan_layout, p0, false)
        val holder = ViewHodler(view)


        holder.itAdd.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val intent = Intent(context, AddHawbActivity::class.java)
                intent.putExtra("title", "货物清单")
                context?.startActivity(intent)

            }
        })

        holder.itContent.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val intent = Intent(context, WorkBetchActivity::class.java)
                context?.startActivity(intent)
            }

        })
        return holder
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    override fun onBindViewHolder(hodler: ViewHodler, p1: Int) {

        hodler.itAdd.setTag(p1)
        val planBean = list?.get(p1)
        if (planBean != null) {

            hodler.tvAll.setText(planBean.getmBillTotal() + planBean.hawbTotal)
            hodler.tvQty.setText(planBean.qtyTotal.toString() + "件")
            hodler.tvWeight.setText(planBean.weightTotal + "kg" + planBean.volumeTotal + "m³")
            hodler.tvFlight.setText(planBean.flight)
            hodler.tvDestination.setText(planBean.destination)
            hodler.tvDate.setText("航班日期" + planBean.flghtDate)
        }
    }

    inner class ViewHodler(view: View) : RecyclerView.ViewHolder(view) {


        val itAdd: IconText = view.findViewById(R.id.item_plan_add)
        val itContent: ConstraintLayout = view.findViewById(R.id.plan_cl_content)
        val tvAll: TextView = view.findViewById(R.id.item_plan_all)
        val tvQty: TextView = view.findViewById(R.id.item_plan_qty)
        val tvWeight: TextView = view.findViewById(R.id.item_plan_weight)
        val tvFlight: TextView = view.findViewById(R.id.item_plan_flight)
        val tvDestination: TextView = view.findViewById(R.id.item_plan_destination)
        val tvDate: TextView = view.findViewById(R.id.item_plan_date)

    }

}