package com.ange.demo.controlView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ange.demo.R;

public class ControlView extends ViewGroup {

    private View viewCenter;
    private int viewCenterSize=62;
    int w,h;
    int touchX;
    int touchY;
    public ControlView(Context context) {
        this(context,null);
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setClickable(true);
    }

    private void initView(Context context) {
        DisplayMetrics metrics= context.getResources().getDisplayMetrics();
        viewCenterSize=(int)metrics.density*viewCenterSize;
        setBackgroundResource(R.mipmap.bg_rl_control);
        viewCenter=new View(context);
        viewCenter.setBackgroundResource(R.mipmap.bg_but_control_press);
        addView(viewCenter,new LayoutParams(viewCenterSize,viewCenterSize));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w=getMeasuredWidth();
        h=getMeasuredHeight();
        measureChild(viewCenter, widthMeasureSpec, heightMeasureSpec);
        touchX=w/2;
        touchY=h/2;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        viewCenter.layout(touchX-viewCenterSize/2,
                touchY-viewCenterSize/2,
                touchX-viewCenterSize/2+viewCenter.getMeasuredWidth(),
                touchY-viewCenterSize/2+viewCenter.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX= (int) event.getX();
                touchY= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touchX= (int) event.getX();
                touchY= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchX=w/2;
                touchY=h/2;
                break;
        }
        requestLayout();
        return true;
    }
}
