package cn.ucai.xm_fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.BoutiqueBean;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.view.FooterViewHolder;

public class BoutiqueAdapter extends Adapter <BoutiqueAdapter.BoutiqueViewHolder>{
    Context mContext;

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mlist) {
        this.mlist = new ArrayList<>();
        this.mlist.addAll(mlist);
        this.mContext = mContext;
    }


    ArrayList<BoutiqueBean> mlist;
    @Override

    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder holder = new BoutiqueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
            BoutiqueBean boutiqueBean = mlist.get(position);
            ImageLoader.downloadImg(mContext,((BoutiqueViewHolder)holder).mivBoutiqueImg,boutiqueBean.getImageurl());
            ((BoutiqueViewHolder) holder).mtvBoutiqueTitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder) holder).mtvBoutiqueName.setText(boutiqueBean.getName());
            ((BoutiqueViewHolder) holder).mtvBoutiqueDescription.setText(boutiqueBean.getDescription());
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size():0;
    }


    public void initData(ArrayList<BoutiqueBean> list) {
        if (mlist != null) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }


    class BoutiqueViewHolder extends ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mtvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mtvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mtvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mlayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
