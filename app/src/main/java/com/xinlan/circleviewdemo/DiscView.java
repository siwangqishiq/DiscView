package com.xinlan.circleviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by panyi on 2015/8/4.
 */
public class DiscView extends FrameLayout {
    public static final int ANIMATION_DURING = 1300;

    private Context mContext;
    private CircleView mCircleView;
    private ValueAnimator animator;
    private TextView headText;//头部Text
    private TextView contentValueText;//属性Text
    private TextView bottomText;//底部Text

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
        a.recycle();

        mCircleView.setStrokenWidth(mCircleView.stokenWidth);
        mCircleView.setCircleMode(mCircleView.mCircleMode);
        mCircleView.setOuterCircle(mCircleView.mOuterCircleIsShow, mCircleView.mOuterCirclePad);
        mCircleView.setRadiusColor(mCircleView.mRadiusColor);
    }

    public void setValue(int value) {
        if (animator.isRunning()) {//上次动画未结束
            animator.cancel();//取消上次动画
        }

        animator = ValueAnimator.ofInt(0, value);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());//先加速 后减速差值
        animator.setDuration(ANIMATION_DURING);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCircleView.setValue(value);
                contentValueText.setText("" + value);
            }
        });

//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                CircleView.this.animate().translationXBy(100).start();
//            }
//        });
    }
}//end class
