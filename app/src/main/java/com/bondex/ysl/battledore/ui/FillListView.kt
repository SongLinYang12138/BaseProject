package com.bondex.ysl.battledore.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.ListView

/**
 * date: 2019/6/5
 * Author: ysl
 * description:
 */
class FillListView(context: Context, attrs: AttributeSet?) : ListView(context, attrs) {


    override fun onMeasure(widthSpec: Int, heightSpec: Int) {

        val expandSpec = MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE shr 2,
            MeasureSpec.AT_MOST
        )

        super.onMeasure(widthSpec, expandSpec)

    }


}