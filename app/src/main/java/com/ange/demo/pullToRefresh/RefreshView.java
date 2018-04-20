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

public class RefreshView extends ViewGroup{
    private final static String TAG="RefreshView";
    private View headerView;
    private View contentView;
    private View footerView;
    private Scroller mScroller;

    private int mTouchSlop;

    private float mYDown;

    private float mYMove;

    private float mYLastMove;

    private float topBorder;

    private float bottomBorder;



    public RefreshView(@NonNull Context context) {
        this(context,null);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        int layoutIdHeader= a.getResourceId(R.styleable.RefreshView_header,R.layout.view_header);
        int layoutIdFooter= a.getResourceId(R.styleable.RefreshView_footer,R.layout.view_footer);

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        headerView=layoutInflater.inflate(layoutIdHeader,null);
        ViewGroup.LayoutParams lp = headerView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(-1, 140);
            headerView.setLayoutParams(lp);
        }
        addView(headerView);
        footerView=layoutInflater.inflate(layoutIdFooter,null);
        ViewGroup.LayoutParams lp1 = footerView.getLayoutParams();
        if (lp1 == null) {
            lp1 = new LayoutParams(-1, 140);
            footerView.setLayoutParams(lp1);
        }
        addView(footerView);
        a.recycle();
        mScroller=new Scroller(context);
        ViewConfiguration viewConfiguration=ViewConfiguration.get(context);
        mTouchSlop=viewConfiguration.getScaledPagingTouchSlop();
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
            int mT=0-headerView.getMeasuredHeight();
            headerView.layout(0,mT,headerView.getMeasuredWidth(),mT+headerView.getMeasuredHeight());
            mT=mT+headerView.getMeasuredHeight();
            contentView=findViewById(R.id.tv_content);
            contentView.layout(0,mT,contentView.getMeasuredWidth(),mT+contentView.getMeasuredHeight());
            mT=mT+contentView.getMeasuredHeight();
            footerView.layout(0,mT,footerView.getMeasuredWidth(),mT+footerView.getMeasuredHeight());
            topBorder=headerView.getTop();
            bottomBorder=footerView.getBottom();
            Log.e(TAG,"topBorder:"+topBorder+" bottomBorder:"+bottomBorder);
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mYDown=ev.getRawY();
                mYLastMove=mYDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mYMove=ev.getRawY();
                mYLastMove=mYMove;
                if(Math.abs(mYMove-mYDown)>mTouchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mYMove=event.getRawY();
                float yScroll=mYLastMove-mYMove;
                Log.e(TAG, "onTouchEvent: "+"yScroll:"+yScroll);
                if(getScrollY()+yScroll+getHeight()>bottomBorder){
                    scrollTo( 0,(int)(bottomBorder-getHeight()));//相对于顶部滑动的距离
                    return true;
                }else if(getScrollY()+yScroll<topBorder){
                    scrollTo(0,(int)topBorder);
                    return true;
                }else {
                    scrollBy(0,(int)yScroll);
                }
                mYLastMove=mYMove;
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY()<0){
                    mScroller.startScroll(0,getScrollY(),0,0-getScrollY());//第三个参数是偏移量
                    invalidate();
                }
                if(getScrollY()>0){
                    mScroller.startScroll(0,getScrollY(),0,-getScrollY());//第三个参数是偏移量
                    invalidate();
                }
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
