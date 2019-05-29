package com.bondex.ysl.battledore.util.interf;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public interface NetApi {


    @GET("")
    Observable<String> getCall();


}
