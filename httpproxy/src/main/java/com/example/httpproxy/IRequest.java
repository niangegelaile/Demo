package com.example.httpproxy;


import java.util.Map;

/**
 * 请求接口
 * Created by ange on 2017/11/28.
 */

public interface IRequest {

//    <T> ICancelTool request(String jsonParam, String apiUrl, IRequestCallback<T> callback);

    <T> ICancelTool request(Map<String, Object> map, String apiUrl, IRequestCallback<T> callback);
}
