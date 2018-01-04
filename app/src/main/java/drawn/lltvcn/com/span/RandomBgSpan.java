package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by zhaolei on 2017/9/4.
 */

public class RandomBgSpan extends LineImgSpan {


    public RandomBgSpan(Bitmap bm, int gravity, int span, boolean isForeground, boolean isFit) {
        super(bm, gravity, span, isForeground, isFit);
    }

    public RandomBgSpan(Bitmap bm, int gravity, int span, boolean isForeground) {
        this(bm, gravity, span, isForeground, true);
    }


    @Override
    protected void drawBitmap(Canvas c, Rect from, Rect to, int width) {
//        int count = width/to.width();
//        to.offset((width-(to.width()*count))/2,0);
//        for (int i = 0; i < count; i++) {
//            c.drawBitmap(img,from,to,null);
//            to.offset(to.width(),0);
//        }
        int end = to.width();
        int offset = 0;
        Random random = new Random(500);

        while (end<=width){
            c.drawBitmap(bitmap,from,to,null);
            offset = random.nextInt(to.width()/2)+to.width()/2;
            if(end+offset>width){
                offset = width-end;
                if(offset==0){
                    break;
                }
            }
            to.offset(offset,0);
            end += offset;
        }
    }
}
