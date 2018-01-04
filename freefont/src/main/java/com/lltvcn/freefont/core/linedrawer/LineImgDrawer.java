package com.lltvcn.freefont.core.linedrawer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LineHeightSpan;
import android.util.Log;


/**
 * Created by zhaolei on 2017/9/20.
 */

public class LineImgDrawer implements LineHeightSpan.WithDensity,LineDrawer{

    private Drawable drawable;
    private int aimHeight;
    private int aimOffset;
    private Paint.FontMetricsInt fontMetricsInt = new Paint.FontMetricsInt();
    private Gravity gravity;
    private float relativeDrawableHeight;
    private int startPos = -1;
    private int endPos;
    private float offset = -0.1f;

    public LineImgDrawer(Drawable drawable, float relativeHeight, Gravity gravity){
        this.drawable = drawable;
        relativeDrawableHeight = relativeHeight;
        this.gravity = gravity;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm, TextPaint paint) {
        if(startPos==-1){
            startPos = ((Spanned)text).getSpanStart(this);
            endPos = ((Spanned)text).getSpanEnd(this);
        }
        Log.i("line","start:"+startPos+"end:"+endPos+"ssss"+start+"eeee"+end);
        if(endPos<start||startPos>=end){
            return;
        }
        updateFM(fm, (int) paint.getTextSize());
    }


    private void updateFM(Paint.FontMetricsInt fm,int txtHeight){
        Log.i("line----start",fm.toString());
        aimHeight = (int) (relativeDrawableHeight*txtHeight);
        aimOffset = (int) (relativeDrawableHeight*txtHeight);
        switch (gravity){
            case CENTER:
                if(aimHeight > txtHeight){
                    int plus = (aimHeight-txtHeight)/2;
                    fm.top -= plus;
                    fm.ascent -= plus;
                    fm.bottom += plus;
                    fm.descent += plus;
                }
                break;
            case OUT_TOP:
                fm.top -= aimHeight;
                fm.ascent -= aimHeight;
                break;
            case OUT_BOTTOM:
                fm.bottom += aimHeight;
                fm.descent += aimHeight;
                break;
            case INNER_TOP:
                if (aimHeight>txtHeight) {
                    int plus = (aimHeight-txtHeight);
                    fm.bottom += plus;
                    fm.descent += plus;
                }
                break;
            case INNER_BOTTOM:
                if (aimHeight > txtHeight) {
                    int plus = aimHeight-txtHeight;
                    fm.top -= plus;
                    fm.ascent -= plus;
                }
                break;
        }
        fontMetricsInt.top = fm.top;
        fontMetricsInt.ascent = fm.ascent;
        fontMetricsInt.bottom = fm.bottom;
        fontMetricsInt.descent = fm.descent;
        Log.i("line---end",fm.toString());
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {

    }

    @Override
    public void draw(Canvas c, Paint p, float left, int top, float right, int bottom , int baseLine) {
        Log.i("line-draw","top:"+top+"bottom:"+bottom);
        top = baseLine+fontMetricsInt.ascent;
        bottom = baseLine+fontMetricsInt.descent;
        switch (gravity){
            case CENTER:
                int moreH = bottom-top-aimHeight;
                top+=moreH/2;
                bottom-=moreH/2;
                break;
            case INNER_TOP:
            case OUT_TOP:
                bottom = top+aimHeight;
                break;
            case INNER_BOTTOM:
            case OUT_BOTTOM:
                top = bottom-aimHeight;
                break;
        }

        if(drawable instanceof BitmapDrawable){
            float dstHeight = bottom-top;
            float scale = dstHeight/drawable.getIntrinsicHeight();
            int width = (int) (drawable.getIntrinsicWidth()*scale);

            int leftIndex = (int) left;
            while (leftIndex<right){
                if(leftIndex+width>right){
                    c.save();
                    c.clipRect(leftIndex,top,right,bottom);
                    drawable.setBounds(leftIndex,top,leftIndex+width,bottom);
                    drawable.draw(c);
                    c.restore();
                }else {
                    drawable.setBounds(leftIndex,top,leftIndex+width,bottom);
                    drawable.draw(c);
                }
                leftIndex+=width;
            }
        }else{
            drawable.setBounds((int)left,top,(int)right,bottom);
            drawable.draw(c);
        }
    }

}
