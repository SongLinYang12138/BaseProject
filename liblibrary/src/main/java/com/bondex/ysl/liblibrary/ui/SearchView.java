package com.bondex.ysl.liblibrary.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bondex.ysl.liblibrary.R;
import com.rengwuxian.materialedittext.MaterialEditText;



/**
 * date: 2018/12/26
 * Author: ysl
 * description:
 */
public class SearchView extends LinearLayout {

    private LinearLayout iconSearch;
    private EditText editText;
    private ImageView ivClear;

    public SearchView(Context context) {
        super(context);

        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);


        init();
    }

    public void addTextWatcher(TextWatcher watcher){

        editText.addTextChangedListener(watcher);
    }

    private void init() {

        inflate(getContext(), R.layout.search_layout, this);

        iconSearch = findViewById(R.id.search_icon);
        editText = findViewById(R.id.search_editext);
        ivClear = findViewById(R.id.search_iv_clear);

        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.getText().clear();
            }
        });

    }

    public Editable getText() {

        return editText.getText();
    }

    public EditText getEdit(){

        return editText;
    }

    public void setIconListener(OnClickListener listener) {
        if (listener != null)
            iconSearch.setOnClickListener(listener);
    }

}
