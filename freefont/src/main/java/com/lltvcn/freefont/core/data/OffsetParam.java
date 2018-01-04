package com.lltvcn.freefont.core.data;

import com.lltvcn.freefont.core.annotation.Description;

/**
 * Created by zhaolei on 2017/10/24.
 */

public class OffsetParam implements IDispatchDraw{

    @Description(name = "位置信息，与偏移量一一对应")
    public float[] positions;

    @Description(name = "每个位置的偏移量")
    public float[] offsets;

    @Override
    public LayerData.DispatchDrawParam toDispatchDrawParam() {
        LayerData.DispatchDrawParam param = new LayerData.DispatchDrawParam();
        param.offsetParam = this;
        return param;
    }
}
