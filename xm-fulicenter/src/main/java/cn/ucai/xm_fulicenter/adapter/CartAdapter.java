package cn.ucai.xm_fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.CartBean;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context mContext;

    public CartAdapter(Context mContext, ArrayList<CartBean> mlist) {
        this.mlist = new ArrayList<>();
        this.mlist.addAll(mlist);
        this.mContext = mContext;
    }


    ArrayList<CartBean> mlist;

    @Override

    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean CartBean = mlist.get(position);
//        ImageLoader.downloadImg(mContext,holder.mivBoutiqueImg,CartBean.getImageurl());
//        holder.mtvBoutiqueTitle.setText(CartBean.getTitle());
//        holder.mtvBoutiqueName.setText(CartBean.getName());
//        holder.mtvBoutiqueDescription.setText(CartBean.getDescription());
//        holder.mlayoutBoutiqueItem.setTag(CartBean);
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }


    public void initData(ArrayList<CartBean> list) {
        if (mlist != null) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }


    class CartViewHolder extends ViewHolder{
        @BindView(R.id.cb_catr_selected)
        CheckBox mcbCatrSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mivCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mtvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mivCartAdd;
        @BindView(R.id.tv_cart_count)
        TextView mtvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mivCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mtvCartPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
