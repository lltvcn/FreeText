package com.lltvcn.freefont.core.data;

import android.graphics.BlurMaskFilter;

import com.lltvcn.freefont.core.annotation.Description;

public class BlurParam{

        @Description(name = "半径")
        public float radius;

        @Description(name = "模糊方式",cls = BlurMaskFilter.Blur.class)
        public String blur;
    }