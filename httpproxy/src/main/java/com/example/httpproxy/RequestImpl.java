package com.example.httpproxy;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 使用retrofit 实现的请求
 * Created by ange on 2017/11/28.
 */

public class RequestImpl implements IRequest {
    private Context mContext;
    private Api api;
    public RequestImpl(Context context) {
        this.mContext=context;
        this.api=HttpTool.getInstance(mContext).getApi();
    }

    @Override
    public <T> ICancelTool get(Map<String,Object> jsonParam, String apiUrl, final IRequestCallback<T> callback) {
//        if (NetWorkUtils.getNetWorkEnable(mContext)) {
//            Map<String, String> params = new HashMap<String, String>();
//            if(jsonParam!=null){
//                params.put("p",jsonParam);
//            }
            Subscription sb=api
                    .get(apiUrl,jsonParam)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onFailed(e.toString());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String result=responseBody.string();
                                Log.d("http",result);
                                Type cls=analysisClassInfo(callback);
                                String s=cls.toString();
                                if(!s.equals("class java.lang.String")){
                                    T response= (T) GsonService.parseJson(result,cls);
                                    callback.onSuccess(response);
                                }else {
                                    callback.onSuccess((T)result);
                                }

                            } catch (IOException e) {
                                callback.onFailed(e.toString());
                            }
                        }
                    });
            return new CancelTool(sb);
//        }else {
//            callback.onNotNetwork();
//            return new CancelTool(null);
//        }
    }

    @Override
    public <T> ICancelTool post(Map<String, Object> params, String apiUrl,final IRequestCallback<T> callback) {
//        if (NetWorkUtils.getNetWorkEnable(mContext)) {

            Subscription sb=api
                    .postJson(apiUrl,params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onFailed(e.toString());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String result=responseBody.string();
                                Log.d("http",result);
                                Type cls=analysisClassInfo(callback);
                                String s=cls.toString();
                                if(!s.equals("class java.lang.String")){
                                    T response= (T) GsonService.parseJson(result,cls);
                                    callback.onSuccess(response);
                                }else {
                                    callback.onSuccess((T)result);
                                }

                            } catch (IOException e) {
                                callback.onFailed(e.toString());
                            }
                        }
                    });
            return new CancelTool(sb);
//        }else {
//            callback.onNotNetwork();
//            return new CancelTool(null);
//        }
    }

    /**
     * 获取泛型
     * @param object 必须是类，不能是接口
     * @return
     */
    protected Type analysisClassInfo(Object object){
        Type genType=object.getClass().getGenericSuperclass();
        Type[] params=((ParameterizedType)genType).getActualTypeArguments();
        return params[0];
    }
}
