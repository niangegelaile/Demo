package com.example.httpproxy;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public interface Api {
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    @POST
    Observable<ResponseBody> postJson(@Url String url,@Body Map<String,Object> map);
}
