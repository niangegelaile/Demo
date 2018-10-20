package com.ange.demo.WheelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WheelView extends View {
    private static final String TAG = "WheelView";
    private List<String> datas;
    private Paint textPaint;
    private int lineHeight;
    private int textSize = 18;
    private int showCount=3;//要显示的条目数
    private Scroller scroller;
    Rect rect;

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setClickable(true);
        datas = new ArrayList<>();
        datas.addAll(Arrays.asList("1.........", "2........", "3..........", "4.........", "5.........", "6........", "7.........."));
        //画笔
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dptoPx(18));
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点到底是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.LEFT);
        lineHeight = dptoPx(40);
        rect = new Rect();
        scroller = new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textStartPointX = 0;//左下角的点
        int textStartPointY = 0;
        int scrolly = getScrollY();//目前view 滑动的距离
        textStartPointY = scrolly;
//        for(int i=0;i<datas.size();i++){
//            textPaint.getTextBounds(datas.get(i), 0, datas.get(i).length(), rect);
////            textStartPointY=rect.height();//右下角的点
//            Log.e("onDraw","textStartPointX="+textStartPointX+",textStartPointY="+textStartPointY);
//            canvas.drawText(datas.get(i),textStartPointX,rect.height()+textStartPointY,textPaint);
//            canvas.save();
//            canvas.translate(0,lineHeight);
//        }
        drawText(canvas, textStartPointY, textStartPointY + getHeight(), textStartPointX);
    }

    /**
     * @param canvas
     * @param startPointY 绘制文字的起点
     * @param endPointY   绘制文字的结束点
     */
    private void drawText(Canvas canvas, int startPointY, int endPointY, int textStartPointX) {
//        canvas.restore();
        Log.e(TAG, "startPointY:" + startPointY);
        canvas.clipRect(0, startPointY, getWidth(), endPointY);
//        while ((startPointY) < endPointY) {
//            int textIndex = (startPointY) / lineHeight;//偏移的行数
//            int index = 0;
//            index = Math.abs(textIndex % datas.size());
//            String s = datas.get(index);
//            canvas.drawText(s, textStartPointX, startPointY + lineHeight, textPaint);
//            startPointY += lineHeight;
//        }
        int first=getFirstDrawIndex();
        int end=getEndDrawIndex();
        for(int i=first;i<=end;i++){
            String txt=getTextByIndex(i);
            int y=getTextBaseLineY(i);
            canvas.drawText(txt, textStartPointX, y, textPaint);
        }
    }

    private int getTextBaseLineY(int i) {
        int baseLineY=lineHeight*i+lineHeight;
        return baseLineY;//初始基线+scrollY  例子：0*lineHeight+lineHeight+getScrollY

    }

    /**
     * 根据条目获取数据
     * @param i
     * @return
     */
    private String getTextByIndex(int i) {
        while(i<0){
            i+=datas.size();
        }
        i=i%datas.size();
        return datas.get(i);
    }

    /**
     * 第一个可见的条目
     * @return
     */
    private int getFirstDrawIndex() {//以第一个为标准,返回第一个要绘制的条目
        int offset = Math.abs(getScrollY()) % lineHeight;//不够整个
        int index = getScrollY() / lineHeight;
        if(index>0){
            if(offset>0){
                 index-=1;
            }
        }
        Log.e(TAG,"getFirstDrawIndex="+index);
        return index;
    }

    /**
     * 最后一个可见的条目
     * @return
     */
    private int getEndDrawIndex() {//以第一个为标准,返回第一个要绘制的条目
        int offset =Math.abs(getScrollY())  % lineHeight;//不够整个
        int index = getScrollY() / lineHeight;
        if(index>0){
            if(offset>0){
                index+=showCount-1;
            }
        }
        if(index<=0){
            if(offset>=0){
                index+=showCount;
            }
        }
        Log.e(TAG,"getEndDrawIndex="+index+" scrollY="+getScrollY()+" offset="+offset);
        return index;
    }









    private float perX, perY;
    private float dy, dx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                perX = event.getX();
                perY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = event.getX();
                float curY = event.getY();
                dy = curY - perY;
                dx = curX - perX;
                scrollBy(0, (int) dy);
                perX = curX;
                perY = curY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int offsetY = getScrollY() % lineHeight;
                scroller.startScroll(0, getScrollY(), 0, -offsetY);
                invalidate();
                break;
        }

        return true;
    }


    private int dptoPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (dp * density);
    }
}
