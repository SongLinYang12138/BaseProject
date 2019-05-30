package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.*;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.bondex.ysl.battledore.main.MainViewModle;
import com.bondex.ysl.battledore.util.NoDoubleClickListener;
import com.bondex.ysl.liblibrary.utils.StatusBarUtil;

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



    protected MyclickListener listener = new MyclickListener();


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

        setStatusBar();

        initView();
    }

    protected abstract void initView();

    private void setStatusBar(){
        StatusBarUtil.setColor(this, getResources().getColor(com.bondex.ysl.styleibrary.R.color.rect_red));

    }


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
    protected abstract void onMyClick(View view);

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

    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {
            onMyClick(v);
        }
    }
}
