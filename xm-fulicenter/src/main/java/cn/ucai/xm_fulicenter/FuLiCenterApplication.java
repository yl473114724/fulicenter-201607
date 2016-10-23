package cn.ucai.xm_fulicenter;

import android.app.Application;

/**
 * Created by yanglei on 2016/10/17.
 */
public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }

    private static String username;

    public FuLiCenterApplication() {
        instance=this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
}
