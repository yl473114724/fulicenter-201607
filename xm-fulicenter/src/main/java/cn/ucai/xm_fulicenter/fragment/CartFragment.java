package cn.ucai.xm_fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.adapter.CartAdapter;
import cn.ucai.xm_fulicenter.bean.CartBean;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.ConvertUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.ResultUtils;
import cn.ucai.xm_fulicenter.view.SpaceItemDecoration;

/**
 * Created by yanglei on 2016/10/19.
 */

public class CartFragment extends BaseFragment {
    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    LinearLayoutManager llm;
    MainActivity mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mlist;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mlist=new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mlist);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }
    @Override
    protected void setListener() {
        setPullDownListener();
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
            NetDao.downloadCart(mContext,user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    msrl.setRefreshing(false);
                    mtvRefresh.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        mAdapter.initData(list);
                    }
                }
                @Override
                public void onError(String error) {
                    msrl.setRefreshing(false);
                    mtvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error"+error);
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
    }
}
