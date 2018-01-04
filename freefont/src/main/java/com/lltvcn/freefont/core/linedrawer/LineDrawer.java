package com.lltvcn.freefont.core.linedrawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by zhaolei on 2017/9/18.
 */

public interface LineDrawer{

    void draw(Canvas c, Paint p,float left,int top,float right,int bottom,int baseLine);

}
