package com.ange.demo.nested;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.EdgeEffect;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v4.view.ViewCompat.TYPE_TOUCH;
import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

public class NestedWebView extends WebView implements NestedScrollingChild2 {
    private NestedScrollingChildHelper mScrollingChildHelper;
    private int mLastMotionY;
    /**
     * 用于跟踪触摸事件速度的辅助类，用于实现
     * fling 和其他类似的手势。
     */
    private VelocityTracker mVelocityTracker;
    /**
     * True if the user is currently dragging this ScrollView around. This is
     * not the same as 'is being flinged', which can be checked by
     * mScroller.isFinished() (flinging begins when the user lifts his finger).
     */
    private boolean mIsBeingDragged = false;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.（多点触控有用）
     */
    private int mActivePointerId = INVALID_POINTER;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private OverScroller mScroller;
    private int mNestedYOffset;
    private int mLastScrollerY;
    private int mLastY;
    private int moveDistance;
    private static final String TAG = "NestedWebView";

    public NestedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(getContext());
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        setNestedScrollingEnabled(true);//设置支持嵌套滑动
    }

    public NestedWebView(Context context) {
        this(context, null);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        boolean eventAddedToVelocityTracker = false;
        MotionEvent vtev = MotionEvent.obtain(event);//复制一个event
        final int actionMasked = event.getActionMasked();//类似getAction
        boolean result = false;
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0;
            isFlinging = false;
        }
        vtev.offsetLocation(0, mNestedYOffset);

        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                if ((mIsBeingDragged = !mScroller.isFinished())) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);//不让父布局拦截事件
                    }
                }

                /*
                 * If being flinged and user touches, stop the fling. isFinished
                 * will be false if being flinged.//如果在fling 的过程中用户触摸屏幕，则停止fling
                 */
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                // Remember where the motion event started
                mLastMotionY = (int) event.getY();
                mLastY = mLastMotionY;
                mActivePointerId = event.getPointerId(0);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
                result = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:

                final int activePointerIndex = event.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    break;
                }
                final int y = (int) event.getY(activePointerIndex);

                int deltaY = mLastMotionY - y;
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset, ViewCompat.TYPE_TOUCH)) {
                    Log.d(TAG, "dispatchNestedPreScroll消费：" + mScrollConsumed[1]);
                    deltaY -= mScrollConsumed[1];//纵轴位移- 被父布局消费的滑动距离
                    Log.d(TAG, "dispatchNestedPreScroll未消费：" + deltaY);
                }
                moveDistance = deltaY;
                if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = true;
                }

                if (mIsBeingDragged) {
                    mLastMotionY = y - mScrollOffset[1];//上一次的坐标
                    int scrolledDeltaY = 0;
                    int unconsumedY = deltaY;
                    if (Math.abs(deltaY) > 0) {

                        if (deltaY <= 0) {
                            if (canScrollVertically(-1)) {//向顶部滑动
                                if (getScrollY() + deltaY < 0) {
                                    scrolledDeltaY = -getScrollY();
                                    unconsumedY = getScrollY() + deltaY;
                                } else {
                                    scrolledDeltaY = deltaY;
                                    unconsumedY = 0;
                                }
                            }
                        } else {
                            if (canScrollVertically(1)) {
                                //todo 这里没有处理底部的事件传递给父布局，本例不需要
                                Log.d(TAG, "canScrollVertically:deltaY:" + deltaY);
                                if (deltaY - getTop() > 0) {
                                    scrolledDeltaY = deltaY - getTop();
                                    unconsumedY = getTop();
                                } else {
                                    scrolledDeltaY = deltaY;
                                    unconsumedY = 0;
                                }
                            }
                        }
                    }
                    if (dispatchNestedScroll(0, scrolledDeltaY, 0, unconsumedY, mScrollOffset)) {
                        mLastMotionY -= mScrollOffset[1];
                        vtev.offsetLocation(0, mScrollOffset[1]);//让webView 不滑动 ，webView 位置 偏移，主要是这行代码
                        mNestedYOffset += mScrollOffset[1];
                    }
                }
                if (deltaY == 0 && mIsBeingDragged) {
                    result = true;
                } else {
                    result = super.onTouchEvent(vtev);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mVelocityTracker != null) {
                    mVelocityTracker.addMovement(vtev);
                }
                eventAddedToVelocityTracker = true;
                caculateV(mActivePointerId, (int) event.getY());
                mLastY = (int) event.getY();
                mActivePointerId = INVALID_POINTER;
                endDrag();
                if (mVelocityTracker != null) {
                    mVelocityTracker.clear();
                }
                stopNestedScroll(TYPE_TOUCH);
                result = super.onTouchEvent(vtev);
                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                endDrag();
                if (mVelocityTracker != null) {
                    mVelocityTracker.clear();
                }
                stopNestedScroll(TYPE_TOUCH);
                result = super.onTouchEvent(event);
                break;
        }
        if (!eventAddedToVelocityTracker) {
            if (mVelocityTracker != null) {
                mVelocityTracker.addMovement(vtev);
            }
        }
        vtev.recycle();
        return result;
    }

    private boolean isFlinging;

    /**
     * 处理fling 速度问题
     * @param mActivePointerId
     * @param curY
     */
    private void caculateV(int mActivePointerId, int curY) {
        mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        int initialVelocity = (int) mVelocityTracker.getYVelocity(mActivePointerId);
        Log.d(TAG, " caculateV curY:" + curY + " mLastY:" + mLastY + " initialVelocity:" + initialVelocity);
        if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
            mLastScrollerY = getScrollY();
            isFlinging = true;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (isFlinging) {
            int dy = getScrollY() - mLastScrollerY;
            if (getScrollY() == 0) {
                int velocityY = 1000;
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH);
                if (moveDistance < 0) {
                    dispatchNestedScroll(0, dy, 0, -velocityY, null,
                            ViewCompat.TYPE_NON_TOUCH);
                }
                isFlinging = false;
                stopNestedScroll(ViewCompat.TYPE_NON_TOUCH);
            }
        }
        Log.d(TAG, "computeScroll webView:getScrollY:" + getScrollY());
    }

    private void endDrag() {
        mIsBeingDragged = false;
        recycleVelocityTracker();
        stopNestedScroll(ViewCompat.TYPE_TOUCH);
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void flingScroll(int vx, int vy) {
        super.flingScroll(vx, vy);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return getScrollingChildHelper().startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }
}
