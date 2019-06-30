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
}
