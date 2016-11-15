package com.example.ssnn.xiugaiziliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

import java.util.Timer;
import java.util.TimerTask;

public class XiuGaiJianJieActivity extends AppCompatActivity {
    private User user;
    private ImageView iv_xiugaijianjie_fanhui;
    private TextView tv_jianjie_baocun;
    private EditText et_jianjie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_jian_jie);
        final Intent intent = this.getIntent();
        user=(User)getIntent().getSerializableExtra("user");
        iv_xiugaijianjie_fanhui = ((ImageView) findViewById(R.id.iv_xiugaijianjie_fanhui));
        tv_jianjie_baocun = ((TextView) findViewById(R.id.tv_jianjie_baocun));
        et_jianjie = ((EditText) findViewById(R.id.et_jianjie));
        String userJianjei = user.getUserJianjie();
        et_jianjie.setText(userJianjei);
        et_jianjie.setFocusable(true);
        et_jianjie.setFocusableInTouchMode(true);
        et_jianjie.requestFocus();
        et_jianjie.setSelection(userJianjei.length());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { //让软键盘延时弹出，以更好的加载Activity

            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)et_jianjie.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(et_jianjie, 0);
            }

        }, 200);
        tv_jianjie_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserJianjie(et_jianjie.getText().toString().trim());
                Bundle bundle = intent.getExtras();
                bundle.putSerializable("user", user);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                XiuGaiJianJieActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                XiuGaiJianJieActivity.this.finish();
            }
        });

        iv_xiugaijianjie_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
