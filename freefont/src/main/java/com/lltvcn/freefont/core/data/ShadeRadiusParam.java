package com.lltvcn.freefont.core.data;

import android.graphics.Shader;

import com.lltvcn.freefont.core.annotation.Description;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class ShadeRadiusParam implements IShaderData {

    @Description(name = "圆心水平相对坐标")
    public float centerX;

    @Description(name = "圆心竖直相对坐标")
    public float centerY;

    @Description(name = "半径")
    public float radius;

    @Description(name = "渐变颜色")
    public String[] colors;

    @Description(name = "渐变位置")
    public float[] positions;

    @Description(name = "重复模式" , cls = Shader.TileMode.class)
    public String tileMode;

    @Override
    public ShaderParam toShaderParam(){
        ShaderParam shaderParam = new ShaderParam();
        shaderParam.radiusParam = this;
        return shaderParam;
    }

}
