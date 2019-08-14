package com.bondex.ysl.battledore.login;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.base.BaseActivity;
import com.bondex.ysl.battledore.databinding.ActivityLoginBinding;
import com.bondex.ysl.battledore.main.MainActivity;
import com.bondex.ysl.battledore.util.Constant;
import com.bondex.ysl.battledore.util.ToastUtils;
import com.bondex.ysl.databaselibrary.login.LoginBean;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;
import com.orhanobut.logger.Logger;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    private final String TAG = LoginActivity.class.getSimpleName();
    private boolean shouldInsert;

    private Observer<Boolean> refreObserver;
    private Observer<LoginBean> loginObserver;


    @Override
    protected int getReourceId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//                    checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
//            ) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//                requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
//            }
//
//        }


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

                    toMain();
                }
            }
        };

        viewModel.getRefresh().observe(this, refreObserver);

        viewModel.getLoginLiveData().observe(this, loginObserver);

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


        if (!name.contains("@bondex.com.cn")) {
            name = name + "@bondex.com.cn";
        }

        shouldInsert = binding.loginCheck.isChecked();
        viewModel.login(name, psw, shouldInsert);

    }


}
