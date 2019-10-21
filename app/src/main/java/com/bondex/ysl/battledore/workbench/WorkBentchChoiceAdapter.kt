package com.bondex.ysl.battledore.workbench

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
class WorkBentchChoiceAdapter(list: MutableList<WorkBentchChoiceBean>) :

    RecyclerView.Adapter<WorkBentchChoiceAdapter.ViewHolder>() {

    private var list = list

    private var context: Context? = null

    val selectList = arrayListOf<WorkBentchChoiceBean>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context

        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_workbench_layout, p0, false)

        val holder = ViewHolder(view)

        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                val position = holder.tv.getTag() as Int

                if (!TextUtils.isEmpty(s.toString())) {

                    try {
                        list.get(position).count = s.toString().toInt()


                        val index = selectList.indexOf(list.get(position))
                        if (index >= 0) {

                            selectList.set(index, list.get(position))
                        } else {

                            selectList.add(list.get(position))
                        }

                        if (s.toString().toInt() == 0) {

                            selectList.remove(list.get(position))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    list.get(position).count = 0
                    selectList.remove(list.get(position))
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

        holder.rde.editText.addTextChangedListener(textWatcher)


        return holder
    }

    fun updateList(list: MutableList<WorkBentchChoiceBean>) {

        this.list = list
        selectList.clear()
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

        p0.tv.setTag(p1)

        p0.tv.setText(bean.name)
        if (bean.count > 0) p0.rde.editText.setText(bean.count.toString())
        else if (bean.count == 0) p0.rde.editText.setText("")


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv = itemView.findViewById<TextView>(R.id.item_work_tv)
        val rde: ReduceAddEditText = itemView.findViewById(R.id.item_work_rde)

    }

}