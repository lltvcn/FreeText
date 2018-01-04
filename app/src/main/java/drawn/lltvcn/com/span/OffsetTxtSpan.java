package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by zhaolei on 2017/9/5.
 */

public class OffsetTxtSpan extends BaseSpan {
    private Paint paint = new Paint();
    private static Canvas canvas;
    private static Bitmap bitmap;
    private Rect from = new Rect();
    private RectF to = new RectF();


    static {
        bitmap = Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }


    private float[] positions,offsets;


    public OffsetTxtSpan(float[] positions, float[] offsets){
        this.positions = positions;
        this.offsets = offsets;
        if(positions==null&&offsets==null){

        }else{
            if(positions.length!=offsets.length){
                throw new RuntimeException("param length wrong!");
            }
        }

    }


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        int width = (int) p.measureText(text,start,end);
        int height = bottom-top;
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        super.drawText(canvas,text,start,end,0,0,y-top,bottom-top,p);
//        canvas.drawText(text,start,end,0,y-top,p);//减去top是为了画在bitmap的最上端
        if(positions!=null){
            c.save();
            c.translate(x,top);
            int offsetY = 0;
            for (int i = 0; i < positions.length; i++) {
                from.set(0,offsetY,width,offsetY+=height*positions[i]);
                to.set(0,from.top,width,from.bottom);
                to.offset(offsets[i]*p.getTextSize(),0);
                c.drawBitmap(bitmap,from,to,null);
            }
            c.restore();
        }
    }
}
