package cn.ucai.xm_fulicenter.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private final long sleepTime=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainAcyivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);
    }
}
