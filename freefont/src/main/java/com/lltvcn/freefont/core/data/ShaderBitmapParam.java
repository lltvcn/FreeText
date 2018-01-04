package com.lltvcn.freefont.core.data;

import android.graphics.Shader;

import com.lltvcn.freefont.core.annotation.Description;
import com.lltvcn.freefont.core.annotation.Img;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class ShaderBitmapParam implements IShaderData {

    @Description(name = "图片名称" ,cls = Img.class)
    public String img;

    @Description(name = "水平方向重复方式" ,cls = Shader.TileMode.class)
    public String tileModeX;

    @Description(name = "竖直方向重复方式" , cls = Shader.TileMode.class)
    public String tileModeY;

//    @Description(name = "图片高(默认为1,字体大小)")
//    public Float height;
//
//    @Description(name = "图片宽(默认为1,字体大小)")
//    public Float width;

    @Override
    public ShaderParam toShaderParam(){
        ShaderParam shaderParam = new ShaderParam();
        shaderParam.bitmapParam = this;
        return shaderParam;
    }
}
