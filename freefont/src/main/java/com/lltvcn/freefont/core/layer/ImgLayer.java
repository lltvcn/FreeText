package com.lltvcn.freefont.core.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.data.IndexParam;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.util.CU;

/**
 * Created by zhaolei on 2017/9/27.
 */

public class ImgLayer extends BaseLayer{

    private Rect from = new Rect();
    private RectF to = new RectF();
    private float scaleX,scaleY,curScale;
    private DrawableLoader loader;


    public ImgLayer(DrawableLoader loader){
        this.loader = loader;
//        this.colors = data.colors;
//        this.imgs = data.imgs;
//        bitmapSourceLoader = loader;
    }

    @Override
    protected void drawLayer(int index,Canvas c, DrawParam param, Paint paint) {
        if(loader!=null){
            Drawable drawable = loader.getDrawable(index);
            float scaleX = rect.width()/drawable.getIntrinsicWidth();
            float scaleY = rect.height()/drawable.getIntrinsicHeight();

            if(drawable!=null){
                drawable.setBounds((int) rect.left,(int) rect.top,(int) rect.right,(int) rect.bottom);
                drawable.draw(c);
            }
        }
//            if(bm!=null){
//                from.set(0,0,bm.getWidth(),bm.getHeight());
//                scaleX = rect.width()/from.width();
//                scaleY = rect.height()/from.height();
//                curScale = Math.min(scaleX,scaleY);
//                to.set(0,0,from.width()*curScale,from.height()*curScale);
//                to.offset(rect.centerX()-to.centerX(),rect.centerY()-to.centerY());
//                c.drawBitmap(bm,from,to,paint);
//            }
    }

    public interface DrawableLoader{
        Drawable getDrawable(int index);
    }




    public static class ImgParam{
        public int index;
    }
}
