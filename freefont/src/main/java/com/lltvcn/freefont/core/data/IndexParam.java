package com.lltvcn.freefont.core.data;

import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.lltvcn.freefont.core.annotation.Description;
import com.lltvcn.freefont.core.util.CU;

import java.util.Random;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class IndexParam<T> {

    public enum Rule{
        Normal,Revert,Random
    }

    private transient static  Random random = new Random(1000);

    @Description(name = "变化规则" ,cls = Rule.class)
    public String rule;

    public T[] datas;

    public boolean available(){
        return datas!=null&&datas.length>0;
    }

    public T getDataByIndex(int index){
        if(datas!=null){
            int length = datas.length;
            if(length==1){
                return datas[0];
            }else {
                switch (IndexParam.Rule.valueOf(rule)){
                    case Random:
                        return datas[random.nextInt(length)];
                    case Normal:
                        return datas[index%length];
                    case Revert:
                        if((index/length)%2 ==0){
                            index = length-index%length-1;
                        }else{
                            index = index%length;
                        }
                        return datas[index];
                }
            }
        }
        return null;
    }
}
