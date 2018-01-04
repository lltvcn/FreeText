package drawn.lltvcn.com.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by zhaolei on 2017/9/5.
 */

public class ShakeTxtSpan extends BaseSpan {


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        int color = p.getColor();
        int alpha = p.getAlpha();
        p.setAlpha(100);
        p.setColor(Color.rgb(80,236,204));
        super.drawText(c, text, start, end, x-3, top, y-3, bottom, p);
        p.setColor(Color.rgb(222,7,84));
        super.drawText(c, text, start, end, x+3, top, y+3, bottom, p);
        p.setAlpha(alpha);
        p.setColor(color);
        super.drawText(c, text, start, end, x, top, y, bottom, p);
    }
}
