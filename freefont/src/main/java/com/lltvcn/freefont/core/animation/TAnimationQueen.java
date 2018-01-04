package com.lltvcn.freefont.core.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/12/5.
 */

public class TAnimationQueen extends BaseAnimation{
    private ArrayList<BaseAnimation> animations = new ArrayList<>();
    private Field fRunning;

    public TAnimationQueen(TextView tv) {
        super(tv);
        try {
            fRunning =  ValueAnimator.class.getDeclaredField("mRunning");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void addAnimation(BaseAnimation animation){
        if(!animations.contains(animation)){
            animations.add(animation);
        }
    }

    @Deprecated
    @Override
    public ValueAnimator setDuration(long duration) {
        return this;
    }


    @Override
    public long getDuration() {
        checkDuration();
        return super.getDuration();
    }

    @Override
    public void start() {
        checkDuration();
        super.start();
        fRunning.setAccessible(true);
        for (BaseAnimation animation:animations) {
            try {
                fRunning.setBoolean(animation,true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fRunning.setAccessible(false);
    }

    private void checkDuration(){
        long duration = 0;
        for (BaseAnimation animation:animations) {
            duration += animation.getDuration();
        }
        if(duration!=super.getDuration()){
            super.setDuration(duration);
        }
    }

    @Override
    public void reverse() {
        checkDuration();
        super.reverse();
    }

    @Override
    public void end() {
        super.end();
        for (BaseAnimation animation:animations) {
            fRunning.setAccessible(true);
            try {
                fRunning.setBoolean(animation,false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fRunning.setAccessible(false);
        }
    }


    @Override
    public void transformCanvas(int index, RectF rect, Canvas canvas, Paint paint) {
        long currentTime = (long) ((Float)getAnimatedValue()*getDuration());
        long time = 0;
        long duration;
        boolean hasTrans = false;
        for (BaseAnimation animation:animations) {
            duration = animation.getDuration();
            if(time<=currentTime&&time+duration>currentTime){
                hasTrans = true;
                Log.i("kkais","currentTime:"+currentTime+"duration:"+duration+"::time"+time);
                animation.setCurrentPlayTime(currentTime-time);
                animation.transformCanvas(index,rect,canvas,paint);
                break;
            }
            time+=duration;
        }
        if(!hasTrans){
            Log.i("ddsiis","no");
        }
    }


}
