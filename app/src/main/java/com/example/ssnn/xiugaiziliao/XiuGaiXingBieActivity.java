package com.example.ssnn.xiugaiziliao;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

public class XiuGaiXingBieActivity extends AppCompatActivity {

    private ImageView iv_xiugaixingbie_fanhui;
    private TextView tv_xingbie_baocun;
    private RelativeLayout rl_nv;
    private RelativeLayout rl_nan;
    private TextView tv_nv;
    private TextView tv_nan;
    private User user;
    private ImageView iv_nv;
    private ImageView iv_nan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_xing_bie);
        final Intent intent = this.getIntent();
        user=(User)getIntent().getSerializableExtra("user");
        iv_xiugaixingbie_fanhui = ((ImageView) findViewById(R.id.iv_xiugaixingbie_fanhui));
        tv_xingbie_baocun = ((TextView) findViewById(R.id.tv_xingbie_baocun));
        rl_nv = ((RelativeLayout) findViewById(R.id.rl_nv));
        rl_nan = ((RelativeLayout) findViewById(R.id.rl_nan));
        tv_nv = ((TextView) findViewById(R.id.tv_nv));
        tv_nan = ((TextView) findViewById(R.id.tv_nan));
        iv_nv = ((ImageView) findViewById(R.id.iv_nv));
        iv_nan = ((ImageView) findViewById(R.id.iv_nan));

        String sexflag = user.getUserSex();
        if (sexflag.equals("0")){
            iv_nan.setSelected(false);
            iv_nv.setSelected(true);
        }else {

            iv_nv.setSelected(false);
            iv_nan.setSelected(true);
        }
        rl_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserSex("0");
                iv_nan.setSelected(false);
                iv_nv.setSelected(true);
            }
        });
        rl_nan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserSex("1");
                iv_nv.setSelected(false);
                iv_nan.setSelected(true);
            }
        });

        tv_xingbie_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = intent.getExtras();
                bundle.putSerializable("user", user);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                XiuGaiXingBieActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                XiuGaiXingBieActivity.this.finish();
            }
        });
        iv_xiugaixingbie_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
