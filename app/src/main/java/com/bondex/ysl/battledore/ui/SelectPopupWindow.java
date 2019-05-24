package com.bondex.ysl.battledore.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.util.NoDoubleClickListener;
import com.bondex.ysl.battledore.util.ToastUtils;
import com.bondex.ysl.battledore.util.interf.PopSelectListener;


import java.util.ArrayList;


public class SelectPopupWindow extends ConstraintLayout {

    EditText popSelectEt;
    IconText popSelectIcon;
    private Context context;
    private ArrayList<String> list;

    private MyclickListener clik = new MyclickListener();

    private PopupWindow popupWindow;
    private PopSelectListener listener;

    public SelectPopupWindow(Context context) {
        super(context);
    }

    public SelectPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView(context);
    }

    private void initView(Context context) {

        inflate(context, R.layout.popupwindow_select, this);
        popSelectEt = findViewById(R.id.pop_select_et);
        popSelectIcon = findViewById(R.id.pop_select_icon);


        popSelectEt.setOnClickListener(clik);
        popSelectIcon.setOnClickListener(clik);
    }

    public void setSelectListener(PopSelectListener listener) {
        this.listener = listener;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
      if(list != null && list.size() > 0)  popSelectEt.setText(list.get(0));
    }

    private void showPopWindow() {

        if(list == null){
            ToastUtils.showToast(context,"请设置list");
            return;
        }
        if(listener == null){
            ToastUtils.showToast(context,"请设置listener");
            return;
        }

        if (popupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_select_layout, this, false);
            popupWindow = new PopupWindow(view, popSelectEt.getWidth(), LayoutParams.WRAP_CONTENT);
            final ListView listView = view.findViewById(R.id.pop_select_listview);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.text_item, list);
            listView.setAdapter(adapter);
            Drawable drawable = new ColorDrawable(Color.WHITE);
            popupWindow.setBackgroundDrawable(drawable);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    listener.onItemClick(list.get((int) id), (int) id);
                    popSelectEt.setText(list.get(0));
                    popupWindow.dismiss();
                }
            });
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(popSelectEt);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    changeStyle();
                }
            });
        }
        popupWindow.showAsDropDown(popSelectEt);
        changeStyle();
    }

    private void changeStyle() {

        if (popupWindow != null) {
            popSelectIcon.setText(popupWindow.isShowing() ? R.string.arrow_down : R.string.arrow_right);
        }
    }


    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            showPopWindow();
        }
    }

}
