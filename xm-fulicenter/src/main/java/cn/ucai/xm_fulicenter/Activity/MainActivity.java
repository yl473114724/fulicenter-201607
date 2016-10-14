package cn.ucai.xm_fulicenter.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");

    }
}
