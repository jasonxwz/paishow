package com.example.ssnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

public class DengJiChengHaoActivity extends AppCompatActivity {

    private TextView tv_rank;
    private ImageView iv_dengjichenghao_fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_ji_cheng_hao);
        User user=(User)getIntent().getSerializableExtra("user");
        Integer userRank = user.getUserRank();
        tv_rank = ((TextView) findViewById(R.id.tv_rank));
        iv_dengjichenghao_fanhui = ((ImageView) findViewById(R.id.iv_dengjichenghao_fanhui));

        tv_rank.setText(" 当前等级为：Lv"+userRank);
        iv_dengjichenghao_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),MineFragment.class);
                finish();
            }
        });

    }

}
