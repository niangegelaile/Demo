package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ange.demo.R;

/**
 * Created by ange on 2018/4/18.
 */

public class RefreshView extends ViewGroup{
    private View headerView;
    private View contentView;
    private View footerView;
    public RefreshView(@NonNull Context context) {
        this(context,null);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        int layoutIdHeader= a.getResourceId(R.styleable.RefreshView_header,R.layout.view_header);
        int layoutIdFooter= a.getResourceId(R.styleable.RefreshView_footer,R.layout.view_footer);

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        headerView=layoutInflater.inflate(layoutIdHeader,this);
        footerView=layoutInflater.inflate(layoutIdFooter,this);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if(b){
            int mT=0;
            headerView.layout(0,mT,headerView.getMeasuredWidth(),headerView.getMeasuredHeight());
            mT=headerView.getMeasuredHeight();
//            contentView=findViewById(R.id.tv_content);
//            contentView.layout(0,mT,contentView.getMeasuredWidth(),contentView.getMeasuredHeight());
//            mT=mT+contentView.getMeasuredHeight();
            footerView.layout(0,mT,footerView.getMeasuredWidth(),footerView.getMeasuredHeight());
        }
    }
}
