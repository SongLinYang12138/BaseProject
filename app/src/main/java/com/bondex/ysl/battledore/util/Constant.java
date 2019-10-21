package com.bondex.ysl.battledore.util;

import com.bondex.ysl.databaselibrary.login.LoginBean;

public class Constant {

    public static final String TAG = "aaa";
    public static LoginBean LOGIN_BEAN;
    //    主单号扫描返回
    public static final int SCAN_MAIN_REQUEST_CODE = 1212;
    //    分单号扫描返回
    public static final int SCAN_HAWB_REQUEST_CODE = 1111;
    //照片扫描返回
    public static final int CAMERA_REQUEST = 101;
    //    带planbean跳转时的key
    public static final String PLAN_BEAN_KEY = "planBean";
    //    //带HAWBBean跳转时的key
    public static final String HAWB_BEAN_KEY = "hwabs";
    //当前所在城市
    public static final String CITY_KEY = "city";
    //    机场三字码
    public static final String AIR_CODE_KEY = "air_code";
    public static String AIR_CODE;
    public static String CITY;


}
