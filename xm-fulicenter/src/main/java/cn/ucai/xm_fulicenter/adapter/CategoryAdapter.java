package cn.ucai.xm_fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.CategoryChildBean;
import cn.ucai.xm_fulicenter.bean.CategoryGroupBean;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.utils.MFGT;

/**
 * Created by yanglei on 2016/10/20.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> GroupList, ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        this.mContext = mContext;
        this.mGroupList = new ArrayList<>();
        this.mGroupList.addAll(GroupList);
        this.mChildList = new ArrayList<>();
        this.mChildList.addAll(ChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        GroupViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_actegory_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            view.getTag();
            holder = (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.mivGroupThume, group.getImageUrl());
            holder.mtvGroupName.setText(group.getName());
            holder.mivIndicator.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_actegory_child, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder= (ChildViewHolder) view.getTag();

        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageLoader.downloadImg(mContext, holder.mivCategoryChildThumb, child.getImageUrl());
            holder.mivCategoryChildName.setText(child.getName());
            holder.mlayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<CategoryChildBean> list=mChildList.get(groupPosition);
                    String groupName = mGroupList.get(groupPosition).getName();
                    MFGT.gotoCategoryChildActivity(mContext, child.getId(),groupName,list);
                }
            });
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> GroupList, ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        if (mGroupList != null) {
            mGroupList.clear();
        }
        mGroupList.addAll(GroupList);
        if (mChildList != null) {
            mChildList.clear();
        }
        mChildList.addAll(ChildList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        @BindView(R.id.iv_group_thume)
        ImageView mivGroupThume;
        @BindView(R.id.tv_group_name)
        TextView mtvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView mivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView mivCategoryChildThumb;
        @BindView(R.id.iv_category_child_name)
        TextView mivCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mlayoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
