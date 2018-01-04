package drawn.lltvcn.com.span;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by zhaolei on 2017/9/14.
 */

public class LightSpan extends BaseSpan{
    Paint paint = new Paint();

    public LightSpan(){
        paint.setAntiAlias(true);
    }

    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        paint.set(p);
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        super.drawText(c, text, start, end, x, top, y, bottom, paint);
    }
}
