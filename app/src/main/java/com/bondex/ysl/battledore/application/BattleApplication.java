package com.bondex.ysl.battledore.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.ImageView;
import com.bondex.ysl.liblibrary.utils.CommonUtils;
import com.bumptech.glide.BuildConfig;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;


/**
 * date: 2019/5/24
 * Author: ysl
 * description:
 */
public class BattleApplication extends Application {

    public static Context CONTEXT;

    private String appid = "14763f889a";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }


    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();

        initLogger();
        imageSelecter();
        initBugly();
        initHotfix();

    }

    private void initHotfix() {

        SophixManager.getInstance().queryAndLoadNewPatch();
    }


    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void imageSelecter() {


        Fresco.initialize(this);

    }

    private void initBugly() {

        Bugly.init(getApplicationContext(), appid, BuildConfig.DEBUG);
        Beta.autoCheckUpgrade = false;//自动更新检查开关

        /*在application中初始化时设置监听，监听策略的收取*/
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {


                    Logger.i("启动更新 " + strategy.apkUrl + "  " + strategy.versionCode + "  " + strategy.versionName);

                } else {


                    Logger.i("没有更新");
                }
            }
        };
    }

    private void initSmart(){


    }

}


