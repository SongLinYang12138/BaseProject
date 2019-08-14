package com.bondex.ysl.battledore.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.bondex.ysl.battledore.util.interf.LifecycleaWacher;

/**
 * date: 2019/5/27
 * Author: ysl
 * description:
 */
public abstract class BaseViewModle extends ViewModel implements LifecycleaWacher {

    private MutableLiveData<Boolean> refres = new MutableLiveData<>();
    private MutableLiveData<Integer> msgLiveData = new MutableLiveData<>();
    protected Context context;



    public void setCont(Context context) {
        this.context = context;
    }

    public LiveData<Boolean> getRefresh() {
        return refres;
    }

    public void setRefresh(boolean isShow) {
        refres.postValue(isShow);
    }

    protected LiveData<Integer> getMsgLiveDatas(){

        return msgLiveData;
    }

    protected void setMsgLiveDataValue (Integer msg){

        msgLiveData.setValue(msg);
    }


}
