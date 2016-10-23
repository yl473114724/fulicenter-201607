package cn.ucai.xm_fulicenter.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.Result;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.CommonUtils;
import cn.ucai.xm_fulicenter.utils.L;
import cn.ucai.xm_fulicenter.utils.MFGT;
import cn.ucai.xm_fulicenter.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.nick)
    EditText mnick;
    @BindView(R.id.password)
    EditText mpassword;
    @BindView(R.id.iv_password2)
    ImageView mivPassword2;
    @BindView(R.id.confirm_password)
    EditText mconfirmPassword;
    @BindView(R.id.btn_register)
    Button mbtnRegister;

    String username ;
    String nickname ;
    String password ;
    RegisterActivity mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mcontext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账户注册");

    }

    @OnClick(R.id.btn_register)
    public void checkedInput() {
        username = musername.getText().toString().trim();
        nickname = mnick.getText().toString().trim();
        password = mpassword.getText().toString().trim();
        String confirmPwd = mconfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            musername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(nickname)) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            mnick.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            mpassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            mconfirmPassword.requestFocus();
            return;
        } else if (!password.equals(confirmPwd)) {
            CommonUtils.showShortToast(R.string.two_input_password);
            mconfirmPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mcontext);
        pd.setMessage(getResources().getString(R.string.register));
        pd.show();
        NetDao.register(mcontext, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success);
                        MFGT.finish(mcontext);
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        musername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(R.string.register_fail);
                L.e(TAG, "register error=" + error);
            }
        });
    }
}
