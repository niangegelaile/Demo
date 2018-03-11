package com.example.httpproxy;

import android.content.Context;




/**
 * Created by ange on 2017/11/28.
 */

public class HttpUtil {
    private IRequest request;
    private HttpUtil(Context context){
        request=new RequestImpl(context.getApplicationContext());
    }
    private static HttpUtil httpUtil;
    public  static synchronized IRequest getInstance(Context context){
        if(httpUtil==null){
            httpUtil=new HttpUtil(context);
        }
        return httpUtil.getRequest();
    }
    public IRequest getRequest(){
        return request;
    }
}
