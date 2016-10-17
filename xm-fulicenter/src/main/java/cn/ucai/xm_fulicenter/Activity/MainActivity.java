package cn.ucai.xm_fulicenter.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.fragment.NewGoodsFragment;
import cn.ucai.xm_fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {


    int index;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        initView();
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment=new NewGoodsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initView() {
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
                index = 4;
                break;
        }
        setRadioButtonsStatus();
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
}
