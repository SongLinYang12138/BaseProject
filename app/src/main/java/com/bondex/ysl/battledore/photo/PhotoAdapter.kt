package com.bondex.ysl.battledore.photo

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.bondex.ysl.databaselibrary.hawb.img.HAWBImgBean
import com.bumptech.glide.Glide
import java.io.File

/**
 * date: 2019/8/23
 * Author: ysl
 * description:
 */

class PhotoAdapter(list: ArrayList<HAWBImgBean>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var context: Context? = null
     var list = list
    val selectList = arrayListOf<HAWBImgBean>()

    fun updateList(list: ArrayList<HAWBImgBean>) {

        this.list = list
        selectList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        context = p0.context
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_photo_layout, p0, false)
        val holder = ViewHolder(view)

        holder.ck.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                if (buttonView?.tag == null) return


                val position = buttonView.tag as Int

                if (isChecked) {
                    selectList.add(list.get(position))
                } else {
                    selectList.remove(list.get(position))
                }
            }
        })

        return holder

    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.ck.tag = p1
        val bean = list.get(p1)
        val file = File(bean.path)
        context?.let { Glide.with(context!!).load(file).into(p0.iv) }
        p0.ck.isChecked = selectList.contains(list.get(p1))

        p0.tv.text = bean.hawb


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val iv: ImageView = itemView.findViewById(R.id.item_photo_iv)
        val ck: CheckBox = itemView.findViewById(R.id.item_photo_ck)
        val tv: TextView = itemView.findViewById(R.id.item_photo_hwab)
    }

}