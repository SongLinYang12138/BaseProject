package com.bondex.ysl.battledore.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.bondex.ysl.liblibrary.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SharedPreferecneUtils {


    public static void saveValue(Context context, String key, String value) {

        if (context == null) {
            return;
        }
        if (CommonUtils.isEmpty(key) || value == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
        editor.clear();
    }

    public static String getValue(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }


    public static void saveInteger(Context context,  String key, int value) {


        if (context == null) {
            return;
        }
        if (CommonUtils.isEmpty(key)) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, value);
        editor.apply();
        editor.clear();
    }


    public static Integer getInteger(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);
        return preferences.getInt(key, 1111);
    }

    public static void saveBoolean(Context context, String key, boolean value) {

        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.clear();
    }

    public static boolean getBoolean(Context context,  String key) {

        SharedPreferences preferences = context.getSharedPreferences("base_data", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }


}
