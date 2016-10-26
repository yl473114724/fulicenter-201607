package cn.ucai.xm_fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.Result;
import cn.ucai.xm_fulicenter.bean.User;
import cn.ucai.xm_fulicenter.dao.UserDao;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.utils.ResultUtils;


/**
 * Created by xiaomiao on 2016/10/24.
 */

public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getCanonicalName();

    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    MainActivity mContext;
    User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLogin(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            syncUserInfo();
        }
    }

    @OnClick({R.id.tv_center_settings,R.id.center_user_info})
    public void gotoSettings() {
        MFGT.gotoSettings(mContext);
    }

    private void syncUserInfo() {
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
