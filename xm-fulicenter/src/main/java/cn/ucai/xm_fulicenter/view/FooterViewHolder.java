package cn.ucai.xm_fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.R;

/**
 * Created by yanglei on 2016/10/19.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvFooter)
    public TextView tvFooter;

   public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
