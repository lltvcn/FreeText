package com.lltvcn.freefont.core.data;

import android.graphics.Shader;

import com.lltvcn.freefont.core.annotation.Description;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class ShaderLinearParam implements IShaderData {

    @Description(name = "起点水平相对坐标")
    public float x0;

    @Description(name = "终点水平相对坐标")
    public float x1;

    @Description(name = "起点竖直相对坐标")
    public float y0;

    @Description(name = "终点竖直相对坐标")
    public float y1;

    @Description(name = "渐变颜色")
    public String[] colors;

    @Description(name = "渐变位置")
    public float[] positions;

    @Description(name = "重复模式" ,cls = Shader.TileMode.class)
    public String tileMode;

    @Override
    public ShaderParam toShaderParam(){
        ShaderParam shaderParam = new ShaderParam();
        shaderParam.linearParam = this;
        return shaderParam;
    }

}
