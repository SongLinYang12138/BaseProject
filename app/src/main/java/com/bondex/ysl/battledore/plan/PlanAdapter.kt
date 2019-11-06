package com.bondex.ysl.battledore.plan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.addhawb.AddHawbActivity
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.liblibrary.ui.IconText
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener

/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class PlanAdapter(lis: ArrayList<PlanBean>) :
    RecyclerView.Adapter<PlanAdapter.ViewHodler>() {

    protected var context: Context? = null
    protected var list: ArrayList<PlanBean>? = lis
    private val selectedMap = ArrayMap<Int, Boolean>()
    private var selectAll = false
    private val selectList = arrayListOf<PlanBean>()

    fun updataList(list: ArrayList<PlanBean>) {

        this.list = list
        notifyDataSetChanged()
    }

    fun isAll(select: Boolean) {

        this.selectAll = select
        if (!select) {

            selectList.clear()
            for (it in selectedMap) {
                selectedMap.set(it.key, false)
            }

        }
        notifyDataSetChanged()
    }

    fun getSelected(): ArrayList<PlanBean> = selectList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHodler {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_plan_layout, p0, false)
        val holder = ViewHodler(view)


        holder.ck.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                val position = buttonView?.getTag() as Int
                selectedMap.put(position, isChecked)

                if (isChecked) {

                    list?.get(position)?.let { selectList.add(it) }
                } else {

                    list?.get(position)?.let { selectList.remove(it) }
                }

            }
        })

        holder.itAdd.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {

                val position = v?.getTag() as Int
                val intent = Intent(context, AddHawbActivity::class.java)
                intent.putExtra("title", "货物清单")

                val bundle = Bundle()
                bundle.putParcelable(Constant.PLAN_BEAN_KEY, list?.get(position))
                intent.putExtras(bundle)
                context?.startActivity(intent)

            }
        })

        holder.itContent.setOnClickListener(object : NoDoubleClickListener() {
            override fun click(v: View?) {


                val position = v?.getTag() as Int
                val intent = Intent(context, WorkBetchActivity::class.java)

                val bundle = Bundle()
                val selectedList = list?.get(position)?.let { arrayListOf<PlanBean>(it) }
                bundle.putParcelableArrayList(Constant.PLAN_BEAN_KEY, selectedList)
                intent.putExtras(bundle)
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
        hodler.itContent.setTag(p1)
        hodler.ck.setTag(p1)

        val planBean = list?.get(p1)
        if (planBean != null) {

            hodler.tvAll.setText(planBean.getmBillTotal()+"M" + planBean.hawbTotal+"H")
            hodler.tvQty.setText(planBean.qtyTotal.toString() + "件")
            hodler.tvWeight.setText(planBean.weightTotal + "kg" + planBean.volumeTotal + "m³")
            hodler.tvFlight.setText(planBean.flight)
            hodler.tvDestination.setText(planBean.destination)
            hodler.tvDate.setText("航班日期" + planBean.flghtDate)
            hodler.itId.setText("ID: "+planBean.id)

            if (selectAll) {
                hodler.ck.isChecked = true
            } else if (selectedMap.contains(p1)) {
                hodler.ck.isChecked = selectedMap.get(p1)!!
            } else hodler.ck.isChecked = false
        }
    }

    inner class ViewHodler(view: View) : RecyclerView.ViewHolder(view) {


        val itId:TextView = view.findViewById(R.id.item_plan_id)
        val itAdd: IconText = view.findViewById(R.id.item_plan_add)
        val itContent: ConstraintLayout = view.findViewById(R.id.plan_cl_content)
        val tvAll: TextView = view.findViewById(R.id.item_plan_all)
        val tvQty: TextView = view.findViewById(R.id.item_plan_qty)
        val tvWeight: TextView = view.findViewById(R.id.item_plan_weight)
        val tvFlight: TextView = view.findViewById(R.id.item_plan_flight)
        val tvDestination: TextView = view.findViewById(R.id.item_plan_destination)
        val tvDate: TextView = view.findViewById(R.id.item_plan_date)
        val ck: CheckBox = view.findViewById(R.id.item_plan_ck)

    }

}