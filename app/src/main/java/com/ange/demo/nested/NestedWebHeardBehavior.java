package com.ange.demo.nested;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class NestedWebHeardBehavior extends AppBarLayout.Behavior {
    private static final String TAG = "NestedWebHeardBehavior";

    public NestedWebHeardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent child.getBottom()" + child.getBottom());

        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
//        int top = child.getTop();
//        int bottom = child.getBottom();
//
//        if (dy < 0 && bottom > 0) {
//            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        }else if(dy>0&&top<=0){
//            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        }
        Log.d(TAG,"dy="+dy);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}
