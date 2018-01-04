package com.lltvcn.freefont.core.animation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhaolei on 2017/12/4.
 */

public abstract class BaseAnimation extends ValueAnimator implements ICanvasTransform,ValueAnimator.AnimatorUpdateListener{
    protected TextView tv;


    public BaseAnimation(TextView tv){
        this.tv = tv;
        setFloatValues(0,1);
        addUpdateListener(this);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        tv.postInvalidate();
    }
}
