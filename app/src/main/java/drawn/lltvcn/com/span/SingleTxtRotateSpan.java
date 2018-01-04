package drawn.lltvcn.com.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Surface;

/**
 * Created by zhaolei on 2017/9/11.
 */

public class SingleTxtRotateSpan extends BaseSpan{
    private float degree;

    public SingleTxtRotateSpan(float degree){
        this.degree = degree;
    }


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        float offsetX = p.getTextSize()/2;
        float offsetY = (bottom-top)/2;
        for (int i = 0; i < end-start; i++) {
            c.drawCircle(x+i*2*offsetX+offsetX,offsetY+top,4,p);
            c.save();
            c.rotate(-degree,x+i*(2*offsetX)+offsetX,offsetY+top);
            super.drawText(c, text, i+start, i+1+start, x+i*(2*offsetX), top, y, bottom, p);
            c.restore();
        }
    }
}
