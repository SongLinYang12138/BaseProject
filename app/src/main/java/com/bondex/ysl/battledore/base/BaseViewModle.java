package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.bondex.ysl.battledore.util.interf.LifecycleaWacher;

/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseViewModle extends ViewModel implements LifecycleaWacher {

    private MutableLiveData<Boolean> refresh = new MutableLiveData<>();



    public LiveData<Boolean> getRefresh() {
        return refresh;
    }

    public void setRefresh(boolean isShow) {


        refresh.postValue(isShow);
    }


}
