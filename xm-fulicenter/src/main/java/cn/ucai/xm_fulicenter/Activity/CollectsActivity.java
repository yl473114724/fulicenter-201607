package cn.ucai.xm_fulicenter.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.adapter.CollectsAdapter;
import cn.ucai.xm_fulicenter.adapter.GoodsAdapter;
import cn.ucai.xm_fulicenter.bean.CollectBean;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.ConvertUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.view.DisplayUtils;
import cn.ucai.xm_fulicenter.view.SpaceItemDecoration;

public class CollectsActivity extends BaseActivity {

    CollectsActivity mContext;
    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    CollectsAdapter mAdapter;
    ArrayList<CollectBean> mlist;
    int pageId=1;
    GridLayoutManager glm;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        mContext = this;
        mlist = new ArrayList<>();
        mAdapter=new CollectsAdapter(mlist,mContext);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.collect_title));
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
        mrv.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setRefreshing(true);
                mtvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadCollects(final int action) {
        NetDao.downloadCollects(mContext,user.getMuserName(), pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> List = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(List);
                    } else {
                        mAdapter.addData(List);
                    }
                    if (List.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e("error" + error);
            }
        });
    }

    private void setPullUpListener() {
        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastposition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastposition == mAdapter.getItemCount() - 1 && mAdapter.isMore()) {
                    pageId++;
                    downloadCollects(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstposition = glm.findFirstVisibleItemPosition();
                msrl.setEnabled(firstposition == 0);
            }
        });
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            finish();
        }
        downloadCollects(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
