package cn.ucai.xm_fulicenter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.xm_fulicenter.R;


public class ShanpinActivity extends AppCompatActivity {
    private final long sleepTime=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanpin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start=System.currentTimeMillis();
                long costTime=System.currentTimeMillis()-start;
                if (sleepTime - costTime > 0) {
                    try{
                        //耗时操作
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(ShanpinActivity.this,MainActivity.class));
                finish();
               /* MFGT.gotoMainActivity(ShanpinActivity.this);
                MFGT.finish(ShanpinActivity.this);*/

            }
        }).start();
    }
}
