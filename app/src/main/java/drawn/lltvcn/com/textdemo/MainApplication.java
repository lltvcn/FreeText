package drawn.lltvcn.com.textdemo;

import android.app.Application;

import java.io.File;
import java.io.IOException;

import drawn.lltvcn.com.util.FileUtil;
import drawn.lltvcn.com.util.ZipUtil;

/**
 * Created by zhaolei on 2017/10/19.
 */

public class MainApplication extends Application{

    private static MainApplication instance;
    public static MainApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if(!new File(FileUtil.getRootPath()+"e.e").exists()){
            try {
                ZipUtil.decompress(getAssets().open("res.zip"),FileUtil.getRootPath(),false);
                new File(FileUtil.getRootPath()+"e.e").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
