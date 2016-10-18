package cn.ucai.xm_fulicenter.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.utils.L;

public class GoodsDatailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_datail);
        int goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details","goodsid="+goodsId);
    }
}
