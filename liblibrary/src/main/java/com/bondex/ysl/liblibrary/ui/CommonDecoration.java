package com.bondex.ysl.liblibrary.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.bondex.ysl.liblibrary.R;

/**
 * date: 2019/7/11
 * Author: ysl
 * description:
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private int height = 10;
    private Paint paint = new Paint();


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        paint.setColor(0x0198CA);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < parent.getChildCount(); i++) {
            drawHorizontalDecoration(c, parent.getChildAt(i));
        }
    }

    private void drawHorizontalDecoration(Canvas canvas, View childView) {

        canvas.drawLine(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom()+height, paint);

    }

}
