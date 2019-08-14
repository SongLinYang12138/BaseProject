package com.bondex.ysl.battledore.util;

import com.bondex.ysl.battledore.util.interf.HttpResultBack;
import com.bondex.ysl.battledore.util.interf.NetApi;
import com.orhanobut.logger.Logger;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public class HttpConnection {

    private static final String BASE_URL = "http://cas.bondex.com.cn:8080/cslogin.jsp/";

    private static Retrofit getRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();


        return retrofit;
    }

    public static NetApi getNetApi() {

        return getRetrofit().create(NetApi.class);

    }


    public static void connect(Observable<String> observable, final HttpResultBack resultBack) {


        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                resultBack.onSuccess(s);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(60, TimeUnit.SECONDS, new ObservableSource<String>() {
                    @Override
                    public void subscribe(Observer<? super String> observer) {

                        Logger.i("连接超时  ");
                        resultBack.onFaile("连接超时");
                    }
                })
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Logger.i("连接失败 " + throwable.getMessage());
                        resultBack.onFaile(throwable.getMessage());
                    }
                });


    }


}
