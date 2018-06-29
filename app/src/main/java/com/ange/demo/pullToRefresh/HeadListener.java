package com.ange.demo.pullToRefresh;

/**
 * Created by ange on 2018/5/12.
 */

public interface HeadListener {
    void pulling();//下拉中...
    void loading();//加载中...
    void complete();//加载完毕..
}
