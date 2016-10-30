package cn.ucai.xm_fulicenter.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.CartBean;
import cn.ucai.xm_fulicenter.bean.GoodsDetailsBean;
import cn.ucai.xm_fulicenter.bean.MessageBean;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.utils.MFGT;

public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mlist;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        this.mlist = list;
    }




    @Override

    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean CartBean = mlist.get(position);
        GoodsDetailsBean goods = CartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.mivCartThumb, goods.getGoodsThumb());
            holder.mtvCartGoodName.setText(goods.getGoodsName());
            holder.mtvCartPrice.setText(goods.getShopPrice());
        }
        holder.mtvCartCount.setText("(" + CartBean.getCount() + ")");
        holder.mcbCatrSelected.setChecked(CartBean.isChecked());
        holder.mcbCatrSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
        holder.mivCartAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }


    public void initData(ArrayList<CartBean> list) {
        mlist = list;
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
        @OnClick({R.id.iv_cart_thumb,R.id.tv_cart_good_name,R.id.tv_cart_price})
        public void gotoDetail(){
            final int position = (int) mivCartAdd.getTag();
            CartBean cart = mlist.get(position);
            MFGT.gotoGoodsDatailActivity(mContext,cart.getGoodsId());
        }

        @OnClick(R.id.iv_cart_add)
        public void addCart(){
            final int position = (int) mivCartAdd.getTag();
            CartBean cart = mlist.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        mlist.get(position).setCount(mlist.get(position).getCount()+1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                        mtvCartCount.setText("("+(mlist.get(position).getCount())+")");
                    }
                }
                @Override
                public void onError(String error) {

                }
            });
        }
        @OnClick(R.id.iv_cart_del)
        public void delCart(){
            final int position = (int) mivCartAdd.getTag();
            CartBean cart = mlist.get(position);
            if (cart.getCount() > 1) {

                NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mlist.get(position).setCount(mlist.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            mtvCartCount.setText("(" + (mlist.get(position).getCount()) + ")");
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            mlist.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            notifyDataSetChanged();
                             }
                    }
                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }
}
