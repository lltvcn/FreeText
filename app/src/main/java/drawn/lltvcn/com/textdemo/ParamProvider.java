package drawn.lltvcn.com.textdemo;

/**
 * Created by zhaolei on 2017/8/17.
 */

public interface ParamProvider {

    int getInt(int index,int def);

    float getFloat(int index,float def);

    int getIntCount();

    int getFloatCount();

    void setInt(int index,int param);

    void setFloat(int index,float param);
}
