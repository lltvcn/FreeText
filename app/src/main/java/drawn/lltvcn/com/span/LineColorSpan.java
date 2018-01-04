package drawn.lltvcn.com.span;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by zhaolei on 2017/9/4.
 */

public class LineColorSpan extends BaseSpan {

    private boolean isForeground;
    private Paint paint;

    public LineColorSpan(int color,boolean isForeground){
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        this.isForeground = isForeground;
    }


    @Override
    protected void afterDrawText(Canvas c, int left, int top, int right, int bottom, Paint p) {
        if(isForeground){
            c.drawRect(left,top,right,bottom,paint);
        }
    }

    @Override
    protected void beforeDrawText(Canvas c, int left, int top, int right, int bottom, Paint p) {
        if(!isForeground){
            c.drawRect(left-5,top,right+5,bottom,paint);
        }
    }
}
