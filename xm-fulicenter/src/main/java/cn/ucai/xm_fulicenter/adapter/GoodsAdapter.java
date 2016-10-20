package cn.ucai.xm_fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.Activity.GoodsDatailActivity;
import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.NewGoodsBean;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.view.FooterViewHolder;

/**
 * Created by yanglei on 2016/10/17.
 */
public class GoodsAdapter extends Adapter {
    List<NewGoodsBean> mList;
    Context mcontext;
    boolean isMore;
    int soryBy=I.SORT_BY_ADDTIME_DESC;

    public GoodsAdapter(List<NewGoodsBean> list, Context context) {
        this.mcontext = context;
        this.mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void setSortBy(int soryBy) {
        this.soryBy = soryBy;
        sortBy();
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
            holder = new GoodsViewHolder(View.inflate(mcontext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh= (FooterViewHolder) holder;
            vh.tvFooter.setText(getFooterString());
        } else {
            GoodsViewHolder vh= (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mcontext, vh.mivGoodsThumd, goods.getGoodsThumb());
            vh.mtvGoodsName.setText(goods.getGoodsName());
            vh.mtvGoodsPrice.setText(goods.getCurrencyPrice());
            vh.mlayoutGoods.setTag(goods.getGoodsId());
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

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


     class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumd)
        ImageView mivGoodsThumd;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mtvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mlayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId=(int)mlayoutGoods.getTag();
            MFGT.gotoGoodsDatailActivity(mcontext,goodsId);
        }
    }

    private void sortBy() {
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right ) {
                int result=0;
                switch (soryBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (Long.valueOf(left.getAddTime()) -Long.valueOf( right.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (Long.valueOf(right.getAddTime()) -Long.valueOf( left.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(left.getCurrencyPrice()) - getPrice(right.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result=getPrice(right.getCurrencyPrice()) - getPrice(left.getCurrencyPrice());
                        break;
                }
                return result;
            }

            private int getPrice(String price) {
                price=price.substring(price.indexOf("￥")+1);
                return Integer.valueOf(price);
            }
        });
    }
}
