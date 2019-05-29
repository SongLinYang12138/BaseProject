package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.*;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.bondex.ysl.battledore.main.MainViewModle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseActivity<M extends BaseViewModle, B extends ViewDataBinding> extends AppCompatActivity {

    protected M viewModel;
    protected B binding;

    private Observer<Boolean> refreshObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isShow) {

            if (isShow) startLoading();
            else stopLoading();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getReourceId());

        viewModel = initViewModle();

        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModle.class;
            }
            viewModel = (M) createViewModel(this, modelClass);
        }

        if(viewModel instanceof MainViewModle){

            Log.i("aaaa","true");
        }

        Log.i("aaa","vieModle "+viewModel.toString());

        getLifecycle().addObserver(viewModel);
    }

//    private <T extends ViewModel> T createViewModel(AppCompatActivity activity, Class<T> cls) {
//
//        return ViewModelProviders.of(activity).get(cls);
//
//    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getRefresh().observe(this, refreshObserver);


    }


    protected abstract void startLoading();

    protected abstract void stopLoading();

     protected <T extends ViewModel> T initViewModle() {
        return null;
    }

    protected abstract int getReourceId();


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (viewModel != null) getLifecycle().removeObserver(viewModel);
        viewModel.getRefresh().removeObserver(refreshObserver);
    }
}
