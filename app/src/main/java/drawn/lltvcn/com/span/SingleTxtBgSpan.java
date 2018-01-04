package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;

/**
 * Created by zhaolei on 2017/9/11.
 */

public class SingleTxtBgSpan extends BaseSpan{

    public static final int MODE_NO = 0;
    public static final int MODE_FITX = 1;
    public static final int MODE_FITY = 2;
    public static final int MODE_FIT_CENTER = 3;
    private Bitmap bm;
    private int fit;
    private Rect from,to;

    public SingleTxtBgSpan(Bitmap bg,int fitMode){
        bm = bg;
        fit = fitMode;
        from = new Rect();
        to = new Rect();
        from.set(0,0,bg.getWidth(),bg.getHeight());
        to.set(0,0,bg.getWidth(),bg.getHeight());
    }


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        float scale = 1;
        to.set(from.left,from.top,from.right,from.bottom);
        switch (fit){
            case MODE_NO:
//                scale = ((float)(bottom-top)/bm.getHeight());
                break;
        }
        to.offset((int) x,top);
        int moreH = bottom-top-to.height();
        int moreW = (int) (p.getTextSize()-to.width());
        to.offset(moreW/2,moreH/2);
        for (int i = start; i < end; i++) {
//            c.drawCircle(x+i*2*offsetX+offsetX,offsetY+top,4,p);
            c.drawBitmap(bm,from,to,null);
            to.offset((int) p.getTextSize(),0);
        }
        super.drawText(c,text,start,end,x,top,y,bottom,p);
    }

}
