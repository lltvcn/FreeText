package drawn.lltvcn.com.textdemo;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/8/17.
 */

public class ParamProviderImpl implements ParamProvider{

    private SparseArray<Integer> integers = new SparseArray<>(1);
    private SparseArray<Float> floats = new SparseArray<>(1);

    @Override
    public int getInt(int index, int def) {
        if(integers.get(index)==null){
            integers.put(index,def);
        }
        return integers.get(index);
    }

    @Override
    public float getFloat(int index, float def) {
        if(floats.get(index)==null){
            floats.put(index,def);
        }
        return floats.get(index);
    }

    @Override
    public int getIntCount() {
        return integers.size();
    }

    @Override
    public int getFloatCount() {
        return floats.size();
    }

    @Override
    public void setInt(int index, int param) {
        integers.put(index,param);
    }

    @Override
    public void setFloat(int index, float param) {
        floats.put(index,param);
    }

}
