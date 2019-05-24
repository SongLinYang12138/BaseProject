package com.bondex.ysl.battledore.util;

import android.view.View;
import android.widget.EditText;

/**
 * date: 2018/11/26
 * Author: ysl
 * description:
 */
public class SelecteAllListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        EditText et = (EditText)v;
         et.setText(et.getText().toString());
        et.selectAll();

    }
}
