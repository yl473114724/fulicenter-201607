package cn.ucai.xm_fulicenter.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.dao.UserDao;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final long sleepTime=2000;
    SplashActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiCenterApplication.getUser();
                if (user==null) {
                    UserDao dao = new UserDao(mContext);
                    user=dao.getUser("a9527010");
                    L.e(TAG, "User=" + user);
                }
                MFGT.gotoMainAcyivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);
    }
}
