package com.bondex.ysl.battledore.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseActivityJ<M extends BaseViewModle, B extends ViewDataBinding> extends AppCompatActivity {

    protected M vieModle;
    protected B binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView()

    }

    protected abstract int getReourceId();



}
