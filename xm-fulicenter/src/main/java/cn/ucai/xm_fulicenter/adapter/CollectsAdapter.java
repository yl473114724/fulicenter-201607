package cn.ucai.xm_fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.CollectBean;
import cn.ucai.xm_fulicenter.bean.MessageBean;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.view.FooterViewHolder;

/**
 * Created by yanglei on 2016/10/17.
 */
public class CollectsAdapter extends Adapter {
    List<CollectBean> mList;
    Context mcontext;
    boolean isMore;
    int soryBy=I.SORT_BY_ADDTIME_DESC;

    public CollectsAdapter(List<CollectBean> list, Context context) {
        this.mcontext = context;
        this.mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void setSortBy(int soryBy) {
        this.soryBy = soryBy;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mcontext, R.layout.item_footer, null));
        } else {
            holder = new CollectsViewHolder(View.inflate(mcontext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh= (FooterViewHolder) holder;
            vh.tvFooter.setText(getFooterString());
        } else {
            CollectsViewHolder vh= (CollectsViewHolder) holder;
            CollectBean goods = mList.get(position);
            ImageLoader.downloadImg(mcontext, vh.mivGoodsThumd, goods.getGoodsThumb());
            vh.mtvGoodsName.setText(goods.getGoodsName());
            vh.mlayoutGoods.setTag(goods);
        }
    }

    @Override
    public int getItemCount() {
        return mList != null? mList.size()+1:1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


     class CollectsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumd)
        ImageView mivGoodsThumd;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout mlayoutGoods;

         CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            CollectBean goods=(CollectBean)mlayoutGoods.getTag();
            MFGT.gotoGoodsDatailActivity(mcontext,goods.getGoodsId());
        }
         @OnClick(R.id.iv_collect_del)
         public void deleteCollect() {
             final CollectBean goods=(CollectBean)mlayoutGoods.getTag();
             final String muserName = FuLiCenterApplication.getUser().getMuserName();
             NetDao.deleteCollect(mcontext, muserName, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                 @Override
                 public void onSuccess(MessageBean result) {
                     if (result != null && result.isSuccess()) {
                         mList.remove(goods);
                         notifyDataSetChanged();
                     } else {
                         CommonUtils.showLongToast(result!=null?result.getMsg():mcontext.getResources().getString(R.string.delete_collect_fail));
                     }
                 }
                 @Override
                 public void onError(String error) {
                     CommonUtils.showLongToast(mcontext.getResources().getString(R.string.delete_collect_fail));
                 }
             });
         }
    }
}
