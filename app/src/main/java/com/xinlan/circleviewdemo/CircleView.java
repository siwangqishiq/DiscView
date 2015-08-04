package com.xinlan.circleviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by panyi on 2015/8/4.
 */
public class CircleView extends View {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 300;

    private int stokenWidth = 25;
    private int angle = 100;

    private Paint paint = new Paint();
    private RectF ovalRect = new RectF();

    public CircleView(Context context) {
        super(context);
        initView(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context mContext) {
        paint.setColor(Color.WHITE);//圆圈颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(stokenWidth);//圆圈宽度设置
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        //绘制虚线
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 10);
        paint.setPathEffect(effects);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(120, getWidth() >> 1, getHeight() >> 1);
        int w = getWidth();
        int h = getHeight();
        int radius = Math.min(w, h) >> 1;
        int left = Math.abs(w - h) >> 1;
        ovalRect.set(left + stokenWidth, stokenWidth, left + radius + radius - stokenWidth, radius + radius - stokenWidth);
        canvas.drawArc(ovalRect, 0, angle, false, paint);
        canvas.restore();
    }

    public void setStrokenWidth(int size) {
        if (size <= 0)
            throw new RuntimeException("size can not be zero or -1");
        stokenWidth = size;
        paint.setStrokeWidth(stokenWidth);//圆圈宽度设置
    }

    /**
     * 设置数值
     *
     * @param value
     */
    public void setValue(int value) {
        angle = value;
        invalidate();//重新设置数值
    }
}//end class
