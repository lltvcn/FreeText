package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;

/**
 * Created by zhaolei on 2017/9/4.
 */

public class LineImgSpan extends BaseSpan {

    protected Bitmap bitmap;
    private Paint paint;
    private int gravity;
    private float span;
    private boolean isForeground,isFit;
    private Rect from,to;


    public LineImgSpan(Bitmap bm, int gravity){
        this(bm,gravity,0,false);
    }

    public LineImgSpan(Bitmap bm, int gravity, float span){
        this(bm,gravity,span,false);
    }


    public LineImgSpan(Bitmap bm, int gravity, float span, boolean isForeground){
        this(bm,gravity,span,isForeground,false);
    }

    public LineImgSpan(Bitmap bm, int gravity, float span, boolean isForeground,boolean isFit){
        bitmap = bm;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        this.span = span;
        this.gravity = gravity;
        this.isForeground = isForeground;
        this.isFit = isFit;
        from = new Rect();
        to = new Rect();
        from.set(0,0,bitmap.getWidth(),bitmap.getHeight());
    }

    @Override
    public void handleFont(Paint.FontMetricsInt fm) {

        if(span>0){
            int spanInt = (int) (txtPaint.getTextSize()*span);
            switch (getGravity()){
                case Gravity.TOP:
                    fm.descent += spanInt;
                    fm.bottom += spanInt;//拉高measure的高度
                    break;
                case Gravity.CENTER:
                    fm.ascent -= spanInt/2;
                    fm.descent += spanInt/2;
                    fm.top -= spanInt/2;
                    fm.bottom += spanInt/2;//拉高measure的高度
                    break;
                case Gravity.BOTTOM:
                    fm.ascent -= spanInt/2;
                    fm.descent += spanInt/2;
                    fm.top -= spanInt/2;
                    fm.bottom += spanInt/2;//拉高measure的高度
//                    fm.ascent -= spanInt;
//                    fm.top -= spanInt;
                    break;
            }
        }
        if(isFit){

        }
    }

    private int getGravity(){
        if((gravity&Gravity.TOP)==Gravity.TOP){
            return Gravity.TOP;
        }
        if((gravity&Gravity.CENTER)==Gravity.CENTER){
            return Gravity.CENTER;
        }
        if((gravity&Gravity.BOTTOM)==Gravity.BOTTOM){
            return Gravity.BOTTOM;
        }
        return gravity;
    }


    @Override
    protected void beforeDrawText(Canvas c, int left, int top, int right, int bottom, Paint p) {
        if(!isForeground){
            drawImg(c,left,top,right,bottom,p);
        }
    }

    @Override
    protected void afterDrawText(Canvas c, int left, int top, int right, int bottom, Paint p) {
        if(isForeground){
            drawImg(c,left,top,right,bottom,p);
        }
    }


    private void drawImg(Canvas c, int left, int top, int right, int bottom, Paint p){
        Log.i("kkkkk","drawImg"+left+":::"+top);
        paint.setStyle(Paint.Style.FILL);
        c.save();
        float scale = 1;
        if(isFit){
            scale = ((float)(bottom-top)/bitmap.getHeight());
        }
        to.set(0,0, (int) (bitmap.getWidth()*scale), (int) (bitmap.getHeight()*scale));
        to.offset(left,top);
        int moreH = bottom-top-to.height();
        if(moreH!=0){
            switch (getGravity()){
                case Gravity.CENTER:
                    to.offset(0,moreH/2);
                    break;
                case Gravity.BOTTOM:
                    to.offset(0,moreH);
                    break;
            }
        }

        drawBitmap(c,from,to,right-left);
        c.restore();
    }

    protected void drawBitmap(Canvas c,Rect from,Rect to,int width){
        int count = width/to.width();
        if(count==0){
            count = 1;
        }
        to.offset((width-(to.width()*count))/2,0);
        for (int i = 0; i < count; i++) {
            c.drawBitmap(bitmap,from,to,null);
            to.offset(to.width(),0);
        }
    }


}
