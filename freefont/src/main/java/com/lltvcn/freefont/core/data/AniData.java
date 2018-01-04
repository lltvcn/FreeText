package com.lltvcn.freefont.core.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhaolei on 2017/12/6.
 */

public class AniData {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_SINGLE_TXT = 1;

    public static final int MODE_SEQUENCE = 0;

    public int type;

    public Float fromX,toX,fromY,toY,fromAlpha,toAlpha,fromScaleX,toScaleX,fromScaleY,toScaleY,fromDegree,toDegree;

    public int aniMode;

    public HashMap<String,String> aniParam;

    public long duration;

    public int repeatMode;

    public int repeatCount;

    public int startDelay;

    public ArrayList<AniData> anis;
}
