package com.bondex.ysl.battledore.goodslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.bondex.ysl.battledore.R

/**
 * date: 2019/6/14
 * Author: ysl
 * description:
 */
class GoodslistAdapter(list: MutableList<GoodsListBean>) : RecyclerView.Adapter<GoodslistAdapter.ViewHolder>() {

    var context: Context? = null
    var list = list

    fun updateList(list: MutableList<GoodsListBean>) {

        this.list = list
        notifyDataSetChanged()
    }

    fun checkAll(isSelect: Boolean) {

        for (i in list.indices) {

            list[i].isSelect = isSelect
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_goods_list_layout, parent, false)

        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        viewHolder.cb.isChecked = list[position].isSelect


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cb: CheckBox = view.findViewById(R.id.item_goodslist_ck)


    }

}