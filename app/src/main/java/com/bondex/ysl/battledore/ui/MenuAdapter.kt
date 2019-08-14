package com.bondex.ysl.battledore.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.liblibrary.ui.IconText

/**
 * date: 2019/7/11
 * Author: ysl
 * description:
 */
class MenuAdapter(list: MutableList<String>, context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    var list = list
    val context = context
    var selectPosition = -1
    var select: ColorStateList? = null
    var normal: ColorStateList? = null
    var animation: Animation? = null
    var animationListenr: Animation.AnimationListener? = null

    var menuClick: MenuList.MenuClick? = null


    fun updateList(list: MutableList<String>) {

        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_item_menu_layout, p0, false)

        select = context.resources.getColorStateList(R.color.rect_red)

        normal = context.resources.getColorStateList(R.color.text_black)

        val holder: ViewHolder = ViewHolder(view)

        animation = AnimationUtils.loadAnimation(context, com.bondex.ysl.liblibrary.R.anim.scale_retotate)


        animationListenr = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        }


        animation?.setAnimationListener(animationListenr)

        holder.tv.setOnClickListener {

            val position = holder.tv.getTag() as Int
            selectPosition = position
            holder.it.startAnimation(animation)
            menuClick?.menuChoice(selectPosition, list.get(selectPosition))
            notifyDataSetChanged()
        }



        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        p0.tv.setText(list.get(p1))
        p0.tv.setTag(p1)

        if (p1 == selectPosition) {

            p0.tv.setTextColor(select)
            p0.it.startAnimation(animation)
            p0.it.setText(R.string.full_down)

        } else {

            p0.tv.setTextColor(normal)
            p0.it.setText(R.string.full_left)
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv: TextView = itemView.findViewById(R.id.menu_item_tv)
        val it: IconText = itemView.findViewById(R.id.menu_item_it)
    }
}