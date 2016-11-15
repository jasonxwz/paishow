package com.example.asus.paishow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.LoadDialog;
import com.example.asus.paishow.utils.MD5Util;
import com.example.asus.paishow.utils.MToast;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.widiget.ClearWriteEditText;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    protected Context mContext;
    private String phoneString;
    private String psdString;
    private ClearWriteEditText mPhoneEdit;
    private ClearWriteEditText mPsdEdit;
    private Button mLogin;
    private TextView mRegister;
    private TextView mForgetPassword;
    private ImageView mLoginBackgroundPho;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Void_.isNetworkAvailable(this) == false){
            Toast.makeText(this, "网络不可用",Toast.LENGTH_LONG).show();
        }

        initView();

    }

    private void initView() {

        mPhoneEdit = (ClearWriteEditText) findViewById(R.id.de_login_phone);
        mPsdEdit = (ClearWriteEditText) findViewById(R.id.de_login_password);
        mLogin = (Button) findViewById(R.id.de_login_sign);
        mRegister = (TextView) findViewById(R.id.de_login_register);
        mForgetPassword = (TextView) findViewById(R.id.de_login_forgot);

        findViewById(R.id.root_login).setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);

        mLoginBackgroundPho = (ImageView) findViewById(R.id.de_login_backgroud);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate_anim);
                mLoginBackgroundPho.startAnimation(animation);
            }
        }, 100);

        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 11){
                    phoneString = mPhoneEdit.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestParams rp = new RequestParams("http://10.40.5.57:8080/MyPaishow/checkphone");
                            rp.addBodyParameter("phoneNum", phoneString);
                            x.http().post(rp, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result.equals("false")){
                                        Toast.makeText(LoginActivity.this, "该手机号未注册"+result, Toast.LENGTH_SHORT).show();

                                        //getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_login));
                                    }else {
                                        Void_.hideKeyBoard(LoginActivity.this, mPhoneEdit);
//                                        Toast.makeText(RegisterActivity.this, "该手机号已被注册" + result, Toast.LENGTH_SHORT).show();
//                                        getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
                                    }

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {

                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    }).start();
                }else {
                    //getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.de_login_sign:
                boolean flag = false;
                flag = Void_.isNetworkAvailable(this);
                if (flag == true) {

                    phoneString = mPhoneEdit.getText().toString().trim();
                    psdString = mPsdEdit.getText().toString().trim();

                    if (TextUtils.isEmpty(phoneString)) {
                        MToast.shortToast(this, "手机号不能为空");
                        mPhoneEdit.setShakeAnimation();
                        return;
                    }

//                if (!AMUtils.isMobile(phoneString)) {
//                    MToast.shortToast(mContext, R.string.Illegal_phone_number);
//                    mPhoneEdit.setShakeAnimation();
//                    return;
//                }

                    if (TextUtils.isEmpty(psdString)) {
                        MToast.shortToast(this, "密码不能为空");
                        mPsdEdit.setShakeAnimation();
                        return;
                    }
                    if (psdString.contains(" ")) {
                        MToast.shortToast(this, "密码不能包含空格");
                        mPsdEdit.setShakeAnimation();
                        return;
                    }

                    LoadDialog.show(this);


//                editor.putBoolean("exit", false);
//                editor.apply();
                    RequestParams requestParams = new RequestParams("http://10.40.5.57:8080/MyPaishow/loginservlet");
                    requestParams.addQueryStringParameter("phoneNum", phoneString);
                    requestParams.addQueryStringParameter("psd", MD5Util.getMd5Value(psdString));
                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            User user = new User();
                            System.out.println(result);
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            if (user.getUserId() != 0) {
                                LoadDialog.dismiss(mContext);
                                System.out.println(user.getUserName());
                                System.out.println(user.getToken());
                                System.out.println(user.getUserTouxiang());
                                RongIM.connect(user.getToken(), new RongIMClient.ConnectCallback() {

                                    @Override
                                    public void onTokenIncorrect() {
                                        System.out.println("连接出了问题");
                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        System.out.println("连接成功");

                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                        System.out.println("连接失败");

                                    }
                                });
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("phoneNum", phoneString);
                                intent.putExtra("userId", user.getUserId());
                                intent.putExtra("userName", user.getUserName());
                                intent.putExtra("userImg", user.getUserTouxiang());
                                startActivity(intent);
                                finish();
                            } else {
                                LoadDialog.dismiss(mContext);
                                Toast.makeText(LoginActivity.this, "手机号与密码不匹配", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else {
                    Toast.makeText(this, "网络不可用",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.de_login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), 1);
                break;
            case R.id.de_login_forgot:
                startActivityForResult(new Intent(this, ForgetPsdActivity.class), 2);
                break;
            case R.id.root_login:
                Void_.hideKeyBoard(LoginActivity.this, v);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
