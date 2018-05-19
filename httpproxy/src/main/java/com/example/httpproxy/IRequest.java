package com.example.httpproxy;


import java.util.Map;

/**
 * 请求接口
 * Created by ange on 2017/11/28.
 */

public interface IRequest {

    <T> ICancelTool post(Map<String, Object> map, String apiUrl, IRequestCallback<T> callback);

    <T> ICancelTool get(Map<String, Object> map, String apiUrl, IRequestCallback<T> callback);
}
