package com.example.asus.paishow;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.paishow.utils.MD5Util;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.utils._String;
import com.example.asus.paishow.widiget.ClearWriteEditText;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPsdActivity extends Activity implements View.OnClickListener {


    private String APPKEY = _String.appkey;
    private String APPSECRETE = _String.appsecrete;
    private ClearWriteEditText mPhoneEt, mCodeEt, mPassword1Et, mPassword2Et;
    private Button getCode, mOK;
    private String phoneNum, mCodeToken;
    private String mPsd1, mPsd2;
    private LinearLayout ll_back;
    private int i = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd);
        initView();
    }

    private void initView() {
        mPhoneEt = (ClearWriteEditText) findViewById(R.id.forget_phone);
        mCodeEt = (ClearWriteEditText) findViewById(R.id.forget_code);
        mPassword1Et = (ClearWriteEditText) findViewById(R.id.forget_password1);
        mPassword2Et = (ClearWriteEditText) findViewById(R.id.forget_password2);
        getCode = (Button) findViewById(R.id.forget_getcode);
        mOK = (Button) findViewById(R.id.forget_button);
        ll_back = (LinearLayout) findViewById(R.id.forget_back);

        getCode.setOnClickListener(this);
        mOK.setOnClickListener(this);
        ll_back.setOnClickListener(this);



        editTextListener();

        SMSSDK.initSDK(this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
//                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }

    private void editTextListener() {
        mPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    phoneNum = mPhoneEt.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestParams rp = new RequestParams("http://10.40.5.57:8080/MyPaishow/checkphone");
                            rp.addBodyParameter("phoneNum", phoneNum);
                            x.http().post(rp, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result.equals("false")) {
                                        Toast.makeText(ForgetPsdActivity.this, "该手机号未注册" + result, Toast.LENGTH_SHORT).show();
                                        getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
                                    } else {
                                        getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_login));
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
                } else {
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
            case R.id.forget_getcode:
                // 1. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNum);

                // 2. 把按钮变成不可点击，并且显示倒计时（正在获取）
                getCode.setClickable(false);
                getCode.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.forget_button:
                mPsd1 = mPassword1Et.getText().toString().trim();
                mPsd2 = mPassword2Et.getText().toString().trim();
                phoneNum = mPhoneEt.getText().toString().trim();
                if (mPsd1.equals(mPsd2)){
                    RequestParams params = new RequestParams("http://10.40.5.57:8080/MyPaishow/updatepsdservlet");
                    params.addQueryStringParameter("phoneNum", phoneNum);
                    params.addQueryStringParameter("psd", MD5Util.getMd5Value(mPsd1));
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.equals("true")){
                                Toast.makeText(ForgetPsdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
                }else {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forget_back:
                Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                getCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                getCode.setText("获取验证码");
                getCode.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
//
//                        psd = psdEt.getText().toString();
//                        userName = userNameEt.getText().toString();
//                        phoneNum = userNameEt.getText().toString();
//
//                        if (psd.length() >= 6) {
//
//                            RequestParams params = new RequestParams("http://10.40.5.57:8080/paishow/registerservlet");
//
//                            params.addQueryStringParameter("nickName", userName);
//                            params.addQueryStringParameter("phoneNum", phoneNum);
//                            params.addQueryStringParameter("psd", MD5Util.getMd5Value(psd));
//
//                            x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
//                                @Override
//                                public void onSuccess(String result) {
//                                    Toast.makeText(getApplicationContext(), "注册成功！",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//
//                                @Override
//                                public void onError(Throwable ex, boolean isOnCallback) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(CancelledException cex) {
//
//                                }
//
//                                @Override
//                                public void onFinished() {
//
//                                }
//                            });
//
//
//                        }
//
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        Toast.makeText(getApplicationContext(), "正在获取验证码",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        ((Throwable) data).printStackTrace();
//                    }
//                }
                    }
                }
            }
        }
    };
}

