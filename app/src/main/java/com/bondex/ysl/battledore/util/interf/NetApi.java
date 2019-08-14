package com.bondex.ysl.battledore.util.interf;

import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public interface NetApi {


    @GET("")
    Observable<String> getCall();


    @FormUrlEncoded
    @POST("http://cas.bondex.com.cn:8080/cslogin.jsp")
    Observable<String> login(@Field("userid") String userid, @Field("pwd") String pwd);

//    通讯录
    @FormUrlEncoded
    @POST("http://wapi.bondex.com.cn:7111/api/Employee/select/PersonList")
    Observable<String> getPhone(@FieldMap Map<String, String> map);

//    检验token是否过时
    @FormUrlEncoded
    @POST("http://cas.bondex.com.cn:8080/castokenValidate.jsp")
    Observable<String> isTokenValid(@Field("token") String token);

}
