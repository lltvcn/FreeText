package com.lltvcn.freefont.core.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.data.ShaderBitmapParam;
import com.lltvcn.freefont.core.data.ShaderParam;
import com.lltvcn.freefont.core.util.CU;


/**
 * Created by zhaolei on 2017/10/11.
 */

public class PaintHandler implements ILayer.IPaintHandler {

    public static final float S = 300;
    public static final RectF R = new RectF(0,0,S,S);
    private LayerData.PaintParam paintParam;
    private BlurMaskFilter maskFilter;
    private Shader shader;
    private Matrix matrix;
    private SourceLoader<Bitmap> loader;
    private SourceLoader<Typeface> fontLoader;
    private RectF shadeRect;

    public PaintHandler(LayerData.PaintParam param){
       this(param,null,null);
    }

    public PaintHandler(LayerData.PaintParam param, SourceLoader<Bitmap> loader, SourceLoader<Typeface> fontLoader){
        paintParam = param;
        this.loader = loader;
        this.fontLoader = fontLoader;
        if(paintParam!=null){
            if(paintParam.blurParam!=null){
                maskFilter = new BlurMaskFilter(param.blurParam.radius* S, BlurMaskFilter.Blur.valueOf(param.blurParam.blur));
            }
            if(paintParam.shaderParam!=null){
                shader = generalShaderByParam(paintParam.shaderParam);
            }
            if(shader!=null){
                matrix = new Matrix();
            }
        }
    }

    @Override
    public void handlePaint(int index,Paint paint, RectF rectF) {
        float textSize = paint.getTextSize();
        float alpha = paint.getAlpha()/255f;
        if(!TextUtils.isEmpty(paintParam.color)){
            paint.setColor(CU.toInt(paintParam.color));
        }else if(paintParam.colors!=null&&paintParam.colors.available()){
            paint.setColor(CU.toInt(paintParam.colors.getDataByIndex(index)));
        }
        paint.setAlpha((int) (alpha*paint.getAlpha()));
        if(!TextUtils.isEmpty(paintParam.style)){
            paint.setStyle(Paint.Style.valueOf(paintParam.style));
        }
        if(paintParam.stokeParam!=null){
            if(alpha>0){
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(paintParam.stokeParam.width*textSize);
                paint.setStrokeJoin(Paint.Join.valueOf(paintParam.stokeParam.join));
            }else{
                paint.setStrokeWidth(0);
            }
        }
        if(paintParam.relativeSize!=null){
            paint.setTextSize(paintParam.relativeSize*textSize);
        }
        if(!TextUtils.isEmpty(paintParam.font)){
            paint.setTypeface(fontLoader.loadByName(paintParam.font));
        }
        if(!TextUtils.isEmpty(paintParam.fontStyle)){
            paint.setTypeface(Typeface.create(paint.getTypeface(),FontStyle.valueOf(paintParam.fontStyle).ordinal()));
        }
        if(paintParam.shadowParam!=null){
            paint.setShadowLayer(paintParam.shadowParam.radius*textSize,paintParam.shadowParam.x*textSize,paintParam.shadowParam.y*textSize, CU.toInt(paintParam.shadowParam.color));
        }
        if(maskFilter!=null){
            paint.setMaskFilter(maskFilter);
        }
        if(shader!=null){
            matrix.reset();
            Log.i("jjjjkkk","handlePaint");
            matrix.setRectToRect(shadeRect,rectF, Matrix.ScaleToFit.FILL);
//            matrix.setTranslate(rectF.left,rectF.top);
            shader.setLocalMatrix(matrix);
            paint.setShader(shader);
        }
    }

    private Shader generalShaderByParam(ShaderParam param){
        Shader shader = null;
        if(param.bitmapParam!=null){
            if(loader!=null){
                ShaderBitmapParam bitmapParam = param.bitmapParam;
                Bitmap bm = loader.loadByName(bitmapParam.img);
                if(bm!=null){
//                    Bitmap tBm = Bitmap.createBitmap((int) S,(int) S, Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(tBm);
//                    Rect rect = new Rect();
//                    float scale = 0;
//                    if(bm.getWidth()>bm.getHeight()){
//                        int offset = (bm.getWidth()-bm.getHeight())/2;
//                        rect.set(offset,0,bm.getWidth()-offset,bm.getHeight());
//                    }else{
//                        int offset = (bm.getHeight()-bm.getWidth())/2;
//                        rect.set(0,offset,bm.getWidth(),bm.getHeight()-offset);
//                    }
//                    canvas.drawBitmap(bm,rect,S.R,null);
                    shadeRect = new RectF(0,0,bm.getWidth(),bm.getHeight());
                    shader = new BitmapShader(bm, Shader.TileMode.valueOf(bitmapParam.tileModeX), Shader.TileMode.valueOf(bitmapParam.tileModeY));
                }
            }
        }
        if(param.sweepParam!=null){
            // TODO: 2017/10/12 这里没有考虑字的宽度来调整圆心位置
            if(param.sweepParam.colors==null||param.sweepParam.colors.length<2){
                throw new RuntimeException("LinearGradient param wrong!");
            }
            if((param.sweepParam.positions==null||param.sweepParam.positions.length==0)&&param.sweepParam.colors.length==2){
                shader = new SweepGradient(param.sweepParam.centerX*S,param.sweepParam.centerY*S,CU.toInt(param.sweepParam.colors[0]),CU.toInt(param.sweepParam.colors[1]));
            }else{
                shader = new SweepGradient(param.sweepParam.centerX*S,param.sweepParam.centerY*S,CU.toInt(param.sweepParam.colors),param.sweepParam.positions);
            }
            shadeRect = R;
        }
        if(param.linearParam!=null){
            if(param.linearParam.colors==null||param.linearParam.colors.length<2){
                throw new RuntimeException("LinearGradient param wrong!");
            }
            if((param.linearParam.positions==null||param.linearParam.positions.length==0)&&param.linearParam.colors.length==2){
                shader = new LinearGradient(param.linearParam.x0*S,param.linearParam.y0*S,param.linearParam.x1*S,param.linearParam.y1*S,CU.toInt(param.linearParam.colors[0]),CU.toInt(param.linearParam.colors[1]), Shader.TileMode.valueOf(param.linearParam.tileMode));
            }else{
                shader = new LinearGradient(param.linearParam.x0*S,param.linearParam.y0*S,param.linearParam.x1*S,param.linearParam.y1*S,
                        CU.toInt(param.linearParam.colors),param.linearParam.positions, Shader.TileMode.valueOf(param.linearParam.tileMode));
            }
            shadeRect = R;
        }
        if(param.radiusParam!=null){
            // TODO: 2017/10/12 这里没有考虑字的宽度来调整圆心位置
            if(param.radiusParam.colors==null||param.radiusParam.colors.length<2){
                throw new RuntimeException("LinearGradient param wrong!");
            }
            if((param.radiusParam.positions==null||param.radiusParam.positions.length==0)&&param.radiusParam.colors.length==2){
                shader = new RadialGradient(param.radiusParam.centerX*S,param.radiusParam.centerY*S,param.radiusParam.radius*S,CU.toInt(param.radiusParam.colors[0]),CU.toInt(param.radiusParam.colors[1]), Shader.TileMode.valueOf(param.radiusParam.tileMode));
            }else{
                shader = new RadialGradient(param.radiusParam.centerX*S,param.radiusParam.centerY*S,param.radiusParam.radius*S,CU.toInt(param.radiusParam.colors),param.radiusParam.positions, Shader.TileMode.valueOf(param.radiusParam.tileMode));
            }
            shadeRect = R;
        }
        return shader;
    }

}
