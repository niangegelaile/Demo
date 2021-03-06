package com.ange.demo.pullToRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.ange.demo.R;

/**
 * Created by ange on 2018/4/18.
 */

public class RefreshView extends ViewGroup {
    private final static String TAG = "RefreshView";
    //刷新状态
    private final static int STATUS_NORMAL = 0;
    private final static int STATUS_PULL = 1;
    private final static int STATUS_HEADER_LOADING = 2;
    private final static int STATUS_FOOTER_LOADING = 3;

    private  int HEAD_HEIGHT = 0;
    private  int FOOTER_HEIGHT = 0;
    private int status = STATUS_NORMAL;
    private boolean contentViewDisable;
    private OnRefreshListener onRefreshListener;
    private HeadView headerView;
    private View contentView;
    private AbstractFooterView footerView;
    private Scroller mScroller;

    private int mTouchSlop;

    private float mYDown;

    private float mYMove;

    private float mYLastMove;

    private float topBorder;//顶部边界

    private float bottomBorder;//底部边界


    public RefreshView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        setClickable(true);
    }

    public void addHeadView(HeadView view) {
        this.headerView = view;
        ViewGroup.LayoutParams lp = headerView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);//头部高度
            headerView.setLayoutParams(lp);
        }
        addView(headerView);
    }
    public void addFootView(AbstractFooterView view) {
        this.footerView = view;
        ViewGroup.LayoutParams lp = footerView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(lp);
        }
        addView(footerView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 3) {
            throw new IllegalStateException("RefreshView only can host 3 elements");
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) != headerView && getChildAt(i) != footerView) {
                contentView = getChildAt(i);
                break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        HEAD_HEIGHT= headerView.getMeasuredHeight();
        FOOTER_HEIGHT=footerView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            int mT = 0 - headerView.getMeasuredHeight();
            headerView.layout(0, mT, headerView.getMeasuredWidth(), mT + headerView.getMeasuredHeight());
            mT = mT + headerView.getMeasuredHeight();
            contentView.layout(0, mT, contentView.getMeasuredWidth(), mT + contentView.getMeasuredHeight());
            mT = mT + contentView.getMeasuredHeight();
            footerView.layout(0, mT, footerView.getMeasuredWidth(), mT + footerView.getMeasuredHeight());
            topBorder = headerView.getTop();
            bottomBorder = footerView.getBottom();
            Log.e(TAG, "topBorder:" + topBorder + " bottomBorder:" + bottomBorder);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mYDown = ev.getRawY();
                mYLastMove = mYDown;
                break;

            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getRawY();
                float yScroll = mYLastMove - mYMove;
                Log.e(TAG, "onTouchEvent: " + "mYLastMove:" + mYLastMove+"mYMove:"+mYMove);
                Log.e(TAG, "onTouchEvent: " + "yScroll:" + yScroll);
                if(yScroll<0&&contentView.canScrollVertically(-1)){//下拉且contentView 没滑到顶
                    return super.dispatchTouchEvent(ev);
                }else if(yScroll>0&&contentView.canScrollVertically(1)){//上啦且contentView 没滑到底
                    return super.dispatchTouchEvent(ev);
                }else {
                    if (getScrollY() + yScroll + getHeight() > bottomBorder) {
                        scrollTo(0, (int) (bottomBorder - getHeight()));//相对于顶部滑动的距离
                        return true;
                    } else if (getScrollY() + yScroll < topBorder) {
                        double x = topBorder - getScrollY() - yScroll;//超出上边界的高度
                        double pos = topBorder - x * (0.4 + (1 / (4 + x)));
                        scrollTo(0, (int) pos);
                        return true;
                    } else {
                        scrollBy(0, (int) yScroll);
                    }
                }

                mYLastMove = mYMove;

             return true;
            case MotionEvent.ACTION_UP:
                dealStatus();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(contentViewDisable){
            return true;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 处理松手后的状态
     */
    private void dealStatus() {
        if (getScrollY() <= -HEAD_HEIGHT) {
            status = STATUS_HEADER_LOADING;
        }
        if(getScrollY()>=FOOTER_HEIGHT){
            status = STATUS_FOOTER_LOADING;
        }


        if (status == STATUS_HEADER_LOADING) {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY() - HEAD_HEIGHT);//第三个参数是偏移量
            invalidate();
            if (onRefreshListener != null) {
                onRefreshListener.onRefresh();
            }
            contentViewDisable=true;
            headerView.loading();
        } else if(status == STATUS_FOOTER_LOADING){
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY() +FOOTER_HEIGHT);//第三个参数是偏移量
            invalidate();
            if (onRefreshListener != null) {
                onRefreshListener.onLoadMore();
            }
            contentViewDisable=true;
            footerView.loading();
        } else {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY());//第三个参数是偏移量
            invalidate();
        }
    }

    /**
     * 刷新完后复位
     */
    public void complete() {
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY());//第三个参数是偏移量
        invalidate();
        status = STATUS_NORMAL;
        headerView.complete();
        contentViewDisable=false;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
