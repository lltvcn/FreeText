package com.lltvcn.freefont.core.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/10/23.
 */

public abstract class DrawDiapatcher implements ILayer.IDrawDispatcher{

    private static Bitmap bitmap;
    private static Canvas canvas;
    private static Paint paint = new Paint();
    private static RectF toRect = new RectF();
    private static Rect fromRect = new Rect();



    @Override
    public void draw(int index,ILayer layer, Canvas c, ILayer.DrawParam param, Paint p) {
        if(layer instanceof BaseLayer){
            BaseLayer baseLayer = (BaseLayer) layer;
            if(bitmap == null){
                bitmap = Bitmap.createBitmap(1080,1920, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
            }
            paint.setColor(Color.TRANSPARENT);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPaint(paint);
            baseLayer.drawLayer(index,canvas,param,p);
            drawToCanvas(bitmap,c,p,baseLayer.rect,fromRect,toRect);
        }
    }

    protected void setRect(RectF res ,Rect to){
        to.set((int)res.left,(int)res.top,(int)res.right,(int)res.bottom);
    }


    protected abstract void drawToCanvas(Bitmap bm,Canvas c,Paint p,RectF aim,Rect from,RectF to);
}
