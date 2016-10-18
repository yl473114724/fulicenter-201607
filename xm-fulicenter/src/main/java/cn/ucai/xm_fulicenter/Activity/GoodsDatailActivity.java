package cn.ucai.xm_fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.GoodsDetailsBean;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.view.FlowIndicator;
import cn.ucai.xm_fulicenter.view.SlideAutoLoopView;

public class GoodsDatailActivity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    LinearLayout mbackClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView mtvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mtvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mtvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mtvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView msalv;
    @BindView(R.id.indicator)
    FlowIndicator mindicator;
    @BindView(R.id.wv_good_brief)
    WebView mwvGoodBrief;
    int goodsId;
    GoodsDatailActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_datail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext=this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {

    }

    private void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details=" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error="+error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mtvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mtvGoodName.setText(details.getGoodsName());
        mtvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mtvGoodPriceShop.setText(details.getShopPrice());
    }

    private void initView() {

    }
}
