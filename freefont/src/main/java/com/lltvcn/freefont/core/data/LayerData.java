package com.lltvcn.freefont.core.data;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/10/10.
 */

public class LayerData {

    public static final int TYPE_MULTI = 2;
    public static final int TYPE_IMG = 1;
    public static final int TYPE_TXT = 0;

    public LayerData(){

    }


    /**
     * type: "img" , "txt","multi"*/
    public int type;

    public String name;

    public ArrayList<LayerData> layerDatas;

    public float offsetX,offsetY,degree,scale;

    public IndexParam<String> imgs;

    public IndexParam<String> colors;

    public PaintParam paintParam;

    public DispatchDrawParam drawParam;

    public static class DispatchDrawParam {
        public ClipParam clipParam;
        public OffsetParam offsetParam;
    }

    public static class PaintParam{
        public Float relativeSize;
        public String color;
        public IndexParam<String> colors;
        public String font;
        public String fontStyle;
        public String style;
        public StokeParam stokeParam;
        public BlurParam blurParam;
        public ShaderParam shaderParam;
        public ShadowParam shadowParam;
    }







}
