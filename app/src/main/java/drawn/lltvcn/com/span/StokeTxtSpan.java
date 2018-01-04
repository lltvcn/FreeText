package drawn.lltvcn.com.span;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;

/**
 * Created by zhaolei on 2017/9/4.
 */

public class StokeTxtSpan extends BaseSpan {

    private Paint paint = new Paint();
    private float relativeWidth;
    private int color;

    public StokeTxtSpan(float rWidth,int color){
        this.color = color;
        relativeWidth = rWidth;
    }


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        paint.set(p);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(p.getTextSize()*relativeWidth);
        paint.setColor(color);
//        paint.setMaskFilter(new BlurMaskFilter(p.getTextSize()*relativeWidth/2F, BlurMaskFilter.Blur.SOLID));
        paint.setShadowLayer(p.getTextSize()*relativeWidth*2,0.5f,0.5f, Color.argb(100,Color.red(color),Color.green(color),Color.blue(color)));
        super.drawText(c, text, start, end, x, top, y, bottom, paint);
        super.drawText(c, text, start, end, x, top, y, bottom, p);
    }
}
