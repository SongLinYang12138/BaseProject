package com.bondex.ysl.battledore.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.bondex.ysl.battledore.base.BaseModel;
import com.bondex.ysl.battledore.util.HttpConnection;
import com.bondex.ysl.battledore.util.interf.HttpResultBack;
import com.bondex.ysl.battledore.util.interf.ModelCallback;
import com.bondex.ysl.databaselibrary.airport.AirPort;
import com.bondex.ysl.databaselibrary.airport.AirPortDao;
import com.bondex.ysl.databaselibrary.airport.AirPortDatabase;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

                if (bean != null) callback.onSuccess(bean);

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


    public void getAirport(final Context context, final String city, final ModelCallback<List<AirPort>> modelCallback) {

        JSONObject json = new JSONObject();
        try {

            json.put("sLike", "中国");
            json.put("maxTotal", "100");
            json.put("fields", "country,code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpConnection.connect(HttpConnection.getNetApi().getAirPortOld(json.toString()), new HttpResultBack() {
            @Override
            public void onSuccess(@NotNull String data) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);

                    boolean isSuccess = jsonObject.getBoolean("success");
                    String msg = jsonObject.getString("msg");
                    if (isSuccess) {
                        JSONArray jsonArr = jsonObject.getJSONArray("list");

                        insert(jsonArr, context, city, modelCallback);

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFaile(@NotNull String error) {
                Logger.i("aaa" + "data" + error);

            }
        });

    }

    private void insert(final JSONArray jsonArr, final Context context, final String city, final ModelCallback<List<AirPort>> modelCallback) {

        Observable<List<AirPort>> observable = Observable.create(new ObservableOnSubscribe<List<AirPort>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AirPort>> emitter) throws Exception {

                AirPortDao dao = AirPortDatabase.getInstance(context).getDao();

                List<AirPort> ports = new ArrayList<>();

                for (int index = 0; index < jsonArr.length(); ++index) {

                    JSONObject child = jsonArr.getJSONObject(index);
                    String name = child.getString("name");
                    String code = child.getString("code");
                    String area = child.getString("area");
                    String country = child.getString("country");

                    AirPort airPort = new AirPort();
                    airPort.setAirPortName(name);
                    airPort.setCity(area);
                    airPort.setCode(code);
                    airPort.setCountry(country);

                    if (city.contains(airPort.getCity()) || airPort.getAirPortName().contains(city)) {
                        ports.add(airPort);
                    }

                    if (dao.check(code) == null) {
                        dao.insert(airPort);
                    } else {
                        dao.update(airPort);
                    }
                }

                emitter.onNext(ports);
            }
        });
        Consumer<List<AirPort>> consumer = new Consumer<List<AirPort>>() {
            @Override
            public void accept(List<AirPort> list) throws Exception {
                if (list != null && list.size() > 0) {
                    modelCallback.onSuccess(list);
                }

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }


}

