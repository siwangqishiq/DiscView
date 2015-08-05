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

/**
 * Created by panyi on 2015/8/4.
 */
public class CircleView extends View {
    public static final int PAD = 2;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 300;

    private Context mContext;

    //圆环模式
    private static final int CIRCLE_MODE_NORMAL = 1;//普通模式
    private static final int CIRCLE_MODE_ROUND = 2;//圆角模式(文艺模式)
    private static final int CIRCLE_MODE_DOT = 3;//虚线模式(2B模式)
    protected int mCircleMode = CIRCLE_MODE_NORMAL;//圆环模式 默认为普通模式

    private static final int EXTRA_CIRCLE_WIDTH = 1;
    protected boolean mOuterCircleIsShow = false;//外部圆环是否显示  默认不显示
    protected int mOuterCirclePad = 20;//外部圆环距离主圆环边距
    private Paint extraCirclePaint = new Paint();

    protected int stokenWidth = 20;//圆环宽度
    private int angle = 30;
    private Paint paint = new Paint();//绘制主圆环画笔
    private RectF ovalRect = new RectF();
    protected int mRadiusColor = Color.WHITE;//圆环颜色

    public CircleView(Context context) {
        super(context);
        initView(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context mContext, AttributeSet attrs) {
        this.mContext = mContext;

        paint.setColor(mRadiusColor);//圆圈颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(stokenWidth);//圆圈宽度设置

        setCircleMode(mCircleMode);//设置圆环模式

        //装饰圆形 Paint
        extraCirclePaint.setColor(mRadiusColor);//圆为白色
        extraCirclePaint.setStyle(Paint.Style.STROKE);//设置空心
        extraCirclePaint.setStrokeWidth(EXTRA_CIRCLE_WIDTH);//圆圈宽度设置


    }


    //外圆设置
    protected void setOuterCircle(boolean isShow, int size) {
        this.mOuterCircleIsShow = isShow;
        if (mOuterCircleIsShow) {//显示外圆
            mOuterCirclePad = size;
        } else {//不显示外圆
            mOuterCirclePad = 0;
        }//end if
    }

    //设置圆圈颜色
    public void setRadiusColor(int color) {
        this.mRadiusColor = color;
        paint.setColor(mRadiusColor);
    }

    //设置圆环模式
    protected void setCircleMode(int mode) {
        mCircleMode = mode;
        switch (mCircleMode) {
            case CIRCLE_MODE_ROUND:
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setStrokeCap(Paint.Cap.ROUND); //设置圆角
                break;
            case CIRCLE_MODE_DOT:
                PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 10);
                paint.setPathEffect(effects);
                break;
            default:
        }//end switch
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        //圆心点坐标
        int centerX = w >> 1;
        int centerY = h >> 1;

        canvas.save();
        canvas.rotate(90, centerX, centerY);//坐标系旋转

        int outerCircleRadius = (Math.min(w, h) >> 1) - PAD;//外圆半径
        if (mOuterCircleIsShow) {//外装饰圆显示
            canvas.drawCircle(centerX, centerY, outerCircleRadius, extraCirclePaint);
        }

        //主圆绘制
        int radius = outerCircleRadius - mOuterCirclePad - (stokenWidth >> 1);
        int left = (w >> 1) - radius;
        int top = (h >> 1) - radius;
        ovalRect.set(left, top,
                left + radius + radius, top + radius + radius);
        canvas.drawArc(ovalRect, 0, angle, false, paint);

        canvas.restore();
    }

    public void setStrokenWidth(int size) {
        if (size <= 0)
            throw new IllegalStateException("size can not be zero or -1");
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
