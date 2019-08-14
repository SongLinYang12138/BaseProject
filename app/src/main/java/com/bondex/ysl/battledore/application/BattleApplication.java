package com.bondex.ysl.battledore.application;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;



/**
 * date: 2019/5/24
 * Author: ysl
 * description:
 */
public class BattleApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();

        initLogger();
        imageSelecter();
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void imageSelecter() {


        Fresco.initialize(this);

    }
}
