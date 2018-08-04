package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class AbstractFooterView extends FrameLayout{
    public AbstractFooterView(@NonNull Context context) {
        super(context);
    }

    public AbstractFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    abstract void pulling();//下拉中...
    abstract void loading();//加载中...
    abstract void complete();//加载完毕..
}
