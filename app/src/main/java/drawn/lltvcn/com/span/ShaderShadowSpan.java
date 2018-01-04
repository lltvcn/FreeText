package drawn.lltvcn.com.span;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by zhaolei on 2017/9/4.
 */

public class ShaderShadowSpan extends BaseSpan {

    private BitmapShader shader;


    public ShaderShadowSpan(Bitmap bitmap){
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }


    @Override
    protected void drawText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p) {
        drawShadowText(c, text, start, end, x, top, y, bottom, p);
    }

    private void drawShadowText(Canvas c, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint p){
        Shader tshader = p.getShader();
        p.setShader(shader);
        super.drawText(c, text, start, end, x+5, top, y+5, bottom, p);
        p.setShader(tshader);
        super.drawText(c, text, start, end, x, top, y, bottom, p);
    }
}
