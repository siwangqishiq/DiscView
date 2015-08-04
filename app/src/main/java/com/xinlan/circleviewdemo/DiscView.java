package com.xinlan.circleviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
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
    public static final int ANIMATION_DURING = 1000;

    private CircleView mCircleView;
    private ValueAnimator animator;
    private TextView headText;//头部Text
    private TextView contentValueText;//属性Text
    private TextView bottomText;//底部Text

    public DiscView(Context context) {
        super(context);
        initView(context);
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        animator = ValueAnimator.ofInt(0, 0);//属性动画差值器

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.view_disc_layout, this, true);
        mCircleView = (CircleView) v.findViewById(R.id.circle_view);

        headText = (TextView) v.findViewById(R.id.head_title);
        contentValueText = (TextView) v.findViewById(R.id.value_content);
        bottomText = (TextView) v.findViewById(R.id.bottom_title);
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
