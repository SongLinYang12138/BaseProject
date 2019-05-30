package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public abstract class BaseFragment<VM extends BaseViewModle, V extends ViewDataBinding> extends Fragment {

    protected VM viewModel;
    protected V binding;

    private Observer<Boolean> refershObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isShow) {


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getResourceId(), container, false);

        viewModel = iniViewModle();

        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModle.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }

        getLifecycle().addObserver(viewModel);
        return binding.getRoot();
    }


    public <V extends ViewModel> V createViewModel(Fragment fragment,Class<V> cls){

        return ViewModelProviders.of(fragment).get(cls);
    }



    @Override
    public void onStart() {
        super.onStart();

        viewModel.getRefresh().observe(this, refershObserver);


    }

    protected abstract int getResourceId();


    protected VM iniViewModle() {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        getLifecycle().removeObserver(viewModel);
        viewModel.getRefresh().removeObserver(refershObserver);

    }
}
