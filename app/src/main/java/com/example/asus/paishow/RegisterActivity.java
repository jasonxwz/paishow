package com.example.asus.paishow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.utils.AMUtils;
import com.example.asus.paishow.utils.MD5Util;
import com.example.asus.paishow.utils._String;
import com.example.asus.paishow.widiget.ClearWriteEditText;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends Activity implements View.OnClickListener{
    private String APPKEY = _String.appkey;
    private String APPSECRETE = _String.appsecrete;

    int i = 30;
    private ClearWriteEditText userNameEt;
    private ClearWriteEditText phoneNumEt;
    private ClearWriteEditText psdEt;
    private ClearWriteEditText codeEt;
    private Button getCode;
    private Button register;
    private TextView forgetPsd;
    private TextView login;

    String userName;
    String psd;
    String phoneNum;
    private ImageView mRegisterBackgroundPho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        userNameEt = (ClearWriteEditText) findViewById(R.id.reg_userName);
        phoneNumEt = (ClearWriteEditText) findViewById(R.id.reg_phoNum);
        psdEt = (ClearWriteEditText) findViewById(R.id.reg_psd);
        codeEt = (ClearWriteEditText) findViewById(R.id.reg_code);
        getCode = (Button) findViewById(R.id.reg_getCode);
        register = (Button) findViewById(R.id.de_register_sign);
        forgetPsd = (TextView) findViewById(R.id.de_register_forgot);
        login = (TextView) findViewById(R.id.de_register_login);

        findViewById(R.id.root_register).setOnClickListener(this);
        getCode.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        forgetPsd.setOnClickListener(this);

        mRegisterBackgroundPho = (ImageView) findViewById(R.id.de_register_backgroud);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.translate_anim);
                mRegisterBackgroundPho.startAnimation(animation);
            }
        }, 100);
        editTextListener();



        // 启动短信验证sdk
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
        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 11){
                    phoneNum = phoneNumEt.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestParams rp = new RequestParams("http://10.40.5.57:8080/MyPaishow/checkphone");
                            rp.addBodyParameter("phoneNum", phoneNum);
                            x.http().post(rp, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result.equals("false")){
                                        Toast.makeText(RegisterActivity.this, "该手机号可用"+result, Toast.LENGTH_SHORT).show();
                                        hideKeyBoard(RegisterActivity.this, phoneNumEt);
                                        getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_login));
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "该手机号已被注册" + result, Toast.LENGTH_SHORT).show();
                                        getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
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
                    getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        psdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5) {
                    register.setClickable(true);
                    register.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_login));
                } else {
                    register.setClickable(false);
                    register.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_reg_gray));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        phoneNum = phoneNumEt.getText().toString();
        switch (v.getId()) {
//            case R.id.reg_getCode:
//                // 1. 通过规则判断手机号
//                if (!judgePhoneNums(phoneNum)) {
//                    return;
//                } // 2. 通过sdk发送短信验证
//                SMSSDK.getVerificationCode("86", phoneNum);
//
//                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
//                getCode.setClickable(false);
//                getCode.setText("重新发送(" + i + ")");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (; i > 0; i--) {
//                            handler.sendEmptyMessage(-9);
//                            if (i <= 0) {
//                                break;
//                            }
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        handler.sendEmptyMessage(-8);
//                    }
//                }).start();
//                break;

            case R.id.de_register_sign:
//                //将收到的验证码和手机号提交再次核对
//                SMSSDK.submitVerificationCode("86", phoneNum, codeEt
//                        .getText().toString());
//                //createProgressBar();



                psd = psdEt.getText().toString();
                userName = userNameEt.getText().toString();
                phoneNum = phoneNumEt.getText().toString();

                RequestParams params = new RequestParams("http://10.40.5.57:8080/MyPaishow/registerservlet");

                params.addQueryStringParameter("nickName", userName);
                params.addQueryStringParameter("phoneNum", phoneNum);
                params.addQueryStringParameter("psd", MD5Util.getMd5Value(psd));

                x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("true")){
                            Toast.makeText(getApplicationContext(), "注册成功！" + result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "注册失败，请重新注册" + result, Toast.LENGTH_SHORT).show();
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

                break;

            case R.id.de_register_login:
                startActivityForResult(new Intent(this, LoginActivity.class), 1);
                break;
            case R.id.de_register_forgot:
                startActivityForResult(new Intent(this, ForgetPsdActivity.class), 2);
                break;
            case R.id.root_register:
                hideKeyBoard(RegisterActivity.this, v);
                break;
        }
    }

    /**
     *
     */
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

                        psd = psdEt.getText().toString();
                        userName = userNameEt.getText().toString();
                        phoneNum = userNameEt.getText().toString();

                        if (psd.length() >= 6) {

                            RequestParams params = new RequestParams("http://10.40.5.57:8080/paishow/registerservlet");

                            params.addQueryStringParameter("nickName", userName);
                            params.addQueryStringParameter("phoneNum", phoneNum);
                            params.addQueryStringParameter("psd", MD5Util.getMd5Value(psd));

                            x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(getApplicationContext(), "注册成功！",
                                            Toast.LENGTH_SHORT).show();

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

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && AMUtils.isMobile(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isMatchLength(String phoneNums, int length) {
        if (phoneNums.isEmpty()) {
            return false;
        } else {
            return phoneNums.length() == length ? true : false;
        }
    }

    public void hideKeyBoard(Context context, View view){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
