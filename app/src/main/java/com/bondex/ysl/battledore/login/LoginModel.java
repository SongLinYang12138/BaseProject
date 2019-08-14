package com.bondex.ysl.battledore.login;

import android.content.Context;
import com.bondex.ysl.battledore.base.BaseModel;
import com.bondex.ysl.battledore.util.HttpConnection;
import com.bondex.ysl.battledore.util.interf.HttpResultBack;
import com.bondex.ysl.battledore.util.interf.ModelCallback;
import com.bondex.ysl.databaselibrary.login.LoginBean;
import com.bondex.ysl.databaselibrary.login.LoginDao;
import com.bondex.ysl.databaselibrary.login.LoginDataBase;
import com.orhanobut.logger.Logger;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import java.util.List;


/**
 * date: 2019/7/19
 * Author: ysl
 * description:
 */
public class LoginModel implements BaseModel<LoginParam> {

    @Override
    public void iniData(LoginParam param, final ModelCallback callback) {

        HttpConnection.connect(HttpConnection.getNetApi().login(param.getName(), param.getPassword()), new HttpResultBack() {
            @Override
            public void onSuccess(@NotNull String data) {

                callback.onSuccess(data);
            }

            @Override
            public void onFaile(@NotNull String error) {

                callback.onFaile(error);

            }
        });
    }


    public void readData(final Context context, final ModelCallback<LoginBean> callback) {


        Observable<LoginBean> observable = Observable.create(new ObservableOnSubscribe<LoginBean>() {
            @Override
            public void subscribe(ObservableEmitter<LoginBean> emitter) throws Exception {

                LoginDao dao = LoginDataBase.getInstance(context).getDao();
                List<LoginBean> list = dao.getByLoginDate();

                LoginBean bean = list == null || list.size() == 0 ? null : list.get(0);

                emitter.onNext(bean);
                emitter.onComplete();

            }
        });
        Consumer<LoginBean> consumer = new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean bean) throws Exception {

                if(bean != null)callback.onSuccess(bean);

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {


                        Logger.i("login  " + throwable.getMessage());
                    }
                });

    }

}

