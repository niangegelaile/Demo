package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by ange on 2018/4/18.
 */

public class RefreshView extends FrameLayout{
    public RefreshView(@NonNull Context context) {
        super(context);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
