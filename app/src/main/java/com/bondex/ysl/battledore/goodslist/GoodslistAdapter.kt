package com.bondex.ysl.battledore.goodslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondex.ysl.battledore.R

/**
 * date: 2019/6/14
 * Author: ysl
 * description:
 */
class GoodslistAdapter(list: MutableList<String>) : RecyclerView.Adapter<GoodslistAdapter.ViewHolder>() {

    var context: Context? = null
    var list = list

    fun updateList(list: MutableList<String>) {

        this.list = list
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

}