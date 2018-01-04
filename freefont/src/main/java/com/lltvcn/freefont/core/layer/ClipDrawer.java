package com.lltvcn.freefont.core.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/10/23.
 */

public class ClipDrawer extends DrawDiapatcher{

    private float size;

    public ClipDrawer(float size){
        this.size = size;
    }

    @Override
    protected void drawToCanvas(Bitmap bm, Canvas c, Paint p,RectF aim, Rect from, RectF to) {
        float span = p.getTextSize()*size;
        c.save();
        from.set((int) aim.left,(int) aim.top,(int) aim.right,(int) (aim.top+aim.height()/2));
//        from.set(0,0, (int) aim.width(),(int) (aim.height()/2));
        to.set(from);
        to.offset(0,-span/2);
        c.drawBitmap(bm,from,to,null);
        from.offset(0, (int) (aim.height()/2));
        to.offset(0,aim.height()/2+span);
        c.drawBitmap(bm,from,to,null);
        c.restore();
    }
}
