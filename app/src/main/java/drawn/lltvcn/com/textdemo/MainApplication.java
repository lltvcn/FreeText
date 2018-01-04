package drawn.lltvcn.com.textdemo;

import android.app.Application;

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
    }
}
