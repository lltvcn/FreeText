package com.lltvcn.freefont.core.layer;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.graphics.Shader;

import java.util.ArrayList;

import com.lltvcn.freefont.core.data.BlurParam;
import com.lltvcn.freefont.core.data.IndexParam;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.data.ShadeRadiusParam;
import com.lltvcn.freefont.core.data.ShaderBitmapParam;
import com.lltvcn.freefont.core.data.ShaderLinearParam;
import com.lltvcn.freefont.core.data.ShaderParam;
import com.lltvcn.freefont.core.data.ShaderSweepParam;
import com.lltvcn.freefont.core.data.ShadowParam;
import com.lltvcn.freefont.core.data.StokeParam;

/**
 * Created by zhaolei on 2017/10/12.
 */

public class LayerDataFactory {

    public static LayerData createTxtLayer(){
        LayerData layerData = new LayerData();
        layerData.type = LayerData.TYPE_TXT;
        return layerData;
    }

    public static LayerData createImgLayer(String[] imgs,IndexParam.Rule rule){
        LayerData layerData = new LayerData();
        layerData.type = LayerData.TYPE_IMG;
        layerData.imgs = new IndexParam<String>();
        layerData.imgs.datas = imgs;
        layerData.imgs.rule = rule.name();
        return layerData;
    }

    public static LayerData createImgLayer(String[] imgs,String[] color,IndexParam.Rule rule,IndexParam.Rule ruleColor){
        LayerData layerData = new LayerData();
        layerData.type = LayerData.TYPE_IMG;
        if(imgs!=null&&rule!=null){
            layerData.imgs = new IndexParam<String>();
            layerData.imgs.datas = imgs;
            layerData.imgs.rule = rule.name();
        }
        if(color!=null&&ruleColor!=null){
            layerData.colors = new IndexParam<>();
            layerData.colors.datas = color;
            layerData.colors.rule = ruleColor.name();
        }
        return layerData;
    }


    public static LayerBuilder createTxtLayerBuilder(){
        LayerData layerData = new LayerData();
        layerData.type = LayerData.TYPE_TXT;
        return new LayerBuilder(layerData);
    }

    public static LayerBuilder createImgLayerBuilder(String[] imgs,IndexParam.Rule rule){
        LayerData layerData = new LayerData();
        layerData.type = LayerData.TYPE_IMG;
        layerData.imgs = new IndexParam<String>();
        layerData.imgs.datas = imgs;
        layerData.imgs.rule = rule.name();
        return new LayerBuilder(layerData);
    }

    public static ShaderLinearParam createLinerShaderParam(float x0, float y0, float x1, float y1, String color0, String color1, Shader.TileMode tile){
        ShaderLinearParam shaderParam = new ShaderLinearParam();
        shaderParam.x0 = x0;
        shaderParam.x1 = x1;
        shaderParam.y0 = y0;
        shaderParam.y1 = y1;
        shaderParam.colors = new String[2];
        shaderParam.colors[0] = color0;
        shaderParam.colors[1] = color1;
        shaderParam.tileMode = tile.name();
        return shaderParam;
    }

    public static ShaderLinearParam createLinerShaderParam(float x0, float y0, float x1, float y1, String[] colors, float[] positions, Shader.TileMode tileMode){
        ShaderLinearParam shaderParam = new ShaderLinearParam();
        shaderParam.x0 = x0;
        shaderParam.x1 = x1;
        shaderParam.y0 = y0;
        shaderParam.y1 = y1;
        shaderParam.colors = colors;
        shaderParam.positions = positions;
        shaderParam.tileMode = tileMode.name();
        return shaderParam;
    }

    public static ShaderSweepParam createSweepShaderParam(float cx, float cy, String color0, String color1){
        ShaderSweepParam shaderParam = new ShaderSweepParam();
        shaderParam.centerX = cx;
        shaderParam.centerY = cy;
        shaderParam.colors = new String[2];
        shaderParam.colors[0] = color0;
        shaderParam.colors[1] = color1;
        return shaderParam;
    }

    public static ShaderSweepParam createSweepShaderParam(float cx, float cy, String colors[], float positions[]){
        ShaderSweepParam shaderParam = new ShaderSweepParam();
        shaderParam.centerX = cx;
        shaderParam.centerY = cy;
        shaderParam.colors = colors;
        shaderParam.positions = positions;
        return shaderParam;
    }

    public static ShadeRadiusParam createRadialShaderParam(float centerX, float centerY, float radius,String colorStart, String colorEnd, Shader.TileMode tileMode){
        ShadeRadiusParam shaderParam = new ShadeRadiusParam();
        shaderParam.centerX = centerX;
        shaderParam.centerY = centerY;
        shaderParam.radius = radius;
        shaderParam.colors = new String[2];
        shaderParam.colors[0] = colorStart;
        shaderParam.colors[1] = colorEnd;
        shaderParam.tileMode = tileMode.name();
        return shaderParam;
    }

    public static ShadeRadiusParam createRadialShaderParam(float centerX, float centerY, float radius,String colors[], float stops[], Shader.TileMode tileMode){
        ShadeRadiusParam shaderParam = new ShadeRadiusParam();
        shaderParam.centerX = centerX;
        shaderParam.centerY = centerY;
        shaderParam.radius = radius;
        shaderParam.colors = colors;
        shaderParam.positions = stops;
        shaderParam.tileMode = tileMode.name();
        return shaderParam;
    }

    public static ShaderBitmapParam createBitmapShaderParam(String imgName, Shader.TileMode tileX, Shader.TileMode tileY){
        ShaderBitmapParam shaderParam = new ShaderBitmapParam();
        shaderParam.img = imgName;
        shaderParam.tileModeX = tileX.name();
        shaderParam.tileModeY = tileY.name();
        return shaderParam;
    }



    public static class LayerBuilder{
        private LayerData layerData;

        private LayerBuilder(LayerData data){
            layerData = data;
        }

        public LayerBuilder imgs(String[] imgs,IndexParam.Rule rule){
            if(layerData.imgs ==null){
                layerData.imgs = new IndexParam<String>();
            }
            layerData.imgs.datas = imgs;
            layerData.imgs.rule = rule.name();
            return this;
        }

        public LayerBuilder add(LayerData layer){
            if(layerData.layerDatas==null){
                layerData.layerDatas = new ArrayList<>();
            }
            layerData.layerDatas.add(layer);
            return this;
        }

        public LayerBuilder offset(float x,float y){
            layerData.offsetX = x;
            layerData.offsetY = y;
            return this;
        }

        public LayerBuilder rotate(float degree){
            layerData.degree = degree;
            return this;
        }

        public LayerBuilder scale(float scale){
            layerData.scale = scale;
            return this;
        }

        public LayerBuilder color(String color){
            preparePaintParam();
            layerData.paintParam.color = color;
            return this;
        }

        public LayerBuilder size(float size){
            preparePaintParam();
            layerData.paintParam.relativeSize = size;
            return this;
        }

        public LayerBuilder font(String fontName){
            preparePaintParam();
            layerData.paintParam.font = fontName;
            return this;
        }

        public LayerBuilder fontStyle(FontStyle style){
            preparePaintParam();
            layerData.paintParam.fontStyle = style.name();
            return this;
        }


        public LayerBuilder stoke(float width,Paint.Join join){
            preparePaintParam();
            layerData.paintParam.stokeParam = new StokeParam();
            layerData.paintParam.stokeParam.join = join.name();
            layerData.paintParam.stokeParam.width = width;
//            layerData.paintParam.style =
            return this;
        }

        public LayerBuilder shadow(float radius, float dx, float dy, String shadowColor){
            preparePaintParam();
            layerData.paintParam.shadowParam = new ShadowParam(radius,dx,dy,shadowColor);
            return this;
        }

        public LayerBuilder blur(float radius, BlurMaskFilter.Blur blur){
            preparePaintParam();
            layerData.paintParam.blurParam = new BlurParam();
            layerData.paintParam.blurParam.blur = blur.name();
            layerData.paintParam.blurParam.radius = radius;
            return this;
        }

        public LayerBuilder linearGradient(float x0, float y0, float x1, float y1, String color0, String color1, Shader.TileMode tileMode){
            prepareShaderParam();
            layerData.paintParam.shaderParam.linearParam = createLinerShaderParam(x0,y0,x1,y1,color0,color1,tileMode);
            return this;
        }

        public LayerBuilder linearGradient(float x0, float y0, float x1, float y1, String[] colors, float[] positions, Shader.TileMode tileMode){
            prepareShaderParam();
            layerData.paintParam.shaderParam.linearParam = createLinerShaderParam(x0,y0,x1,y1,colors,positions,tileMode);
            return this;
        }

        public LayerBuilder sweepGradient(float cx, float cy, String color0, String color1){
            prepareShaderParam();
            layerData.paintParam.shaderParam.sweepParam = createSweepShaderParam(cx,cy,color0,color1);
            return this;
        }

        public LayerBuilder sweepGradient(float cx, float cy, String colors[], float positions[]){
            prepareShaderParam();
            layerData.paintParam.shaderParam.sweepParam = createSweepShaderParam(cx,cy,colors,positions);
            return this;
        }

        public LayerBuilder radialGradient(float centerX, float centerY, float radius, String centerColor, String edgeColor, Shader.TileMode tileMode){
            prepareShaderParam();
            layerData.paintParam.shaderParam.radiusParam = createRadialShaderParam(centerX,centerY,radius,centerColor,centerColor,tileMode);
            return this;
        }

        public LayerBuilder radialGradient(float centerX, float centerY, float radius,String colors[], float stops[], Shader.TileMode tileMode){
            prepareShaderParam();
            layerData.paintParam.shaderParam.radiusParam = createRadialShaderParam(centerX,centerY,radius,colors,stops,tileMode);
            return this;
        }

        public LayerBuilder bitmapShader(String imgName, Shader.TileMode tileX,Shader.TileMode tileY){
            prepareShaderParam();
            layerData.paintParam.shaderParam.bitmapParam = createBitmapShaderParam(imgName,tileX,tileY);
            return this;
        }

        public LayerData create(){
            return layerData;
        }



        private void prepareShaderParam(){
            preparePaintParam();
            if(layerData.paintParam.shaderParam == null){
                layerData.paintParam.shaderParam = new ShaderParam();
            }
        }

        private void preparePaintParam(){
            if(layerData.paintParam == null){
                layerData.paintParam = new LayerData.PaintParam();
            }
        }
    }
}
