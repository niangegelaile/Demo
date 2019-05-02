package com.ange.demo.nested;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
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
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {

        WebView view=coordinatorLayout.findViewById(R.id.web);
        Log.d(TAG,"getScrollY="+view.getScrollY());
        Log.d(TAG,"dy="+dy);
        if(view.getScrollY()<=0){
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }

    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        WebView view=coordinatorLayout.findViewById(R.id.web);
        Log.d(TAG,"dyUnconsumed="+dyUnconsumed);
        if(view.getScrollY()<=0){

           super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }



    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}
