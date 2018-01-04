package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lltvcn.freefont.core.animation.ICanvasTransform;

/**
 * Created by zhaolei on 2017/9/21.
 */

public interface ILayer {

    void draw(int index,Canvas canvas, DrawParam param,Paint paint);

    void setRectF(RectF rect,float absSize);

    void offset(float x,float y);

    void rotate(float degree);

    void scale(float scale);

    void setPaintHandler(IPaintHandler shader);

    void setDrawDispatcher(IDrawDispatcher dispatcher);

    void setCanvasTransform(ICanvasTransform transform);



    /**
     * layer 可以叠加,offset(x,y)，
     * txtlayer：mask(blur,emo---),textsize
     * imglayer:调整
     * 变换:分割，平移分割，波纹......
     * 以上中paint 通用的可以设置的属性：shader,mask,shadow,*/



    interface IPaintHandler {
        void handlePaint(int index,Paint paint,RectF rectF);
    }

    interface IDrawDispatcher {
        void draw(int index,ILayer layer,Canvas canvas, DrawParam param,Paint paint);
    }

    interface DrawParam{

    }

}
