package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/10/19.
 */

public class MultiLayer extends BaseLayer{
    protected ArrayList<ILayer> layers;
    private static Paint multiPaint = new Paint();

    public MultiLayer(ArrayList<ILayer> layers){
        this.layers = layers;
    }


    public MultiLayer(){
    }


    @Override
    protected void onRectChange(RectF rect, float absSize) {
        if(layers!=null){
            for (ILayer layer:layers) {
                layer.setRectF(rect,absSize);
            }
        }
    }

    public final void add(ILayer layer) {
        if(layers == null){
            layers = new ArrayList<>(1);
        }
        layers.add(layer);
    }

    public final void remove(ILayer layer) {
        layers.remove(layer);
    }

    @Override
    protected void drawLayer(int index,Canvas c, DrawParam param, Paint paint) {
        multiPaint.reset();
        multiPaint.set(paint);
        if(layers!=null){
            for (ILayer layer:layers) {
                layer.draw(index,c,param,multiPaint);
            }
        }
    }
}
