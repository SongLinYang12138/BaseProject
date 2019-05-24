package com.bondex.ysl.battledore.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferecneUtils {


    public static void saveValue(Context context, String name, String key, String value) {

        if (context == null) {
            return;
        }
        if (CommonUtil.isEmpty(key) || value == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
        editor.clear();
    }

    public static String getValue(Context context, String name, String key) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }


    public static void saveInteger(Context context, String name, String key, int value) {


        if (context == null) {
            return;
        }
        if (CommonUtil.isEmpty(key)) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, value);
        editor.apply();
        editor.clear();
    }


    public static Integer getInteger(Context context, String name, String key) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, 1111);
    }

    public static void saveBoolean(Context context, String name, String key, boolean value) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.clear();
    }

    public static boolean getBoolean(Context context, String name, String key) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

}
