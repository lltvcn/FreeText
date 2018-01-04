package com.lltvcn.freefont.core.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/10/23.
 */

public class OffsetDrawer extends DrawDiapatcher{
    private float[] positions,offsets;

    public OffsetDrawer(float[] positions,float[] offsets){
        this.positions = positions;
        this.offsets = offsets;
    }

    @Override
    protected void drawToCanvas(Bitmap bm, Canvas c, Paint p, RectF aim, Rect from, RectF to) {
        if(positions!=null){
            int offsetY = 0;
            for (int i = 0; i < positions.length; i++) {
                from.set((int) aim.left,(int)aim.top+offsetY,(int)aim.right,(int)aim.top+(offsetY+=aim.height()*positions[i]));
                if(offsetY>aim.bottom){
                    return;
                }
                to.set(from);
                to.offset(offsets[i]*p.getTextSize(),0);
                c.drawBitmap(bm,from,to,null);
            }
        }
    }
}
