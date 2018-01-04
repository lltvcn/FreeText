package com.lltvcn.freefont.core.linedrawer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by zhaolei on 2017/9/20.
 */

public class LineImgForegroundDrawer extends LineImgDrawer implements ForegroundDrawer{


    public LineImgForegroundDrawer(Bitmap bm, float relativeHeight, Gravity gravity) {
        super(new BitmapDrawable(bm), relativeHeight, gravity);
    }

    public LineImgForegroundDrawer(Drawable drawable, float relativeHeight, Gravity gravity) {
        super(drawable, relativeHeight, gravity);
    }
}
