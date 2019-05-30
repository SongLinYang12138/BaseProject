package com.bondex.ysl.battledore.util;

import android.content.Context;
import android.widget.Toast;
import com.bondex.ysl.battledore.application.BattleApplication;

public class ToastUtils {

    public static void showToast(String msg) {

        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(BattleApplication.CONTEXT, msg, Toast.LENGTH_SHORT).show();

    }


    public static void showShort(String msg) {

        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(BattleApplication.CONTEXT, msg, Toast.LENGTH_SHORT).show();

    }

}
