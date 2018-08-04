package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.ange.demo.R;

public class FooterViewImp extends AbstractFooterView {
    public FooterViewImp(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_footer,this);
    }

    @Override
    void pulling() {

    }

    @Override
    void loading() {

    }

    @Override
    void complete() {

    }
}
