package drawn.lltvcn.com.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;

/**
 * Created by zhaolei on 2017/9/2.
 */

public class BaseSpan extends ReplacementSpan{

    protected BaseSpan pre,sizeSpan;
    protected int horSpace,singleWidth,singleHeight;
    protected Paint txtPaint;

    public BaseSpan(){

    }

    public final BaseSpan addNext(BaseSpan span){
        if(span!=null){
            span.pre = this;
            return span;
        }else{
            return this;
        }
    }

    public BaseSpan setSizeSpan(BaseSpan span){
        if(span!=this){
            sizeSpan = span;
        }
        return this;
    }

    public void setHorSpace(int space){
        horSpace = space;
    }

    @Override
    public final int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        txtPaint = paint;
        if(sizeSpan!=null){
            return sizeSpan.getSize(paint,text,start,end,fm);
        }
        if (fm != null) {
            Paint.FontMetricsInt fontMetricsInt =  paint.getFontMetricsInt();
            fm.ascent = fontMetricsInt.ascent;
            fm.descent = fontMetricsInt.descent;
            fm.top = fontMetricsInt.top;
            fm.bottom = fontMetricsInt.bottom;
            handleFont(fm);
        }
        int size = (int) (paint.measureText(text,start,end)/(end-start));
        return measureWidth(paint,text,start,end);//需要整部分的长度，才可以正确量度其他字体位置
    }

    public void handleFont(Paint.FontMetricsInt fontMetricsInt){

    }

    private int measureWidth(Paint paint,CharSequence text, int start, int end){
        return (int) paint.measureText(text,start,end)+horSpace*(end-start-1);
    }


    @Override
    public final void draw(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        int width = measureWidth(p,text,start,end);
//        if(pre!=null){
//            pre.beforeDrawText(c,(int)x,top, (int) (x+width),bottom,p);
//        }
        beforeDrawText(c,(int)x,top, (int) (x+width),bottom,p);
//        if(pre!=null){
//            pre.drawText(c,text,start,end,x,top,y,bottom,p);
//        }
        if(horSpace!=0){
            for (int i = start; i < end; i++) {
//            c.drawCircle(x+i*2*offsetX+offsetX,offsetY+top,4,p);
                drawText(c,text,i,i+1,x+measureWidth(p,text,start,i),top,y,bottom,p);
            }
        }else{
            drawText(c,text,start,end,x,top,y,bottom,p);
        }
//        if(pre!=null){
//            pre.afterDrawText(c,(int)x,top, (int) (x+width),bottom,p);
//        }
        afterDrawText(c,(int)x,top, (int) (x+width),bottom,p);
    }

    protected void beforeDrawText(Canvas c, int left, int top, int right, int bottom, Paint p){
        if(pre!=null){
            pre.beforeDrawText(c,left,top,right,bottom,p);
        }
    }

    protected void afterDrawText(Canvas c, int left, int top, int right, int bottom, Paint p){
        if(pre!=null){
            pre.afterDrawText(c,left,top,right,bottom,p);
        }
    }

    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p){
        if(pre!=null){
            pre.drawText(c,text,start,end,x,top,y,bottom,p);
        }else{
            c.drawText(text,start,end,x,y,p);
        }
    }
}
