package cn.ucai.xm_fulicenter.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.FuLiCenterApplication;
import cn.ucai.xm_fulicenter.R;

import cn.ucai.xm_fulicenter.fragment.BoutiqueFragment;
import cn.ucai.xm_fulicenter.fragment.CategoryFragment;
import cn.ucai.xm_fulicenter.fragment.NewGoodsFragment;
import cn.ucai.xm_fulicenter.fragment.PersonalCenterFragment;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {


    int index;
    int currentindex;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mpersonalCenterFragment;

    @BindView(R.id.layout_new_good)
    RadioButton mlayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mlayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mlayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mlayoutCart;
    @BindView(R.id.tvCartHint)
    TextView mtvCartHint;
    @BindView(R.id.layout_personal_center)
    RadioButton mlayoutPersonalCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mpersonalCenterFragment = new PersonalCenterFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[4] = mpersonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    @Override
    protected  void initView() {
        rbs = new RadioButton[5];
        rbs[0] = mlayoutNewGood;
        rbs[1] = mlayoutBoutique;
        rbs[2] = mlayoutCategory;
        rbs[3] = mlayoutCart;
        rbs[4] = mlayoutPersonalCenter;

    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);

                } else {
                    index = 4;
                }
                break;
        }
        setFragment();

    }

    private void setFragment() {
        if (index != currentindex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentindex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment_container, mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonsStatus();
        currentindex = index;
    }

    private void setRadioButtonsStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
    public void onBackPressed( ) {
        finish();
    }
}
