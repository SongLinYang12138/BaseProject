package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public abstract class BaseFragment<VM extends BaseViewModle,V extends ViewDataBinding> extends Fragment {

    protected VM viewModle;
    protected V  binding;

    private Observer<Boolean> refershObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isShow) {


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,getResourceId(),container,false);

        viewModle = iniViewModle();

        getLifecycle().addObserver(viewModle);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModle.getRefresh().observe(this,refershObserver);



    }

    protected abstract int getResourceId();


    protected abstract VM iniViewModle();


    @Override
    public void onDestroy() {
        super.onDestroy();

        getLifecycle().removeObserver(viewModle);
        viewModle.getRefresh().removeObserver(refershObserver);

    }
}
