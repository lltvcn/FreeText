package com.lltvcn.freefont.core.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/12/4.
 */

public interface ICanvasTransform {

    void transformCanvas(int index , RectF rect, Canvas canvas, Paint paint);

}
