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

    public WebBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        WebView view=child.findViewById(R.id.web);
        View appbar=parent.findViewById(R.id.appbar);
        Log.d("WebBehavior","appbar.getBottom()="+appbar.getBottom());
        if(appbar.getBottom()>0){
            Log.d("WebBehavior","ev.getY"+ev.getY());
            return true;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        WebView view=child.findViewById(R.id.web);
        if(view.getTop()<=0){
            Log.d("WebBehavior","ev.getY"+ev.getY());
            return view.onTouchEvent(ev);
        }
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
