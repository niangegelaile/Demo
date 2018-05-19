package com.example.httpproxy;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private final static String TAG="HttpTool";
    private static HttpTool mHttpTool;
    private final Context context;
    private Api api;
    public static synchronized HttpTool getInstance(Context context){
        if(mHttpTool==null){
            mHttpTool=new HttpTool(context);

        }
        return mHttpTool;
    }



    private HttpTool(Context context){
        this.context=context;
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

        //设置缓存
        File httpCacheDirectory = new File(context.getCacheDir(), "cache_responses_yjbo");
        Cache cache = null;
        try {
            cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }

        builder.addNetworkInterceptor(provideerNetWorkInterceptor());
        OkHttpClient mOkHttpClient=builder
                .cache(cache)
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

    private Interceptor provideerNetWorkInterceptor (){
        return  new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(NetWorkUtils.getNetWorkEnable(context)){
                    Response response = chain.proceed(request);
                    String cacheControl=request.cacheControl().toString();
                    Log.e(TAG,"cacheControl:"+cacheControl);
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + 10000)
                            .build();
                }else {
                   //无网时一直请求有网请求好的缓存数据，不设置过期时间
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)//此处不设置过期时间
                                .build();
                    Response response = chain.proceed(request);
                    //下面注释的部分设置也没有效果，因为在上面已经设置了
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached")
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }




}
