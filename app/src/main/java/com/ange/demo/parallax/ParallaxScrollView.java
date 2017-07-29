package com.ange.demo.parallax;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

/**
 * Created by ange on 2017/7/7.
 */

public class ParallaxScrollView extends ScrollView {

    private boolean isRestoring;
    private int mActivePointerId;
    private float mInitialMotionY;
    private boolean isBeginDragged;
    private float mScale;
    private float mDistance;
    private int mTouchSlop;

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (isRestoring && action == MotionEvent.ACTION_DOWN) {
            isRestoring = false;
        }
        if (!isEnabled() || isRestoring || (!isScrollToTop() && !isScrollToBottom())) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                isBeginDragged = false;
                float initialMotionY = getMotionEventY(ev);
                if (initialMotionY == -1) {
                    return super.onInterceptTouchEvent(ev);
                }
                mInitialMotionY = initialMotionY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == MotionEvent.INVALID_POINTER_ID) {
                    return super.onInterceptTouchEvent(ev);
                }
                final float y = getMotionEventY(ev);
                if (y == -1f) {
                    return super.onInterceptTouchEvent(ev);
                }
                if (isScrollToTop() && !isScrollToBottom()) {
                    // 在顶部不在底部
                    float yDiff = y - mInitialMotionY;
                    if (yDiff > mTouchSlop && !isBeginDragged) {
                        isBeginDragged = true;
                    }
                } else if (!isScrollToTop() && isScrollToBottom()) {
                    // 在底部不在顶部
                    float yDiff = mInitialMotionY - y;
                    if (yDiff > mTouchSlop && !isBeginDragged) {
                        isBeginDragged = true;
                    }
                } else if (isScrollToTop() && isScrollToBottom()) {
                    // 在底部也在顶部
                    float yDiff = y - mInitialMotionY;
                    if (Math.abs(yDiff) > mTouchSlop && !isBeginDragged) {
                        isBeginDragged = true;
                    }
                } else {
                    // 不在底部也不在顶部
                    return super.onInterceptTouchEvent(ev);
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                isBeginDragged = false;
                break;

        }
        return isBeginDragged||super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId=event.getPointerId(0);
                isBeginDragged=false;
                break;
            case MotionEvent.ACTION_MOVE:
                float y=getMotionEventY(event);
                if(isScrollToTop()&&!isScrollToBottom()){
                    mDistance=y-mInitialMotionY;
                    if(mDistance<0){
                        return super.onTouchEvent(event);

                    }
                    mScale=calculateRate(mDistance);
                    pull(mScale);
                }else if(!isScrollToTop()&&isScrollToBottom()){
                    mDistance=mInitialMotionY-y;
                    if(mDistance<0){
                        return super.onTouchEvent(event);
                    }
                    mScale=calculateRate(mDistance);
                    push(mScale);
                }else if (isScrollToTop() && isScrollToBottom()) {
                    // 在底部也在顶部
                    mDistance = y - mInitialMotionY;
                    if (mDistance > 0) {
                        mScale = calculateRate(mDistance);
                        pull(mScale);
                    } else {
                        mScale = calculateRate(-mDistance);
                        push(mScale);
                    }
                } else {
                    // 不在底部也不在顶部
                    return super.onTouchEvent(event);
                }

                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mActivePointerId=event.getPointerId(MotionEventCompat.getActionIndex(event));
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                break;
            case MotionEvent.ACTION_UP:
                if (isScrollToTop() && !isScrollToBottom()) {
                    animateRestore(true);
                } else if (!isScrollToTop() && isScrollToBottom()) {
                    animateRestore(false);
                } else if (isScrollToTop() && isScrollToBottom()) {
                    if (mDistance > 0) {
                        animateRestore(true);
                    } else {
                        animateRestore(false);
                    }
                } else {
                    return super.onTouchEvent(event);
                }
                break;


        }










        return super.onTouchEvent(event);
    }

    private void animateRestore(final boolean isPullRestore) {
        ValueAnimator animator=ValueAnimator.ofFloat(mScale,1f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                if(isPullRestore){
                    pull(value);
                }else {
                    push(value);
                }
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRestoring=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRestoring=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();




    }

    private void pull(float scale) {
        this.setPivotY(0);
        this.setScaleY(scale);
    }

    private void push(float scale){
        this.setPivotY(this.getHeight());
        this.setScaleY(scale);
    }








    private float calculateRate(float distance) {
        float originalDragPercent=distance/getResources().getDisplayMetrics().heightPixels;
        float dragPrecent=Math.min(1f,originalDragPercent);
        float rate=2f*dragPrecent-(float)Math.pow(dragPrecent,2f);
        return 1+rate/5f;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex=MotionEventCompat.getActionIndex(ev);
        final int pointerId=ev.getPointerId(pointerIndex);
        if(pointerId==mActivePointerId){
            int newPointerIndex=pointerIndex==0?1:0;
            mActivePointerId=ev.getPointerId(newPointerIndex);
        }
    }

    public boolean isScrollToTop() {
        return !ViewCompat.canScrollVertically(this, -1);
    }

    public boolean isScrollToBottom() {
        return !ViewCompat.canScrollVertically(this, 1);
    }


    private float getMotionEventY(MotionEvent ev) {
        int index = ev.findPointerIndex(mActivePointerId);
        return index < 0 ? -1f : ev.getY(index);
    }

}
