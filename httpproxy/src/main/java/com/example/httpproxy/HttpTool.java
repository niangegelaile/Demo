package com.example.httpproxy;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public class HttpTool {

    private static HttpTool mHttpTool;
    private Api api;
    public static synchronized HttpTool getInstance(){
        if(mHttpTool==null){
            mHttpTool=new HttpTool();
        }
        return mHttpTool;
    }

    private HttpTool(){
        api=providerRetrofit(
                providerOkHttpClient(),
                providerConverterFactory(),
                providerCallAdapterFactory(),
                "http://pension.uat.hengtech.com.cn/")
                .create(Api.class);

    }

    public Api getApi() {
        return api;
    }

    public OkHttpClient providerOkHttpClient(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient mOkHttpClient=builder
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .build();
        return mOkHttpClient;
    }

    /**
     * 提供json 解析的factory
     * @return
     */
    public Converter.Factory providerConverterFactory(){
        return GsonConverterFactory.create();
    }

    /**
     * 提供rxjava支持
     * @return
     */
    public CallAdapter.Factory providerCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }

    /**
     * 提供 网络请求
     * @param okHttpClient
     * @param converFactory
     * @param callAdapterFactory
     * @return
     */
    public Retrofit providerRetrofit(OkHttpClient okHttpClient,
                                     Converter.Factory converFactory,
                                     CallAdapter.Factory callAdapterFactory,
                                    String baseUrl){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(converFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }
}
