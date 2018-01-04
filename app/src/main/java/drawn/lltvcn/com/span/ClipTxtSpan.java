package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import drawn.lltvcn.com.span.BaseSpan;

/**
 * Created by zhaolei on 2017/9/1.
 */

public class ClipTxtSpan extends BaseSpan {


    private Paint paint = new Paint();
    private static Canvas canvas;
    private static Bitmap bitmap;
    private Rect from = new Rect();
    private RectF to = new RectF();



    static {
        bitmap = Bitmap.createBitmap(1280,720, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    private float relativeSpan;
    private int span;

    public ClipTxtSpan(float rspan){
        this.relativeSpan = rspan;
    }


    @Override
    public void handleFont(Paint.FontMetricsInt fm) {
        span = (int) (txtPaint.getTextSize()*relativeSpan);
        fm.ascent -= span/2;
        fm.descent += span/2;
        fm.top -= span/2;
        fm.bottom += span/2;//拉高measure的高度
    }

    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        top = (int) (y+p.getFontMetrics().top);
        bottom = (int) (y+p.getFontMetrics().bottom);
        int width = (int) p.measureText(text,start,end);
        int height = bottom-top;
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        super.drawText(canvas,text,start,end,0,0,y-top,bottom-top,p);
        c.save();
        from.set(0,0,width,height/2);
        to.set(x,top,x+width,top+height/2);
        to.offset(0,-span/2);
        c.drawBitmap(bitmap,from,to,null);
        from.offset(0,height/2);
        to.offset(0,height/2+span);
        c.drawBitmap(bitmap,from,to,null);
        c.restore();
    }


}
