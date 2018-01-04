package com.lltvcn.freefont.core.animation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by zhaolei on 2017/12/4.
 */

public class TAnimation extends BaseAnimation {


    private long itemDuration = 400;
    private float fromX,toX,fromY,toY,fromAlpha,toAlpha,fromScaleX,toScaleX,fromScaleY,toScaleY,fromDegree,toDegree;
    private boolean hasAlpha,hasTranslate,hasRotate,hasScale;
    private ValueComputer valueComputer;
    private Matrix matrix = new Matrix();


    public TAnimation(TextView tv) {
        super(tv);
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
    }

    @Override
    public void reverse() {
        checkDuration();
        super.reverse();
    }

    public void setItemDuration(long duration){
        itemDuration = duration;
    }


    private void checkDuration(){
        long duration = valueComputer.getDuration(tv.getText().length(),this);
        if(duration != super.getDuration()){
            super.setDuration(duration);
        }
    }

    public static class Builder{
        TAnimation a;

        public Builder(TextView tv){
            a = new TAnimation(tv);
        }

        public Builder itemDuration(long duration){
            a.itemDuration = duration;
            return this;
        }

        public Builder alpha(float from,float to){
            a.hasAlpha = true;
            a.fromAlpha = from;
            a.toAlpha = to;
            return this;
        }

        public Builder translate(float fromX,float toX,float fromY,float toY){
            a.hasTranslate = true;
            a.fromX = fromX;
            a.toX = toX;
            a.fromY = fromY;
            a.toY = toY;
            return this;
        }


        public Builder scale(float fromX,float toX,float fromY,float toY){
            a.fromScaleX = fromX;
            a.toScaleX = toX;
            a.fromScaleY = fromY;
            a.toScaleY = toY;
            a.hasScale = true;
            return this;
        }

        public Builder rotate(float from,float to){
            a.hasRotate = true;
            a.fromDegree = from;
            a.toDegree = to;
            return this;
        }

        public Builder valueComputer(ValueComputer computer){
            a.valueComputer = computer;
            return this;
        }

        public TAnimation create(){
            return a;
        }

    }


    public interface ValueComputer{
        float getVlaue(int index,TAnimation animation);

        long getDuration(int count,TAnimation animation);
    }



    @Override
    public void transformCanvas(int index, RectF rect, Canvas canvas, Paint paint) {
        if(valueComputer!=null){
            float value = valueComputer.getVlaue(index,this);
            if(valueComputer instanceof SquenceComputer&&(value==0||value == 1)){
                if((int) (255*(fromAlpha+value*(toAlpha-fromAlpha))) == 255){
                    Log.i("jisisos","value="+value+"index------"+index+"-----"+getCurrentPlayTime()+"::::");
                }
            }
            if(hasAlpha){
                paint.setAlpha((int) (255*(fromAlpha+value*(toAlpha-fromAlpha))));
            }
            matrix.reset();
            boolean needTransform = false;
            if(hasRotate){
                needTransform = true;
                matrix.setRotate((fromDegree+value*(toDegree-fromDegree)),rect.centerX(),rect.centerY());
            }
            if(hasScale){
                needTransform = true;
                matrix.postScale((fromScaleX+value*(toScaleX-fromScaleX)),(fromScaleY+value*(toScaleY-fromScaleY)),rect.centerX(),rect.centerY());
            }
            if(hasTranslate){
                needTransform = true;
                matrix.postTranslate(rect.width()*(fromX+value*(toX-fromX)),rect.height()*(fromY+value*(toY-fromY)));
            }
            if(needTransform){
                canvas.concat(matrix);
            }
        }
    }

    public static ValueComputer SAME = new ValueComputer() {
        @Override
        public float getVlaue(int index, TAnimation animation) {
            return (float) animation.getAnimatedValue();
        }

        @Override
        public long getDuration(int count, TAnimation animation) {
            return animation.itemDuration;
        }
    };

    public static ValueComputer NEG = new ValueComputer() {
        @Override
        public float getVlaue(int index, TAnimation animation) {
            if(index%2==0){
                return (float)animation.getAnimatedValue();
            }else{
                return -(float)animation.getAnimatedValue();
            }
        }

        @Override
        public long getDuration(int count,TAnimation animation) {
            return animation.itemDuration;
        }
    };

    public static ValueComputer REVERSE = new ValueComputer() {
        @Override
        public float getVlaue(int index, TAnimation animation) {
            if(index%2==0){
                return (float)animation.getAnimatedValue();
            }else{
                return 1-(float)animation.getAnimatedValue();
            }
        }

        @Override
        public long getDuration(int count,TAnimation animation) {
            return animation.itemDuration;
        }
    };

    public static class SquenceComputer implements ValueComputer{
        private long offsetDuration;

        public SquenceComputer(long offset){
            offsetDuration = offset;
        }

        @Override
        public float getVlaue(int index, TAnimation animation) {
            float duration = animation.getCurrentPlayTime()-(offsetDuration*index);
            if(animation.isRunning()){
                if (duration>0&&duration<animation.itemDuration){
                    return duration/animation.itemDuration;
                }else if(duration <=0){
                    return 0;
                }else{
                    return 1;
                }
            }else{
                return 1;
            }
        }

        @Override
        public long getDuration(int count,TAnimation animation) {
            return offsetDuration*(count-1)+animation.itemDuration;
        }
    }






}
