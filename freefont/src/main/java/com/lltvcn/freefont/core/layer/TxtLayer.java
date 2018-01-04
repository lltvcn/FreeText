package com.lltvcn.freefont.core.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;


/**
 * Created by zhaolei on 2017/9/26.
 */

public class TxtLayer extends BaseLayer{


    @Override
    public void drawLayer(int index,Canvas canvas, DrawParam param, Paint paint) {
        if(param instanceof ITxtDrawParam){
            TxtParam txtParam = ((ITxtDrawParam)param).getTxtParam();
            if(txtParam!=null){
                Paint.FontMetrics metrics = paint.getFontMetrics();
                Log.i("draw-txt",""+txtParam.y);
                canvas.drawText(txtParam.text,txtParam.start,txtParam.end,txtParam.x,txtParam.y,paint);
//                canvas.drawText(txtParam.text,txtParam.start,txtParam.end,txtParam.x,txtParam.centerY-(metrics.bottom+metrics.top)/2f,paint);
            }
        }
    }

    public interface ITxtDrawParam extends DrawParam{
        TxtParam getTxtParam();
    }

    public static class TxtParam{
        public float x,y,centerY;
        public int start,end;
        public CharSequence text;
    }
}
