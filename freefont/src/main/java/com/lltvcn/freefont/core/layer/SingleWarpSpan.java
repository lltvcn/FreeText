package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * Created by zhaolei on 2017/10/21.
 */

public class SingleWarpSpan extends ReplacementSpan{

    private ReplacementSpan span;

    public SingleWarpSpan(ReplacementSpan span){
        this.span = span;
    }



    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return span.getSize(paint,text,start,end,fm);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        span.draw(canvas,text,start,end,x,top,y,bottom,paint);
    }
}
