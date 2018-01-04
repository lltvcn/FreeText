package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.util.Log;


import com.lltvcn.freefont.core.animation.ICanvasTransform;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/9/21.
 */

public class LayerSpan extends ReplacementSpan{

    private float rH,rW;
    private ArrayList<ILayer> layers;
    private Matrix matrix = new Matrix();
    private Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
    private Paint.FontMetricsInt paintMetrics = new Paint.FontMetricsInt();
    private ICanvasTransform canvasTransform;
    private static Paint staticPaint = new Paint();

    public LayerSpan(float relativeHeight, float relativeWidth){
        rH = relativeHeight;
        rW = relativeWidth;
        layers = new ArrayList<>();
    }


    public void addLayer(ILayer layer){
        layers.add(layer);
    }

    public void setCanvasTransform(ICanvasTransform transform){
        canvasTransform = transform;
    }



    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            Log.i("txt-getSize-start",fm.toString());
            Paint.FontMetricsInt fontMetricsInt =  paint.getFontMetricsInt();
            fm.ascent = fontMetricsInt.ascent;
            fm.descent = fontMetricsInt.descent;
            fm.top = fontMetricsInt.top;
            fm.bottom = fontMetricsInt.bottom;
            if(rH!=1&&rH!=0){
//                int moreH = ((int) (paint.getTextSize()*rH)-(fm.bottom-fm.top))/2;
                fm.top = (int) (fm.top*rH);
                fm.ascent = (int) (fm.ascent*rH);
                fm.bottom = (int) (fm.bottom*rH);
                fm.descent = (int) (fm.descent*rH);
            }
            fontMetrics.ascent = fm.ascent;
            fontMetrics.descent = fm.descent;
            fontMetrics.top = fm.top;
            fontMetrics.bottom = fm.bottom;
            Log.i("txt-getSize-end",fm.toString()+":::size:"+paint.getTextSize());
        }
        int width = measureWidth(paint,text,start,end);
        Log.i("BaseSpanNew","getSize:"+width);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//        float scale = S.S /paint.getTextSize();
        Log.i("BaseSpanNew","draw");
        Log.i("txt-draw-top",""+top+"bottom"+bottom);
        Log.i("txt-draw",fontMetrics.toString());
        float txtSize = paint.getTextSize();

        RectF rect = new RectF();
        int width = measureWidth(paint,text,start,end);
        int height = bottom-top;

        rect.set(x,y+fontMetrics.ascent,width+x,y+fontMetrics.descent);

//        rect.set(x,top,width+x,bottom);
//        if(scale!=1){
//            canvas.save();
//            paint.setTextSize(S.S);
////            getSize(paint,text,start,end,paint.getFontMetricsInt());
//            float tempY = y-rect.centerY();
//            canvas.scale(1f/scale,1f/scale,rect.centerX(),rect.centerY());
//            matrix.setScale(scale,scale,rect.centerX(),rect.centerY());
//            matrix.mapRect(rect);
////            canvas.scale(scale,scale,rect.centerX(),rect.centerY());
//
////            canvas.setMatrix(matrix);
////            canvas.concat(matrix);
////            canvas.scale(scale,scale,rect.centerX(),rect.centerY());
//            Shader shader = paint.getShader();
//            if(shader!=null){
//                shader.setLocalMatrix(matrix);
//            }
//            width = (int) (scale*width);
//            height = (int) (scale*height);
//            Paint.FontMetrics metrics = paint.getFontMetrics();
//            y = (int) (rect.centerY()-(metrics.descent+metrics.ascent)/2);
//            matrix.reset();
//        }
        paint.getFontMetricsInt(paintMetrics);
        DrawParamI param = new DrawParamI();
        param.txtParam.text = text;
        if(rW>0){
            param.txtParam.x = (width-paint.measureText(text,start,end))/2+rect.left;
        }else{
            param.txtParam.x = rect.left;
        }

        param.txtParam.centerY = rect.centerY();
        param.txtParam.y = rect.centerY()-(paintMetrics.descent+paintMetrics.ascent)/2f;
//        param.txtParam.y = y;



//        if(rH>0&&rH!=1){
//            param.txtParam.y = (height-paint.getTextSize())/2+y;
//        }else{
//            param.txtParam.y = y;
//        }
        param.txtParam.start = start;
        param.txtParam.end = end;
        if(canvasTransform!=null){
            staticPaint.set(paint);
            canvas.save();
            canvasTransform.transformCanvas(start,rect,canvas,staticPaint);
            for (ILayer layer:layers) {
                layer.setRectF(rect,staticPaint.getTextSize());
                layer.draw(start,canvas,param,staticPaint);
            }
            canvas.restore();
        }else{
            for (ILayer layer:layers) {
                layer.setRectF(rect,paint.getTextSize());
                layer.draw(start,canvas,param,paint);
            }
        }
//        if(scale!=1){
//            canvas.restore();
//            paint.setTextSize(txtSize);
//        }
    }

    private int measureWidth(Paint paint,CharSequence text, int start, int end){
//        float scale = paint.getTextScaleX();
//        if(rW!=1){
//            paint.setTextScaleX(rW);
//        }
//        int width = (int) paint.measureText(text,start,end);
//        paint.setTextScaleX(scale);
//        return width;
        return rW>0? (int) (rW * paint.getTextSize() * (end - start)): (int) paint.measureText(text, start, end);
    }

    private class DrawParamI implements TxtLayer.ITxtDrawParam{
        public TxtLayer.TxtParam txtParam = new TxtLayer.TxtParam();

        @Override
        public TxtLayer.TxtParam getTxtParam() {
            return txtParam;
        }


    }
}
