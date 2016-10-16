package cn.ucai.xm_fulicenter.Activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                break;
            case R.id.layout_boutique:
                break;
            case R.id.layout_category:
                break;
            case R.id.layout_cart:
                break;
            case R.id.layout_personal_center:
                break;
        }
    }
}
