package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by ange on 2018/5/12.
 */

public abstract class HeadView extends FrameLayout {
    public HeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public HeadView(@NonNull Context context) {
        super(context);
    }
    abstract void pulling();//下拉中...
    abstract void loading();//加载中...
    abstract void complete();//加载完毕..
}
