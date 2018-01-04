package com.lltvcn.freefont.core.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/12/7.
 */

public class TAnimationSet extends BaseAnimation{
    private ArrayList<TAnimation> animations = new ArrayList<>();


    public TAnimationSet(TextView tv) {
        super(tv);
    }

    public void addTAnimation(TAnimation animation){
        animations.add(animation);
    }


    @Override
    public void transformCanvas(int index, RectF rect, Canvas canvas, Paint paint) {
        for (TAnimation ani: animations) {
            ani.setCurrentPlayTime((long)((float)getAnimatedValue()*getDuration()));
            ani.transformCanvas(index,rect,canvas,paint);
        }
    }
}
