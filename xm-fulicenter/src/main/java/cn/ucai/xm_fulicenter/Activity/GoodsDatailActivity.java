package cn.ucai.xm_fulicenter.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.AlbumsBean;
import cn.ucai.xm_fulicenter.bean.GoodsDetailsBean;
import cn.ucai.xm_fulicenter.bean.MessageBean;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.view.FlowIndicator;
import cn.ucai.xm_fulicenter.view.SlideAutoLoopView;
import sharesdk.onekeyshare.OnekeyShare;

public class GoodsDatailActivity extends BaseActivity {

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
    boolean isCollected = false;
    @BindView(R.id.iv_good_coolect)
    ImageView ivGoodCoolect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_goods_datail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
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
                L.e("details,error=" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mtvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mtvGoodName.setText(details.getGoodsName());
        mtvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mtvGoodPriceShop.setText(details.getShopPrice());
        msalv.startPlayLoop(mindicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        mwvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }


    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }

        return urls;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_coolect)
    public void onCollectClick() {
        User user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLogin(mContext);
        } else {
            if (isCollected) {
                NetDao.deleteCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.addCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }

    public void onBackPressed(View v) {
        MFGT.finish(this);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {

        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private void isCollected() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isColected(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    } else {
                        isCollected = false;
                    }
                    updateGoodsCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodsCollectStatus();
                }
            });
        }
        updateGoodsCollectStatus();
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            ivGoodCoolect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivGoodCoolect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
    @OnClick(R.id.iv_good_share)
    public void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }


}
