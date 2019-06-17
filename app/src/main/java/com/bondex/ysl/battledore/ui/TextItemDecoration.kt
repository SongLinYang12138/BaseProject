package com.bondex.ysl.battledore.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView



/**
 * date: 2019/6/4
 * Author: ysl
 * description:
 */

class TextItemDecoration : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {


        paint.color = Color.GRAY
        paint.style = Paint.Style.FILL
    }



    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val  count: Int = parent.childCount
        for (i in 0 .. count-1){

            val child = parent.getChildAt(i)
            val left = child.left
            val rights = child.right
            val top = child.bottom
            val  bottom = child.bottom+1
            c.drawRect(left.toFloat(), top.toFloat(), rights.toFloat(), bottom.toFloat(),paint)

        }

    }

}

