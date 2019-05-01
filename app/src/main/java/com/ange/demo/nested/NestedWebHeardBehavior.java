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
    private static final String TAG="NestedWebHeardBehavior";
    public NestedWebHeardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent child.getBottom()"+child.getBottom());
        return super.onInterceptTouchEvent(parent, child, ev);
    }

//    @Override
//    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        Log.d(TAG, "onNestedScroll child.getBottom()" + child.getBottom()+ " dyUnconsumed="+dyUnconsumed);
//        if (child.getBottom() == 825&&dyUnconsumed<=0) {
//            target.setEnabled(false);
//        } else {
//            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//        }
//    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}
