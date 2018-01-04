package com.lltvcn.freefont.core.data;

import com.lltvcn.freefont.core.annotation.Description;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class ShaderSweepParam implements IShaderData {

    @Description(name = "圆心X相对坐标")
    public float centerX;

    @Description(name = "圆心Y相对坐标")
    public float centerY;

    @Description(name = "渐变颜色")
    public String[] colors;

    @Description(name = "渐变位置")
    public float[] positions;

    @Override
    public ShaderParam toShaderParam(){
        ShaderParam shaderParam = new ShaderParam();
        shaderParam.sweepParam = this;
        return shaderParam;
    }
}
