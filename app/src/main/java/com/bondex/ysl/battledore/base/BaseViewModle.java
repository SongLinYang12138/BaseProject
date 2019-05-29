package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.view.View;
import com.bondex.ysl.battledore.util.NoDoubleClickListener;
import com.bondex.ysl.battledore.util.interf.LifecycleaWacher;

/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseViewModle extends ViewModel implements LifecycleaWacher {

    private MutableLiveData<Boolean> refresh = new MutableLiveData<>();
    protected MyclickListener listener = new MyclickListener();


    public LiveData<Boolean> getRefresh() {
        return refresh;
    }

    public void setRefresh(boolean isShow) {


        refresh.postValue(isShow);
    }


    protected abstract void onClick(View view);

    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {
            onClick(v);
        }
    }
}
