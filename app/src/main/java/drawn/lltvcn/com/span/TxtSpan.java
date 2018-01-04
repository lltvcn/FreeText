package drawn.lltvcn.com.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by zhaolei on 2017/9/21.
 */

public class TxtSpan extends BaseSpan{

    private static Paint savePaint = new Paint();
    private PaintHandler handler;

    public TxtSpan setPaintHandler(PaintHandler handler){
        this.handler = handler;
        return this;
    }

    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        savePaint.set(p);
        handler.handlePaint(savePaint);
        super.drawText(c, text, start, end, x, top, y, bottom, savePaint);
    }

    public interface PaintHandler{
        void handlePaint(Paint p);
    }
}
