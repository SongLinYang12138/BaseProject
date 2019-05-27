package com.bondex.ysl.battledore.application;

import android.app.Application;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * date: 2019/5/24
 * Author: ysl
 * description:
 */
public class BattleApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
