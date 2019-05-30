package com.bondex.ysl.battledore.application;

import android.app.Application;
import android.content.Context;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * date: 2019/5/24
 * Author: ysl
 * description:
 */
public class BattleApplication  extends Application {

    public static Context CONTEXT;
    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();

        initLogger();
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
