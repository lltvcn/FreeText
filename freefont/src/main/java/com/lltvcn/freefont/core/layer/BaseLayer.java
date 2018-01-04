package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lltvcn.freefont.core.animation.ICanvasTransform;


/**
 * Created by zhaolei on 2017/9/26.
 */

public abstract class BaseLayer implements ILayer{

    protected IPaintHandler paintHandler;
    protected IDrawDispatcher drawDispatcher;
    protected ICanvasTransform canvasTransform;
    private static Paint paint = new Paint();
    protected float offsetX,offsetY,degree,scale;
    private float absSize;
    protected RectF rect;

    @Override
    public void setRectF(RectF rect,float absSize) {
        this.absSize = absSize;
        if(this.rect==null||this.rect!=rect){
            this.rect = rect;
            onRectChange(rect,absSize);
        }
    }

    protected void onRectChange(RectF rect,float absSize){

    }

    @Override
    public void offset(float x, float y) {
        offsetX = x;
        offsetY = y;
    }

    @Override
    public void rotate(float degree) {
        this.degree = degree;
    }

    @Override
    public void scale(float scale) {
        this.scale = scale;
    }


    public final void setCanvasTransform(ICanvasTransform canvasTransform) {
        this.canvasTransform = canvasTransform;
    }

    public final void setPaintHandler(IPaintHandler shader) {
        paintHandler = shader;
    }

    @Override
    public final void draw(int index,Canvas canvas, DrawParam param, Paint paint) {
        if(offsetX!=0||offsetY!=0||degree!=0||(scale!=0&&scale!=1)){
            canvas.save();
            canvas.translate(offsetX*absSize,offsetY*absSize);
            if(degree!=0){
                canvas.rotate(degree,rect.centerX(),rect.centerY());
            }
            if(scale!=0&&scale!=1){
                canvas.scale(scale,scale,rect.centerX(),rect.centerY());
            }
            this.paint.reset();
            this.paint.set(paint);
            if(paintHandler !=null){
                paintHandler.handlePaint(index,this.paint,rect);
            }
            if(canvasTransform !=null){
                canvas.save();
                canvasTransform.transformCanvas(index,rect,canvas,this.paint);
            }
            if(drawDispatcher !=null){
                drawDispatcher.draw(index,this,canvas,param,this.paint);
            }else{
                drawLayer(index,canvas,param,this.paint);
            }
            if(canvasTransform !=null){
                canvas.restore();
            }
            canvas.restore();
        }else{
            this.paint.reset();
            this.paint.set(paint);
            if(paintHandler !=null){
                paintHandler.handlePaint(index,this.paint,rect);
            }
            if(canvasTransform !=null){
                canvas.save();
                canvasTransform.transformCanvas(index,rect,canvas,this.paint);
            }
            if(drawDispatcher !=null){
                drawDispatcher.draw(index,this,canvas,param,this.paint);
            }else{
                drawLayer(index,canvas,param,this.paint);
            }
            if(canvasTransform !=null){
                canvas.restore();
            }
        }
    }

    public void setDrawDispatcher(IDrawDispatcher dispatcher) {
        drawDispatcher = dispatcher;
    }

    protected void drawLayer(int index,Canvas c, DrawParam param, Paint paint){

    }

}
