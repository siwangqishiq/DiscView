package com.xinlan.discview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by panyi on 2015/8/4.
 */
public class CircleView extends View {
    public static final int PAD = 30;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 360;

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

    protected boolean mInnerCircleIsShow = false;//内装饰圆是否显示
    protected int mInnerCirclePad = 20;

    protected int stokenWidth = 20;//圆环宽度
    private int angle = 0;
    private Paint paint = new Paint();//绘制主圆环画笔
    private RectF ovalRect = new RectF();
    protected int mRadiusColor = Color.WHITE;//圆环颜色

    protected boolean isBottomCircleShow = false;//底部圆是否显示  默认不显示
    private Paint bottomPaint = new Paint();

    private Bitmap indicatorBitmap;//圆形指示器Bitmap
    private RectF bitDistRect;
    private Rect bitSrcRect;

    protected int startRotateAngle = MIN_VALUE;//圆盘开始角度 默认为最底部0度
    protected int angleRotateSpan = MAX_VALUE;//圆盘可旋转角度范围

    private RectF tempRect = new RectF();

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
        paint.setAntiAlias(true);//反锯齿

        bottomPaint.setStyle(Paint.Style.STROKE);//设置空心
        bottomPaint.setStrokeWidth(stokenWidth);//圆圈宽度设置

        setCircleMode(mCircleMode);//设置圆环模式

        //装饰圆形 Paint
        extraCirclePaint.setColor(mRadiusColor);//圆为白色
        extraCirclePaint.setStyle(Paint.Style.STROKE);//设置空心
        extraCirclePaint.setStrokeWidth(EXTRA_CIRCLE_WIDTH);//圆圈宽度设置
        extraCirclePaint.setAntiAlias(true);//反锯齿
    }

    protected void setIndicatorBit(Bitmap bit) {
        indicatorBitmap = bit;
        if (indicatorBitmap != null) {
            bitSrcRect = new Rect(0, 0, indicatorBitmap.getWidth(), indicatorBitmap.getHeight());
            bitDistRect = new RectF(0, 0, bitSrcRect.width(), bitSrcRect.height());
        }//end if
    }

    //内圆设置
    protected void setInnerCircle(boolean isShow, int pad) {
        this.mInnerCircleIsShow = isShow;
        mInnerCirclePad = mInnerCircleIsShow ? pad : 0;
    }

    //外圆设置
    protected void setOuterCircle(boolean isShow, int pad) {
        this.mOuterCircleIsShow = isShow;
        mOuterCirclePad = mOuterCircleIsShow ? pad : 0;
    }

    //设置圆圈颜色
    public void setRadiusColor(int color) {
        this.mRadiusColor = color;
        paint.setColor(mRadiusColor);
        setBottomCircleShow(isBottomCircleShow);
    }

    //设置圆环模式
    protected void setCircleMode(int mode) {
        mCircleMode = mode;
        switch (mCircleMode) {
            case CIRCLE_MODE_ROUND:
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setStrokeCap(Paint.Cap.ROUND); //设置圆角
                bottomPaint.setStrokeJoin(Paint.Join.ROUND);
                bottomPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角
                break;
            case CIRCLE_MODE_DOT:
                PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 10);
                paint.setPathEffect(effects);
                bottomPaint.setPathEffect(effects);
                break;
            default:
        }//end switch
    }

    //设置底部圆是否显示
    protected void setBottomCircleShow(boolean isShow) {
        isBottomCircleShow = isShow;
        if (isBottomCircleShow) {//修改绘制Paint参数
            bottomPaint.setColor(paint.getColor());
            bottomPaint.setAlpha(0x33);
        }//end if
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
        canvas.rotate(90 + startRotateAngle, centerX, centerY);//坐标系旋转

        int outerCircleRadius = (Math.min(w, h) >> 1) - PAD;//外圆半径
        if (mOuterCircleIsShow) {//外装饰圆显示
            //canvas.drawCircle(centerX, centerY, outerCircleRadius, extraCirclePaint);
            tempRect.set(centerX - outerCircleRadius, centerY - outerCircleRadius,
                    centerX + outerCircleRadius, centerY + outerCircleRadius);
            canvas.drawArc(tempRect, 0, angleRotateSpan, false, extraCirclePaint);
        }

        //draw Main
        int radius = outerCircleRadius - mOuterCirclePad - (stokenWidth >> 1);
        int left = (w >> 1) - radius;
        int top = (h >> 1) - radius;
        ovalRect.set(left, top,
                left + radius + radius, top + radius + radius);
        //绘制底部圆
        if (isBottomCircleShow) {//底部圆显示
            canvas.drawArc(ovalRect, 0, angleRotateSpan, false, bottomPaint);
        }
        //主圆绘制
        canvas.drawArc(ovalRect, 0, angle, false, paint);

        //内圆绘制
        if (mInnerCircleIsShow) {//内圆显示
            int innerRadius = radius - (stokenWidth >> 1) - mInnerCirclePad - EXTRA_CIRCLE_WIDTH;
            //canvas.drawCircle(centerX, centerY, innerRadius, extraCirclePaint);
            tempRect.set(centerX - innerRadius, centerY - innerRadius,
                    centerX + innerRadius, centerY + innerRadius);
            canvas.drawArc(tempRect, 0, angleRotateSpan, false, extraCirclePaint);
        }

        //绘制指示器
        if (indicatorBitmap != null) {
            int bitWidth = indicatorBitmap.getWidth();
            int bitHeight = indicatorBitmap.getHeight();

            //角度制转弧度制
            double radAngle = Math.toRadians(angle);
            //圆的参数方程确定坐标点 极坐标
            int x = (int) (radius * Math.cos(radAngle)) + centerX - (int) (bitDistRect.width() / 2);
            int y = (int) (radius * Math.sin(radAngle)) + centerY - (int) (bitDistRect.height() / 2);
            bitDistRect.set(x, y, x + bitDistRect.width(), y + bitDistRect.height());
            canvas.drawBitmap(indicatorBitmap, bitSrcRect, bitDistRect, null);
        }//end if

        canvas.restore();
    }


    public void setStrokenWidth(int size) {
        if (size <= 0)
            throw new IllegalStateException("size can not be zero or -1");
        stokenWidth = size;
        paint.setStrokeWidth(stokenWidth);//圆圈宽度设置
        bottomPaint.setStrokeWidth(stokenWidth);//底部圆圈宽度 应与主圆保持一致
    }

    public int getStartRotateAngle() {
        return startRotateAngle;
    }

    public void setStartRotateAngle(int startRotateAngle) {
        this.startRotateAngle = startRotateAngle;
    }

    public int getAngleRotateSpan() {
        return angleRotateSpan;
    }

    public void setAngleRotateSpan(int angleRotateSpan) {
        this.angleRotateSpan = angleRotateSpan;
    }

    /**
     * 设置数值
     *
     * @param value
     */
    public void setValue(int value) {
        angle = Math.min(value, angleRotateSpan);
        invalidate();//重新设置数值
    }
}//end class
