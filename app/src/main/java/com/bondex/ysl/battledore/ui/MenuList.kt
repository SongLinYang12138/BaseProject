package com.bondex.ysl.battledore.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.bondex.ysl.battledore.R


/**
 * date: 2019/7/10
 * Author: ysl
 * description:
 */
class MenuList : ConstraintLayout {

    var list: MutableList<String> = mutableListOf()
    var recyclerView: RecyclerView? = null
    val adapter: MenuAdapter = MenuAdapter(list, context)


    //继承三个构造方法
    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        View.inflate(context, R.layout.menu_item_layout, this)

        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView = findViewById(R.id.menu_recyclerview)
        recyclerView?.layoutManager = manager
        recyclerView?.adapter = adapter
    }

    fun setOnMenuClick(click:MenuClick){

        adapter.menuClick = click

    }

    fun addData(list: MutableList<String>) {

        this.list = list
        adapter.updateList(list)
    }


     interface MenuClick{

         fun menuChoice(position:Int,menu:String)
    }



}
