package com.example.ssnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

public class CaoGaoXiangActivity extends AppCompatActivity {

    private ImageView iv_caogaoxiang_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cao_gao_xiang);
        User user=(User)getIntent().getSerializableExtra("user");
        iv_caogaoxiang_fanhui = ((ImageView) findViewById(R.id.iv_caogaoxiang_fanhui));





        iv_caogaoxiang_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
