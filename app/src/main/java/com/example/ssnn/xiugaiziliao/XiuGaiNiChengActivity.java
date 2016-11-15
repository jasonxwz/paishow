package com.example.ssnn.xiugaiziliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


public class XiuGaiNiChengActivity extends AppCompatActivity {
    private User user;
    private ImageView iv_xiugainicheng_fanhui;
    private TextView tv_nicheng_baocun;
    private EditText et_nicheng;
    private TextView tv_jianyiwenzi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_ni_cheng);
        final Intent intent = this.getIntent();
        user=(User)getIntent().getSerializableExtra("user");
        iv_xiugainicheng_fanhui = ((ImageView) findViewById(R.id.iv_xiugainicheng_fanhui));
        tv_nicheng_baocun = ((TextView) findViewById(R.id.tv_nicheng_baocun));
        et_nicheng = ((EditText) findViewById(R.id.et_nicheng));
        tv_jianyiwenzi = ((TextView) findViewById(R.id.tv_jianyiwenzi));

        String userName  = user.getUserName();
        et_nicheng.setText(userName);
        et_nicheng.setFocusable(true);
        et_nicheng.setFocusableInTouchMode(true);
        et_nicheng.requestFocus();
        et_nicheng.setSelection(userName.length());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { //让软键盘延时弹出，以更好的加载Activity

            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)et_nicheng.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(et_nicheng, 0);
            }

        }, 200);


        tv_nicheng_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((et_nicheng.getText().toString()).equals("")){
                    tv_jianyiwenzi.setText("  还没输入昵称呢～");
                    tv_jianyiwenzi.setTextColor(Color.parseColor("#ea8010"));
                }else if ((et_nicheng.getText().toString()).equals(user.getUserName())){
                    tv_jianyiwenzi.setText("  和原用户名一样哦～");
                    tv_jianyiwenzi.setTextColor(Color.parseColor("#ea8010"));
                }else {
                    user.setUserName(et_nicheng.getText().toString().trim());
                    Bundle bundle = intent.getExtras();
                    bundle.putSerializable("user", user);//添加要返回给页面1的数据
                    intent.putExtras(bundle);
                    XiuGaiNiChengActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                    XiuGaiNiChengActivity.this.finish();
                }


            }
        });




        iv_xiugainicheng_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
