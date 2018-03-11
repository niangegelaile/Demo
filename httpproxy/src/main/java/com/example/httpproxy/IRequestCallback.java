package com.example.httpproxy;


/**
 * Created by ange on 2017/11/28.
 */

public abstract class IRequestCallback<T> {
    public abstract void onSuccess(T response);

    public abstract  void onFailed(String msg);
    /**
     * 网络不可用
     */
    public abstract  void onNotNetwork();
}
