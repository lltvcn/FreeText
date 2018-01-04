package drawn.lltvcn.com.textdemo;

import android.widget.TextView;

/**
 * Created by zhaolei on 2017/8/17.
 */

public abstract class TxtShader2Ext implements TxtShader2{


    private ParamProvider paramProvider;


    public final ParamProvider getParamProvider(){
        if(paramProvider==null){
            paramProvider = new ParamProviderImpl();
        }
        return paramProvider;
    }


}
