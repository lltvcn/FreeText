package com.lltvcn.freefont.core.animation;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/12/6.
 */

public class BaseAnimation2IA implements TA<BaseAnimation>{
    private BaseAnimation animator;

    public BaseAnimation2IA(BaseAnimation animator){
        this.animator = animator;
    }

    @Override
    public void start() {
        animator.start();
    }

    @Override
    public void stop() {
        animator.end();
    }

    @Override
    public long getDuration() {
        return animator.getDuration();
    }

    @Override
    public BaseAnimation getValue() {
        return animator;
    }


}
