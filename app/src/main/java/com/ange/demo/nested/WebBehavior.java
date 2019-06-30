package com.ange.demo.nested;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ange.demo.R;

public class WebBehavior extends AppBarLayout.ScrollingViewBehavior {
    private final static String TAG="WebBehavior";
    public WebBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(child instanceof NestedWebView){
            NestedWebView nestedWebView= (NestedWebView) child;
            Log.d(TAG,"nestedWebView.getTop():"+nestedWebView.getTop());
            if(nestedWebView.getTop()<=0){
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
