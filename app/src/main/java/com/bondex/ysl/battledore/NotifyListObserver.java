package com.bondex.ysl.battledore;

import android.arch.lifecycle.MutableLiveData;

/**
 * date: 2019/8/22
 * Author: ysl
 * description:
 */
public class NotifyListObserver {

    public static NotifyListObserver intstace;

    private MutableLiveData<Integer> notify = new MutableLiveData<>();

    public static NotifyListObserver getIntstace() {

        if (intstace == null) intstace = new NotifyListObserver();
        return intstace;
    }

    private NotifyListObserver() {

    }


    public MutableLiveData<Integer> getNotify() {

        return notify;
    }

    public void notifyList(Integer msg) {

        notify.setValue(msg);
    }


}
