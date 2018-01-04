package com.lltvcn.freefont.core.animation;

import android.view.animation.Animation;

/**
 * Created by zhaolei on 2017/12/6.
 */

public class Animation2IA implements TA<Animation> {
    private Animation animation;

    public Animation2IA(Animation animation){
        this.animation = animation;
    }

    @Override
    public void start() {
        animation.start();
    }

    @Override
    public void stop() {
        animation.cancel();
    }


    @Override
    public long getDuration() {
        return animation.getDuration();
    }

    @Override
    public Animation getValue() {
        return animation;
    }
}
