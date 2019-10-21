package com.bondex.ysl.battledore.install.download;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import com.bondex.ysl.battledore.util.interf.NetApi;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpConnection {

    private static final String BASE_URL = "http://wol.bondex.com.cn:8089/";
    private static final String VERSION_URL = "http://cas.bondex.com.cn:8080/";


    private static final OkHttpClient httpClient = new OkHttpClient
            .Builder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .build();


    public static Retrofit getRretrofit(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }


    public static void donwolad(String url, final String filePath, final DownloadListener listener) {

        String httpurl = url.substring(0,url.lastIndexOf("/")+1);
        String name = url.substring(url.lastIndexOf("/")+1,url.length());
        Log.i("INstall","httpurl  "+httpurl+"  \n  name "+name);
        NetApi netApi = HttpConnection.getRretrofit(httpurl).create(NetApi.class);

        netApi.downloadFile(name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.writeResponseToDisk(filePath, response, listener);
                    }
                });
                thread.setDaemon(true);
                thread.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
