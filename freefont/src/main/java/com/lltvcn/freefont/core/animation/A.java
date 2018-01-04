package com.lltvcn.freefont.core.animation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by zhaolei on 2017/12/6.
 */

public class A {

    public static final int SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT = 0;
    public static final int SINGLE_ROTATE = 1;
    public static final int SINGLE_UP_DOWN = 2;
    public static final int ROTATE_REPEAT = 3;

    public static final int SCALE_SHOW = 4;
    public static final int BOTTOM_IN_SCALE_UP_OUT = 5;
    public static final int SINGLE_SCALE = 6;
    public static final int SINGLE_X = 7;



    public static TA createTAByType(int type, TextView tv){
        switch (type){
            case SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT:
                return createSRILO(tv);
            case SINGLE_ROTATE:
                return createSRotate(tv);
            case ROTATE_REPEAT:
                return createRoteteRepeat(tv);
            case BOTTOM_IN_SCALE_UP_OUT:
                return createBISUO(tv);
            case SINGLE_UP_DOWN:
                return createSUD(tv);
            case SCALE_SHOW:
                return createScaleShow(tv);
            case SINGLE_SCALE:
                return createSS(tv);
            case SINGLE_X:
                return createX(tv);
        }
        return null;
    }

    private static TA createX(TextView tv) {
        long duration = 200;
        TAnimationQueen queen = new TAnimationQueen(tv);

        TAnimationSet set = new TAnimationSet(tv);
        TAnimation rotateAni = new TAnimation.Builder(tv)
                .rotate(0,-7)
                .itemDuration(duration)
                .valueComputer(TAnimation.NEG)
                .create();
        TAnimation trans = new TAnimation.Builder(tv)
                .translate(0,0,0,-0.1f)
                .valueComputer(TAnimation.SAME)
                .itemDuration(duration)
                .create();
        set.addTAnimation(rotateAni);
        set.addTAnimation(trans);
        set.setDuration(duration);




        TAnimationSet set2 = new TAnimationSet(tv);
        TAnimation rotateAni2 = new TAnimation.Builder(tv)
                .rotate(-7,7f)
                .itemDuration(duration)
                .valueComputer(TAnimation.REVERSE)
                .create();
        TAnimation trans2 = new TAnimation.Builder(tv)
                .translate(0,0,-0.1f,0f)
                .itemDuration(duration)
                .valueComputer(TAnimation.SAME)
                .create();
        set2.addTAnimation(rotateAni2);
        set2.addTAnimation(trans2);
        set2.setDuration(duration);



        TAnimationSet set3 = new TAnimationSet(tv);
        TAnimation rotateAni3 = new TAnimation.Builder(tv)
                .rotate(7,-7)
                .itemDuration(duration)
                .valueComputer(TAnimation.REVERSE)
                .create();
        TAnimation trans3 = new TAnimation.Builder(tv)
                .translate(0,0,0,-0.1f)
                .itemDuration(duration)
                .valueComputer(TAnimation.SAME)
                .create();
        set3.addTAnimation(rotateAni3);
        set3.addTAnimation(trans3);
        set3.setDuration(duration);


        TAnimationSet set4 = new TAnimationSet(tv);
        TAnimation rotateAni4 = new TAnimation.Builder(tv)
                .rotate(-7,7)
                .itemDuration(duration*2)
                .valueComputer(TAnimation.REVERSE)
                .create();
        TAnimation trans4 = new TAnimation.Builder(tv)
                .translate(0,0,-0.1f,0f)
                .itemDuration(duration)
                .valueComputer(TAnimation.SAME)
                .create();
        set4.addTAnimation(rotateAni4);
        set4.addTAnimation(trans4);
        set4.setDuration(duration);
//
//
        queen.addAnimation(set);
        queen.addAnimation(set2);
        queen.addAnimation(set3);
        queen.addAnimation(set4);
        queen.setRepeatMode(ValueAnimator.RESTART);
        queen.setRepeatCount(ValueAnimator.INFINITE);
//        queen.addAnimation(set3);
//        queen.addAnimation(set4);
        return new BaseAnimation2IA(queen);
    }


    private static TA createScaleShow(TextView tv) {
        ScaleAnimation sa = new ScaleAnimation(4,1f,4,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setStartOffset(500);
        sa.setDuration(600);
        sa.setRepeatCount(Animation.INFINITE);
        sa.setRepeatMode(Animation.RESTART);
        tv.setAnimation(sa);
        return new Animation2IA(sa);
    }


    private static TA createBISUO(TextView tv) {
        AnimationQueen queen = new AnimationQueen(tv);

        long upDuration = 200;
        long scaleDuration = 200;

        AnimationSet upIn = new AnimationSet(false);
        Animation alphaIn = new AlphaAnimation(0,1);
        alphaIn.setDuration(upDuration);
        Animation translateIn = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,0F);
        translateIn.setDuration(upDuration);
        upIn.addAnimation(alphaIn);
        upIn.addAnimation(translateIn);


        AnimationSet scaleRightLarge = new AnimationSet(false);
        Animation scaleIn = new ScaleAnimation(1,1.5f,1,1.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleIn.setDuration(scaleDuration);
        Animation rotateLeftIn = new RotateAnimation(0,10,Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        rotateLeftIn.setDuration(scaleDuration);
        rotateLeftIn.setRepeatCount(1);
        rotateLeftIn.setRepeatMode(Animation.REVERSE);
        scaleIn.setRepeatCount(1);
        scaleIn.setRepeatMode(Animation.REVERSE);
        scaleRightLarge.addAnimation(scaleIn);
        scaleRightLarge.addAnimation(rotateLeftIn);

        AnimationSet scaleLeftLarge = new AnimationSet(false);
        Animation rotateRightIn = new RotateAnimation(0,-10,Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        scaleLeftLarge.setDuration(scaleDuration);
        rotateRightIn.setRepeatCount(1);
        rotateRightIn.setRepeatMode(Animation.REVERSE);
        scaleLeftLarge.addAnimation(scaleIn);
        scaleLeftLarge.addAnimation(rotateRightIn);



        AnimationSet upOut = new AnimationSet(false);
        Animation alphaOut = new AlphaAnimation(1,0);
        alphaOut.setDuration(upDuration);
        Animation translateOut = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1F);
        translateOut.setDuration(upDuration);
        upOut.addAnimation(alphaOut);
        upOut.addAnimation(translateOut);

        queen.addAnimation(upIn);
        queen.addAnimation(scaleRightLarge);
        queen.addAnimation(upOut);
        queen.addAnimation(upIn);
        queen.addAnimation(scaleLeftLarge);
        queen.addAnimation(upOut);

        queen.setRepeatMode(Animation.RESTART);
        queen.setRepeatCount(Animation.INFINITE);



        return new Animation2IA(queen);
    }


    private static TA createRoteteRepeat(TextView tv) {
        RotateAnimation animation = new RotateAnimation(-3,3, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(150);
//        animation.setInterpolator(new DecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        tv.setAnimation(animation);
        return new Animation2IA(animation);
    }

    private static TA createSRotate(TextView tv) {
        TAnimation rotateAni = new TAnimation.Builder(tv)
                .rotate(-10,10)
                .valueComputer(TAnimation.REVERSE)
                .itemDuration(200)
                .create();
        rotateAni.setRepeatMode(ValueAnimator.REVERSE);
        rotateAni.setRepeatCount(ValueAnimator.INFINITE);
        return new BaseAnimation2IA(rotateAni);
    }

    private static TA createSS(TextView tv) {
        TAnimation sa = new TAnimation.Builder(tv)
                .scale(1.1f,0.8f,1.1f,0.8f)
                .itemDuration(80)
                .valueComputer(TAnimation.REVERSE)
                .create();
        sa.setRepeatMode(ValueAnimator.REVERSE);
        sa.setRepeatCount(ValueAnimator.INFINITE);
        return new BaseAnimation2IA(sa);
    }

    private static TA createSUD(TextView tv) {
        TAnimation animationDown = new TAnimation.Builder(tv)
                .translate(0,0,0.05f,-0.05f)
                .valueComputer(TAnimation.REVERSE)
                .itemDuration(200)
                .create();
        animationDown.setRepeatCount(ValueAnimator.INFINITE);
        animationDown.setRepeatMode(ValueAnimator.REVERSE);
        return new BaseAnimation2IA(animationDown);
    }

    private static TA createSRILO(TextView tv) {
        long duration = 400;
        TAnimationQueen queen = new TAnimationQueen(tv);
        TAnimation animation = new TAnimation.Builder(tv)
                .translate(4,0,0,0)
                .itemDuration(duration)
                .alpha(0,1)
                .valueComputer(new TAnimation.SquenceComputer(duration))
//                .valueComputer(TAnimation.REVERSE)
                .create();
        queen.addAnimation(animation);
        TAnimation toAni = new TAnimation.Builder(tv)
                .translate(0,-4,0,0)
                .itemDuration(duration)
                .alpha(1,0)
                .valueComputer(new TAnimation.SquenceComputer(duration))
                .create();
        queen.addAnimation(toAni);
        queen.setInterpolator(null);
        queen.setRepeatMode(ValueAnimator.RESTART);
        queen.setRepeatCount(ValueAnimator.INFINITE);
        return new BaseAnimation2IA(queen);
    }

}
