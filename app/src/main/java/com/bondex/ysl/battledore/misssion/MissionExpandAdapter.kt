package com.bondex.ysl.battledore.misssion

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.bondex.ysl.battledore.R
import com.gc.materialdesign.views.CheckBox
import org.w3c.dom.Text

/**
 * date: 2019/6/5
 * Author: ysl
 * description:
 */

class MissionExpandAdapter(list: MutableList<MissionBean>) : BaseExpandableListAdapter() {

    private var list: MutableList<MissionBean>? = list
    private var context: Context? = null

    override fun getGroup(groupPosition: Int): MissionBean? {

        return list?.get(groupPosition)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {

        return false
    }

    override fun hasStableIds(): Boolean {

        return false
    }


    override fun getChildrenCount(groupPosition: Int): Int {

        return list?.get(groupPosition)?.hawbs?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        return list?.get(groupPosition)?.hawbs?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {

        return groupPosition.toLong()
    }


    private class GroupHolder {

        var ckAll: CheckBox? = null
        var hawbHead: TextView? = null
        var qtyHead: TextView? = null
        var flightHead: TextView? = null
        var dateHead: TextView? = null
        var detailHead: TextView? = null
        var ckMain: CheckBox? = null
        var tvMain: TextView? = null

        init {

        }


    }

    private var groupHolder: GroupHolder? = null


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {


        if (convertView == null) {
            context = parent?.context



          val  groupView = LayoutInflater.from(context).inflate(R.layout.listview_item_mission_layout, parent, false)
            groupHolder = GroupHolder()

            groupHolder!!.ckAll = groupView!!.findViewById(R.id.item_mission_ck_all)
            groupHolder!!.hawbHead = groupView!!.findViewById(R.id.item_mission_tv_hawb)
            groupHolder!!.qtyHead = groupView!!.findViewById(R.id.item_mission_tv_total_qty)
            groupHolder!!.flightHead = groupView!!.findViewById(R.id.item_mission_tv_total_flight)
            groupHolder!!.dateHead = groupView!!.findViewById(R.id.item_mission_date)
            groupHolder!!.detailHead = groupView!!.findViewById(R.id.item_mission_detail)
            groupHolder!!.ckMain = groupView!!.findViewById(R.id.item_mission_ck_main)
            groupHolder!!.tvMain = groupView!!.findViewById(R.id.item_mission_main)

hideHeadView(false)

            return groupView
        } else {

            hideHeadView(true)



            return convertView
        }
    }

    private fun hideHeadView(isHide:Boolean){


        groupHolder?.ckAll?.visibility = if(isHide) View.GONE else View.VISIBLE
        groupHolder?.hawbHead?.visibility = if(isHide) View.GONE else View.VISIBLE
        groupHolder?.qtyHead?.visibility = if(isHide) View.GONE else View.VISIBLE
        groupHolder?.flightHead?.visibility = if(isHide) View.GONE else View.VISIBLE
        groupHolder?.dateHead?.visibility = if(isHide) View.GONE else View.VISIBLE
        groupHolder?.ckMain?.visibility = if(isHide) View.GONE else View.VISIBLE

    }


    override fun getChildView(
        groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?,
        parent: ViewGroup?
    ): View {


        return LayoutInflater.from(context).inflate(R.layout.item_mission_list_item_layout, parent, false)
    }


    override fun getChildId(groupPosition: Int, childPosition: Int): Long {

        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return list?.size ?: 0
    }


}