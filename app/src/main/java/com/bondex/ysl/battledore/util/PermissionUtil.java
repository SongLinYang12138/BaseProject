package com.bondex.ysl.battledore.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    public static final int READ_REQUEST = 1000;
    public static final int WRITE_REQUEST = 1001;
    public static final int INSTALL_PACKAGES_REQUEST_CODE = 110;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkPermission(Activity context) {

        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_REQUEST);
        }
    }

    /*
     *
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    public static void checkIsAndroidO(Activity activity) {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = activity.getPackageManager().canRequestPackageInstalls();
            //请求安装未知应用来源的权限
            if (!b)
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUEST_CODE);
        } else {
        }
    }

}
