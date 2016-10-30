package cn.ucai.xm_fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.adapter.CartAdapter;
import cn.ucai.xm_fulicenter.bean.CartBean;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.utils.ResultUtils;
import cn.ucai.xm_fulicenter.view.SpaceItemDecoration;

import static android.content.ContentValues.TAG;

/**
 * Created by yanglei on 2016/10/19.
 */

public class CartFragment extends BaseFragment {
    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    @BindView(R.id.rv)
    RecyclerView mrv;
    MainActivity mContext;
    CartAdapter mAdapter;
    LinearLayoutManager llm;
    ArrayList<CartBean> mlist;
    @BindView(R.id.tv_cart_sum_price)
    TextView mtvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView mtvCartSavePrice;
    @BindView(R.id.layout_cart)
    RelativeLayout mlayoutCart;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;



    updateCartReceiver mReceiver;
    String cartIds="";

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mlist = new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mlist);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullDownListener();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        mReceiver = new updateCartReceiver();
        mContext.registerReceiver(mReceiver,filter);

    }

    private void setPullDownListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setRefreshing(true);
                mtvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    @Override
    protected void initData() {
        downloadCart();
    }

    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    msrl.setRefreshing(false);
                    mtvRefresh.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        mlist.clear();
                        mlist.addAll(list);
                        mAdapter.initData(mlist);
                        setCartLayout(true);
                    } else {
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
                    msrl.setRefreshing(false);
                    mtvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error" + error);
                }
            });
        }
    }

    @Override
    protected void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(mContext);
        mrv.setLayoutManager(llm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
        mrv.addItemDecoration(new SpaceItemDecoration(10));
        setCartLayout(false);
    }

    private void  setCartLayout(boolean hasCart) {
        mlayoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        tvNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        mrv.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        sumPrice();
    }

    @OnClick(R.id.tv_cart_buy)
    public void buy() {
        if(cartIds!=null && !cartIds.equals("") && cartIds.length()>0){
            MFGT.gotoBuy(mContext,cartIds);
        }else{
            CommonUtils.showLongToast(R.string.order_nothing);
        }

    }

    private void sumPrice() {
        cartIds = "";
        int sumPrice = 0;
        int rankPrice = 0;
        if (mlist != null && mlist.size() > 0) {
            for (CartBean c : mlist) {
                if (c.isChecked()) {
                    cartIds += c.getId()+",";
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            mtvCartSumPrice.setText("合计：￥"+Double.valueOf(rankPrice));
            mtvCartSavePrice.setText("节省:￥" + Double.valueOf(sumPrice - rankPrice));
        } else {
            cartIds = "";
            mtvCartSumPrice.setText("合计：￥0");
            mtvCartSavePrice.setText("节省：￥0");
        }
    }
    private int getPrice(String price) {
        price=price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }

    class updateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            sumPrice();
            setCartLayout(mlist != null && mlist.size() > 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume.......");
        initData();
        }

}
