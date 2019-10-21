package com.bondex.ysl.battledore.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.util.interf.AirPortDialogCallback;
import com.bondex.ysl.databaselibrary.airport.AirPort;
import com.bondex.ysl.liblibrary.utils.CommonUtils;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.List;

/**
 * date: 2019/8/29
 * Author: ysl
 * description:
 */
public class AirPortDialogUtil {


    private Context context;

    public AirPortDialogUtil(Context context) {

        this.context = context;
    }

    Dialog airPortDialog;

    AirPort selectAirPort = null;
    AppCompatSpinner spinner;

    public void showAirPort(List<AirPort> mylist,  AirPortDialogCallback callback1) {


        final List<AirPort> list = mylist;
        final  AirPortDialogCallback callback = callback1;
        if (airPortDialog == null) {

            airPortDialog = new Dialog(context, R.style.dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_city, null, false);
            airPortDialog.setContentView(view);
            ImageView ivBack = view.findViewById(R.id.base_back);
            TextView tvTitle = view.findViewById(R.id.base_title);
            spinner = view.findViewById(R.id.choose_city_sp);
            ButtonRectangle btCancel = view.findViewById(R.id.choose_city_bt_cancel);
            ButtonRectangle btConfirm = view.findViewById(R.id.choose_city_bt_confirm);

            ArrayAdapter<AirPort> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectAirPort = list.get((int) id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    if (list.size() > 1) selectAirPort = list.get(0);
                }
            });
            ivBack.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {
                    airPortDialog.dismiss();
                }
            });
            tvTitle.setText("选择机场");

            btCancel.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {
                    airPortDialog.dismiss();
                }
            });
            btConfirm.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void click(View v) {

                    if (selectAirPort == null) {

                        ToastUtils.showShort("请选择一个机场");
                        return;
                    }
                    Constant.AIR_CODE = selectAirPort.getCode();
                    SharedPreferecneUtils.saveValue(context, Constant.AIR_CODE_KEY, selectAirPort.getCode());

                    callback.confirm(selectAirPort);
                    airPortDialog.dismiss();

                }
            });

            WindowManager.LayoutParams lp = airPortDialog.getWindow().getAttributes();

            lp.width = CommonUtils.getScreenW(context) - 50;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            airPortDialog.getWindow().setAttributes(lp);
            airPortDialog.show();
        } else {

            if (!airPortDialog.isShowing()) {

                ArrayAdapter<AirPort> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                spinner.setAdapter(adapter);

                airPortDialog.show();
            } else airPortDialog.dismiss();
        }


    }


}
