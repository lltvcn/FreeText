package com.lltvcn.freefont.core.data;

import java.util.ArrayList;

import com.lltvcn.freefont.core.annotation.Description;
import com.lltvcn.freefont.core.annotation.Img;


/**
 * Created by zhaolei on 2017/10/11.
 */

public class DrawData {

    public ArrayList<LineData> backLayers;

    public ArrayList<LineData> foreLayers;

    public ArrayList<LayerData> layers;

    public Float width;

    public Float height;

    @Description(name = "图片名称",cls = Img.class)
    public String bgImg;

    @Description(name = "颜色")
    public String bgColor;

    public String fontStyle;

    public ShaderParam shaderParam;

//    public AniData aniData;

    public Integer aniType;


}
