package com.bondex.ysl.battledore.application;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;
import com.bumptech.glide.BuildConfig;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * date: 2019/9/4
 * Author: ysl
 * description:
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    private static final String ID_SECRET = "27845502-1";
    private static final String APP_SECRET = "befc07f627a4e4414e5a34be17d92fc6";
    private static final String RSA_SECRET = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCYhR+cgpgruqcUcEuTForEzJRvE0VLOas5yifx8wTw5UNowiRW3hqNlYV1yFhmLDK3/Ac6AxTZdegmI7v2ZCOKxP2PADAbJKxfB2jTgn1kC0Bq3iow9XjCeJmcYZelbWzx8Hyr/MU3HLeCRnTE4HQJi28wqv48rMoqrVg7tlEO2JeYGfrqurDtooT/5vySWUbYyosGshjHZg2wezl0DN6q4NPRp79BEE2X7jYeep8WVI+YgAKtCTdUh7eUSJf83MMDGKzXi+53UvcLBAEXg6phijsmCdz955+1cCZvh0miV+JEKfNQVpo2lTPQ+oOThs7G1OhbLij4k99KGm8dmeRDAgMBAAECggEBAI2AvOJVG18+zfKdeNBfUqHheDlRdLGlMGHuV38XS9c/rXnM3TAm4hXHykmLddBdYzTlQ8Mjxr02sgpojuO65mrB9skskHUuYinMUwog/7rI4ZwEwLoplD9A6qt2s94MFs6kotZiLorCPtNEEOPzplSQo7lQe43JdcuAheLodG3DQAmbf3IxyWC0yZCtVNAItRvRSHEPUDG016JBXw8kgCvPjIcARz6HNi/BZU69UyxJm7RC94z/tWX9QsOwtdLFBiALqR6KCwzDhsDgt55RmkLvtKvwFC2kKawBFPNlHQ0DWfkQ3JIJeKl1h3hcQ7QRjSE8ipCbQQXVeLD3cbpiGQECgYEA0mNUEJx/5wAg1Gmj1bM5r0gVCLcor5pxrknb7KMLjeMxKroZksO2DHPGj/9cznTDjr2eFsQrpQQn6h/eQi6j0wk1krpFIeTXU47u6MU6CpmC01yaISH6ShrrGV88ivUUTFIRYFC0Whp2uoUOtvAWeESrJxdux/7gntvxY3L1Z8MCgYEAuZYVfXQLtfbtuKGEVXyXYijNCQKNV5sZvwicK6QjO4iP4kHlKG6FmufFshHijhsCgD0eeaEMjHEcY1I8+9ITU7sctx7ZvJuEEMQaJTJJykUGZpryefKeiGIvy+YGOWchi5yffb7MhfapyhJuKLMpzTcOfnRmCH/X1CbWQ3meSYECgYEAiElb7Z7To/qLSc5dGVQOrCNPrOOpcYfk2Hh0BOjh1To1YW5InrA1P//HwwQzRHKTM+ZVgH33n9//U0Ttn4XDDjRLBg+wNPKfIjvXO+Z3YOJswyqblZcDIfoqDbeJ6qI0UfRf2TqueifmfZEwJQWGKMai8KHXW6saKw5jYn7Xxi0CgYBMe8b1gdrFrjNAUQhZttQotwnw+aVIgno02ZaLAcUnZtttcpy/cfUL5xL5lRdaW1VHhmjF1ZJ3WrJJWH4bhfNDIazu5v7zFOBzjG3+AGCaxKGjrZ3alhuxt01daboM9CzF/dFZIEXnu8Uzqy7NBTFoCpWlldDrFczb+Pl6q2eJgQKBgQC+QzFxiJ5ehL1wqNX4ku0qDH8E4LttKR+0u5MyrYvhcuK4abtxEaXn/xhBPm3CyYWr+Jc08tFb0oRL4PcNjEgvIiguKMK09PrqvnpouBeqAxIN+EsehmcRugarjLUt7GnimAkaYGEaGPWDskfv0qru3rR2BpQ+qI2fUltha1pUsQ==";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(value = BattleApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(ID_SECRET, APP_SECRET, RSA_SECRET)
                .setEnableDebug(BuildConfig.DEBUG)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {

                        Log.i("aaa", "hotfix code  " + code + "  " + info);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            SophixManager.getInstance().killProcessSafely();

                        }
                    }
                }).initialize();
    }
}
//    //兼容老版本的code说明
//    int CODE_LOAD_SUCCESS = 1;//加载阶段, 成功
//    int CODE_ERR_INBLACKLIST = 4;//加载阶段, 失败设备不支持
//    int CODE_REQ_NOUPDATE = 6;//查询阶段, 没有发布新补丁
//    int CODE_REQ_NOTNEWEST = 7;//查询阶段, 补丁不是最新的
//    int CODE_DOWNLOAD_SUCCESS = 9;//查询阶段, 补丁下载成功
//    int CODE_DOWNLOAD_BROKEN = 10;//查询阶段, 补丁文件损坏下载失败
//    int CODE_UNZIP_FAIL = 11;//查询阶段, 补丁解密失败
//    int CODE_LOAD_RELAUNCH = 12;//预加载阶段, 需要重启
//    int CODE_REQ_APPIDERR = 15;//查询阶段, appid异常
//    int CODE_REQ_SIGNERR = 16;//查询阶段, 签名异常
//    int CODE_REQ_UNAVAIABLE = 17;//查询阶段, 系统无效
//    int CODE_REQ_SYSTEMERR = 22;//查询阶段, 系统异常
//    int CODE_REQ_CLEARPATCH = 18;//查询阶段, 一键清除补丁
//    int CODE_PATCH_INVAILD = 20;//加载阶段, 补丁格式非法
//    //查询阶段的code说明
//    int CODE_QUERY_UNDEFINED = 31;//未定义异常
//    int CODE_QUERY_CONNECT = 32;//连接异常
//    int CODE_QUERY_STREAM = 33;//流异常
//    int CODE_QUERY_EMPTY = 34;//请求空异常
//    int CODE_QUERY_BROKEN = 35;//请求完整性校验失败异常
//    int CODE_QUERY_PARSE = 36;//请求解析异常
//    int CODE_QUERY_LACK = 37;//请求缺少必要参数异常
//    //预加载阶段的code说明
//    int CODE_PRELOAD_SUCCESS = 100;//预加载成功
//    int CODE_PRELOAD_UNDEFINED = 101;//未定义异常
//    int CODE_PRELOAD_HANDLE_DEX = 102;//dex加载异常
//    int CODE_PRELOAD_NOT_ZIP_FORMAT = 103;//基线dex非zip格式异常
//    int CODE_PRELOAD_REMOVE_BASEDEX = 105;//基线dex处理异常
//    //加载阶段的code说明 分三部分dex加载, resource加载, lib加载
//    //dex加载
//    int CODE_LOAD_UNDEFINED = 71;//未定义异常
//    int CODE_LOAD_AES_DECRYPT = 72;//aes对称解密异常
//    int CODE_LOAD_MFITEM = 73;//补丁SOPHIX.MF文件解析异常
//    int CODE_LOAD_COPY_FILE = 74;//补丁拷贝异常
//    int CODE_LOAD_SIGNATURE = 75;//补丁签名校验异常
//    int CODE_LOAD_SOPHIX_VERSION = 76;//补丁和补丁工具版本不一致异常
//    int CODE_LOAD_NOT_ZIP_FORMAT = 77;//补丁zip解析异常
//    int CODE_LOAD_DELETE_OPT = 80;//删除无效odex文件异常
//    int CODE_LOAD_HANDLE_DEX = 81;//加载dex异常
//    // 反射调用异常
//    int CODE_LOAD_FIND_CLASS = 82;
//    int CODE_LOAD_FIND_CONSTRUCTOR = 83;
//    int CODE_LOAD_FIND_METHOD = 84;
//    int CODE_LOAD_FIND_FIELD = 85;
//    int CODE_LOAD_ILLEGAL_ACCESS = 86;
//    //resource加载
//    public static final int CODE_LOAD_RES_ADDASSERTPATH = 123;//新增资源补丁包异常
//    //lib加载
//    int CODE_LOAD_LIB_UNDEFINED = 131;//未定义异常
//    int CODE_LOAD_LIB_CPUABIS = 132;//获取primaryCpuAbis异常
//    int CODE_LOAD_LIB_JSON = 133;//json格式异常
//    int CODE_LOAD_LIB_LOST = 134;//lib库不完整异常
//    int CODE_LOAD_LIB_UNZIP = 135;//解压异常
//    int CODE_LOAD_LIB_INJECT = 136;//注入异常