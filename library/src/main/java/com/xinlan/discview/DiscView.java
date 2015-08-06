package com.xinlan.discview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by panyi on 2015/8/4.
 */
public class DiscView extends FrameLayout {
    public static final int ANIMATION_DURING = 1100;

    private Context mContext;
    private CircleView mCircleView;
    private ValueAnimator animator;
    private TextView headText;//头部Text
    private TextView contentValueText;//属性Text
    private TextView bottomText;//底部Text

    private Animation afterAnimation;//设值完毕后动画

    public DiscView(Context context) {
        super(context);
        initView(context, null);
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        animator = ValueAnimator.ofInt(0, 0);//属性动画差值器

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.view_disc_layout, this, true);
        mCircleView = (CircleView) v.findViewById(R.id.circle_view);
        headText = (TextView) v.findViewById(R.id.head_title);
        contentValueText = (TextView) v.findViewById(R.id.value_content);
        bottomText = (TextView) v.findViewById(R.id.bottom_title);

        readAttributeAndSet(context, attrs);

        afterAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scale_animation);
    }

    //配置属性读取并配置
    private void readAttributeAndSet(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiscView);
        mCircleView.stokenWidth = a.getDimensionPixelSize(R.styleable.DiscView_dvStrokenWidth,
                mCircleView.stokenWidth);//宽度属性读取
        mCircleView.mCircleMode = a.getInt(R.styleable.DiscView_dvCircleMode, mCircleView.mCircleMode);//读取圆环模式

        //读取外装饰圆圈是否显示
        mCircleView.mOuterCircleIsShow = a.getBoolean(R.styleable.DiscView_dvOuterCircleShow, mCircleView.mOuterCircleIsShow);
        mCircleView.mOuterCirclePad = a.getDimensionPixelSize(R.styleable.DiscView_dvOuterCirclePad, mCircleView.mOuterCirclePad);//外圆边距

        //读取圆圈颜色设置
        mCircleView.mRadiusColor = a.getColor(R.styleable.DiscView_dvStrokenColor, mCircleView.mRadiusColor);

        //读取外装饰圆圈是否显示
        mCircleView.mInnerCircleIsShow = a.getBoolean(R.styleable.DiscView_dvInnerCircleShow, mCircleView.mInnerCircleIsShow);
        mCircleView.mInnerCirclePad = a.getDimensionPixelSize(R.styleable.DiscView_dvInnerCirclePad, mCircleView.mInnerCirclePad);//外圆边距
        mCircleView.isBottomCircleShow = a.getBoolean(R.styleable.DiscView_dvBottomCircleIsShow, false);
        Drawable bitDrawable = a.getDrawable(R.styleable.DiscView_dvIndicatorDraw);
        Bitmap indicatorBit = null;
        if (bitDrawable != null) {
            indicatorBit = DiscViewUtils.drawableToBitmap(bitDrawable);
        }

        //读取开始角度偏移量
        mCircleView.startRotateAngle = a.getInteger(R.styleable.DiscView_dvStartRotateAngle, CircleView.MIN_VALUE);
        //读取圆盘可旋转范围
        mCircleView.angleRotateSpan = a.getInteger(R.styleable.DiscView_dvAngleRotateSpan, CircleView.MAX_VALUE);
        a.recycle();

        mCircleView.setStrokenWidth(mCircleView.stokenWidth);
        mCircleView.setCircleMode(mCircleView.mCircleMode);
        mCircleView.setOuterCircle(mCircleView.mOuterCircleIsShow, mCircleView.mOuterCirclePad);
        mCircleView.setRadiusColor(mCircleView.mRadiusColor);
        mCircleView.setInnerCircle(mCircleView.mInnerCircleIsShow, mCircleView.mInnerCirclePad);
        mCircleView.setBottomCircleShow(mCircleView.isBottomCircleShow);
        mCircleView.setIndicatorBit(indicatorBit);

        mCircleView.setStartRotateAngle(mCircleView.startRotateAngle);
        mCircleView.setAngleRotateSpan(mCircleView.angleRotateSpan);
    }


    public void setValue(int value) {
        setValue(value, ANIMATION_DURING);
    }

    /**
     * 设置指示器的值 min - max之间
     *
     * @param value
     */
    public void setValue(int value, int duration) {
        if (animator.isRunning()) {//上次动画未结束
            animator.cancel();//取消上次动画
            mCircleView.clearAnimation();
        }

        animator = ValueAnimator.ofInt(0, value);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());//先加速 后减速差值
        animator.setDuration(duration);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCircleView.setValue(value);
                contentValueText.setText("" + value);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCircleView.startAnimation(afterAnimation);
            }

            public void onAnimationCancel(Animator animation) {
            }
        });
    }
}//end class
