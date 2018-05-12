package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ange.demo.R;

/**
 * Created by ange on 2018/5/12.
 */

public class HeadView extends FrameLayout implements HeadListener{
    private View view;
    private ImageView ivRocket;
    private AnimationDrawable mAnimation;
    public HeadView(@NonNull Context context) {
        this(context,null);
    }

    public HeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        view= LayoutInflater.from(getContext()).inflate(R.layout.view_header,this);
        ivRocket=view.findViewById(R.id.iv_rocket);
        mAnimation= (AnimationDrawable) ivRocket.getBackground();
    }

    @Override
    public void pulling() {

    }

    @Override
    public void loading() {
        mAnimation.start();
    }

    @Override
    public void complete() {
        mAnimation.stop();
    }
}
