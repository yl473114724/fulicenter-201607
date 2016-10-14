package cn.ucai.xm_fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.R;

/**
 * Created by yanglei on 2016/10/14.
 */
public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainAcyivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    private static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
