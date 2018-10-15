package com.ange.demo.WheelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WheelView extends View {

    private List<String> datas;
    private Paint textPaint;
    private int lineHeight;
    Rect rect;
    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setClickable(true);
        datas=new ArrayList<>();
        datas.addAll(Arrays.asList("1.........","2........","3..........","4.........","5.........","6........","7.........."));
        //画笔
        textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(18);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点到底是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
        lineHeight=40;
        rect=new Rect();

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textStartPointX=0;//左下角的点
        int textStartPointY=0;
        for(int i=0;i<1;i++){
            textPaint.getTextBounds(datas.get(i), 0, datas.get(i).length(), rect);
            textStartPointY=rect.height();//右下角的点
            Log.e("onDraw","textStartPointX="+textStartPointX+",textStartPointY="+textStartPointY);
            canvas.drawText(datas.get(i),textStartPointX,textStartPointY,textPaint);
//            canvas.save();
//            canvas.translate(0,lineHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
