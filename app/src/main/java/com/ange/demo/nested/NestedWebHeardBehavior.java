package com.ange.demo.nested;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.ange.demo.R;

public class NestedWebHeardBehavior extends AppBarLayout.Behavior {
    private static final String TAG = "NestedWebHeardBehavior";

    public NestedWebHeardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        WebView view= (WebView) target;
        Log.d(TAG,"dyUnconsumed="+dyUnconsumed);
        Log.d(TAG,"getScrollY="+view.getScrollY());
        if(dyUnconsumed<0&&view.getScrollY()<=0){//手指由上往下滑动
            if(type==ViewCompat.TYPE_NON_TOUCH){
                Log.d(TAG,"TYPE_NON_TOUCH");
            }
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }
}
