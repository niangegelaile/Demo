package com.example.httpproxy;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public interface Api {
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Observable<ResponseBody> postJson(@Url String url,@Body Map<String,Object> map);
}
