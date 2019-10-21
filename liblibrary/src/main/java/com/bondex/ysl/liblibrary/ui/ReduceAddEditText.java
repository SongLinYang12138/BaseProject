package com.bondex.ysl.liblibrary.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bondex.ysl.liblibrary.R;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;

/**
 * date: 2019/7/9
 * Author: ysl
 * description:
 */
public class ReduceAddEditText extends ConstraintLayout {

    private ImageView tvReduce, tvAdd;
    private EditText editText;

    public ReduceAddEditText(Context context) {
        super(context);
        initViwe();
    }

    public ReduceAddEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViwe();
    }


    private void initViwe() {

        inflate(getContext(), R.layout.reduce_edit_add_layout, this);

        tvReduce = findViewById(R.id.reduce);
        tvAdd = findViewById(R.id.add);
        editText = findViewById(R.id.edit);

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) editText.selectAll();

            }
        });
        initClick();
    }

    private void initClick() {

        tvReduce.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void click(View v) {

                int value = getEdit() - 1;
                if (value < 0) return;
                editText.setText(value + "");
            }
        });

        tvAdd.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void click(View v) {

                int value = getEdit() + 1;
                editText.setText(value + "");

            }
        });
    }


    private int getEdit() {
        int value = 0;

        try {
            String str = editText.getText().toString();
            if (TextUtils.isEmpty(str)) str = "0";
            value = Integer.valueOf(str.trim());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public EditText getEditText() {

        return editText;
    }


}
