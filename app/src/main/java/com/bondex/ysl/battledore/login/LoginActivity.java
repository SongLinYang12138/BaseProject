package com.bondex.ysl.battledore.login;


import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.base.BaseActivity;
import com.bondex.ysl.battledore.databinding.ActivityLoginBinding;
import com.bondex.ysl.battledore.main.MainActivity;
import com.bondex.ysl.battledore.util.AirPortDialogUtil;
import com.bondex.ysl.battledore.util.Constant;
import com.bondex.ysl.battledore.util.SharedPreferecneUtils;
import com.bondex.ysl.battledore.util.ToastUtils;
import com.bondex.ysl.battledore.util.interf.AirPortDialogCallback;
import com.bondex.ysl.databaselibrary.airport.AirPort;
import com.bondex.ysl.databaselibrary.login.LoginBean;
import com.bondex.ysl.liblibrary.ui.IconText;
import com.bondex.ysl.liblibrary.utils.CommonUtils;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;
import com.gc.materialdesign.views.ButtonRectangle;
import com.orhanobut.logger.Logger;
import org.w3c.dom.Text;

import java.util.List;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    private final String TAG = LoginActivity.class.getSimpleName();
    private boolean shouldInsert;

    private Observer<Boolean> refreObserver;
    private Observer<LoginBean> loginObserver;
    private AirPortDialogUtil airPortDialogUtil = new AirPortDialogUtil(this);
    private Observer<List<AirPort>> airPortObserver = new Observer<List<AirPort>>() {
        @Override
        public void onChanged(@Nullable List<AirPort> airPorts) {

            if (airPorts == null || airPorts.size() == 0) {
                toMain();
                return;
            }

            airPortDialogUtil.showAirPort(airPorts, new AirPortDialogCallback() {
                @Override
                public void confirm(AirPort airPort) {
                    toMain();
                }
            });
        }
    };


    @Override
    protected int getReourceId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                viewModel.getLocation();
            }

        } else {
            viewModel.getLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getLocation();
            } else {
                ToastUtils.showShort("请开启定位权限");
            }

        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        showLeft(false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showTitle("登录");


        binding.loginIvAccount.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void click(View v) {

                binding.loginEtPassword.getText().clear();
                binding.loginEtAccount.getText().clear();
            }
        });
        binding.loginIvPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        binding.loginEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        //否则隐藏密码

                        binding.loginEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });
        refreObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                binding.avLoading.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        };

        loginObserver = new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean bean) {

                Logger.i("readLogin  " + bean.getPsnname());
                binding.loginEtAccount.setText(bean.getEmail());
                binding.loginEtPassword.setText(bean.getPsw());
                binding.loginCheck.setChecked(bean.isShouldRember());

                Constant.LOGIN_BEAN = bean;

                if (bean.isLogin()) {
                    if (TextUtils.isEmpty(Constant.AIR_CODE)) {

                        viewModel.getAirPortOld(Constant.CITY);
                    } else {

                        toMain();
                    }

                }
            }
        };

        viewModel.getRefresh().observe(this, refreObserver);

        viewModel.getLoginLiveData().observe(this, loginObserver);
        viewModel.getAirportLiveData().observe(this, airPortObserver);

        binding.loginBtLogin.setOnClickListener(listener);
        binding.loginIvAccount.setOnClickListener(listener);
        binding.loginIvPassword.setOnClickListener(listener);


    }


    @Override
    protected void handleMessage(Integer msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        viewModel.getRefresh().removeObserver(refreObserver);
        viewModel.getLoginLiveData().removeObserver(loginObserver);

        viewModel.getAirportLiveData().removeObserver(airPortObserver);
    }

    private void toMain() {


        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onMyClick(View view) {


        switch (view.getId()) {

            case R.id.login_bt_login:

                login();
                break;

            case R.id.login_iv_account:

                binding.loginEtAccount.getText().clear();
                break;
        }

    }

    private void login() {
        String name = binding.loginEtAccount.getText().toString();
        String psw = binding.loginEtPassword.getText().toString();


        if (TextUtils.isEmpty(name)) {

            ToastUtils.showShort("请输入邮箱");
            return;
        }

        if (TextUtils.isEmpty(psw)) {
            ToastUtils.showShort("请输入密码");
            return;
        }


        if (!name.contains("@")) {
            name = name + "@bondex.com.cn";
        }

        shouldInsert = binding.loginCheck.isChecked();
        viewModel.login(name, psw, shouldInsert);

    }


}
