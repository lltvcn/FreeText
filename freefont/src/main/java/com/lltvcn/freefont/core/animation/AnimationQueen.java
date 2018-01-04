package com.lltvcn.freefont.core.animation;

import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/12/6.
 */

public class AnimationQueen extends Animation{
    private ArrayList<Animation> animations = new ArrayList<>();
    private TextView tv;
    private Animation currentAnimation;
    private int currentIndex;
    private int repeatCount;

    public AnimationQueen (TextView tv){
        this.tv = tv;
    }


    public AnimationQueen addAnimation(Animation animation){
        animations.add(animation);
        animation.setAnimationListener(listener);
        return this;
    }


    @Override
    public void start() {
        if(currentAnimation!=null){
            cancel();
            tv.post(new Runnable() {
                @Override
                public void run() {
                    start();
                }
            });
            return;
        }
        currentAnimation = animations.get(0);
        currentIndex = 0;
        repeatCount = 0;
        if(currentAnimation!=null){
            tv.setAnimation(currentAnimation);
            currentAnimation.start();
        }
    }


    @Override
    public void cancel() {
        if(currentAnimation!=null&&currentAnimation.hasStarted()){
            Animation animation = currentAnimation;
            currentAnimation = null;
            currentIndex = 0;
            animation.cancel();
        }
        currentAnimation = null;
        currentIndex = 0;
        repeatCount = 0;
    }

    @Override
    public long getDuration() {
        long duration = 0;
        for (Animation animation :
                animations) {
            if(animation.getRepeatCount()!=INFINITE){
                duration += animation.getRepeatCount()*animation.getDuration();
            }else {
                return INFINITE;
            }
        }
        return duration;
    }

    private AnimationListener listener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(currentAnimation!=null){
                boolean start = false;
                if(currentIndex == animations.size()-1){
                    repeatCount++;
                    if(getRepeatCount() == INFINITE||repeatCount<=getRepeatCount()){
                        currentIndex = 0;
                        start = true;
                    }else{
                        start = false;
                        repeatCount = 0;
                        currentIndex = 0;
                        currentAnimation = null;
                    }
                }else if(currentIndex<animations.size()){
                    start = true;
                    currentIndex++;
                }
                if(start){
                    currentAnimation = animations.get(currentIndex);
                    tv.setAnimation(currentAnimation);
                    currentAnimation.start();
                }
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

}
