package com.ange.demo.sroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by ange on 2017/7/5.
 */

public class MScrollerLayout extends ViewGroup {

    private final static String TAG="MScrollerLayout";

    private Scroller mScroller;

    private int mTouchSlop;

    private float mXDown;

    private float mXMove;

    private float mXLastMove;

    private float leftBorder;

    private float rightBorder;

    public MScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
        ViewConfiguration viewConfiguration=ViewConfiguration.get(context);
        mTouchSlop=viewConfiguration.getScaledPagingTouchSlop();
//        setClickable(true);
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int mL=0;
            for(int i=0;i<getChildCount();i++){
                View child=getChildAt(i);
                child.layout(mL,0,mL+child.getMeasuredWidth(),child.getMeasuredHeight());
                mL+=child.getMeasuredWidth();
            }
            leftBorder=getChildAt(0).getLeft();
            rightBorder=getChildAt(getChildCount()-1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onInterceptTouchEvent:MotionEvent.ACTION_DOWN");
                mXDown=ev.getRawX();
                mXLastMove=mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onInterceptTouchEvent:MotionEvent.ACTION_MOVE");
                mXMove=ev.getRawX();
                mXLastMove=mXMove;
                if(Math.abs(mXMove-mXDown)>mTouchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG,"onTouchEvent:"+(event.getAction()==MotionEvent.ACTION_MOVE?"MotionEvent.ACTION_MOVE":""));
//        Log.e(TAG,"onTouchEvent:"+(event.getAction()==MotionEvent.ACTION_UP?"MotionEvent.ACTION_UP":""));
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onTouchEvent:MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent:"+"MotionEvent.ACTION_MOVE");
                mXMove=event.getRawX();
                float xScroll=mXLastMove-mXMove;
                if(getScrollX()+xScroll+getWidth()>rightBorder){
                    scrollTo((int) (rightBorder-getWidth()),0);
                    return true;
                }else if(getScrollX()+xScroll<leftBorder){
                    scrollTo((int) leftBorder,0);
                    return true;
                }else {
                    scrollBy((int) xScroll,0);
                }
                mXLastMove=mXMove;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onTouchEvent:"+"MotionEvent.ACTION_UP");
                int targetIndex=(getScrollX()+getWidth()/2)/getWidth();
                mScroller.startScroll(getScrollX(),0,getWidth()*targetIndex-getScrollX(),0);//第三个参数是偏移量
                invalidate();
                break;

        }
        return super.onTouchEvent(event);
    }




    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
           scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
