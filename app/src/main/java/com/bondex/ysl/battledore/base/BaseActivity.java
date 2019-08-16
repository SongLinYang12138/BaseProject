package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.*;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bondex.ysl.battledore.main.MainViewModle;
import com.bondex.ysl.liblibrary.ui.IconText;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;
import com.bondex.ysl.liblibrary.utils.StatusBarUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.bondex.ysl.battledore.R;


/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseActivity<M extends BaseViewModle, B extends ViewDataBinding> extends AppCompatActivity {

    protected M viewModel;
    protected B binding;

    private ImageView ivBack;
    private TextView tvTitle;
    private IconText iconRight;

    protected MyclickListener listener = new MyclickListener();


    private Observer<Integer> msgObserver = new Observer<Integer>() {
        @Override
        public void onChanged(@Nullable Integer msg) {
            handleMessage(msg);
        }
    };
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

        initData();
        binding = DataBindingUtil.setContentView(this, getReourceId());

        ivBack = binding.getRoot().findViewById(R.id.base_back);
        tvTitle = binding.getRoot().findViewById(R.id.base_title);
        iconRight = binding.getRoot().findViewById(R.id.base_right);

        if (ivBack != null) ivBack.setVisibility(View.INVISIBLE);
        if (tvTitle != null) tvTitle.setVisibility(View.INVISIBLE);
        if (iconRight != null) iconRight.setVisibility(View.INVISIBLE);

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

            viewModel.setCont(getApplicationContext());
        }


        getLifecycle().addObserver(viewModel);

        setStatusBar();

        initView();
    }

    protected abstract void initData();

    protected abstract void initView();

    private void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(com.bondex.ysl.styleibrary.R.color.colorPrimary));

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
        viewModel.getMsgLiveDatas().observe(this, msgObserver);

    }

    protected void showLeft(boolean isShow, View.OnClickListener listener) {

        if (ivBack != null && isShow) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(listener);
        }
    }


    protected void showTitle(String title) {

        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }


    protected void showRight(boolean isShow, int resourceId, View.OnClickListener listener) {

        if (iconRight != null && isShow) {

            iconRight.setVisibility(View.VISIBLE);
            iconRight.setOnClickListener(listener);
            if (resourceId != 0) iconRight.setText(resourceId);
        }
    }

//    处理从viewmodel中传递过来的信息
    protected abstract void handleMessage(Integer msg);

//    listner有关的点击事件
    protected abstract void onMyClick(View view);

    protected void startLoading() {
    }

    protected void stopLoading() {
    }

    protected <T extends ViewModel> T initViewModle() {
        return null;
    }

    protected abstract int getReourceId();


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (viewModel != null) {


            viewModel.getRefresh().removeObserver(refreshObserver);
            viewModel.getMsgLiveDatas().removeObserver(msgObserver);
            getLifecycle().removeObserver(viewModel);

        }
    }

    protected class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {
            onMyClick(v);
        }
    }
}
