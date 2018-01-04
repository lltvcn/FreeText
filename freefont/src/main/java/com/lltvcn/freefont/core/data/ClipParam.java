package com.lltvcn.freefont.core.data;

import com.lltvcn.freefont.core.annotation.Description;

/**
 * Created by zhaolei on 2017/10/24.
 */

public class ClipParam implements IDispatchDraw{

    @Description(name = "间隔高度")
    public float span;

    @Override
    public LayerData.DispatchDrawParam toDispatchDrawParam() {
        LayerData.DispatchDrawParam param = new LayerData.DispatchDrawParam();
        param.clipParam = this;
        return param;
    }
}
