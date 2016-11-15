package com.example.ssnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

public class XiuGaiMiMaActivity extends AppCompatActivity {
    private  User user;
    private ImageView iv_xiugaimima_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_mi_ma);
        user=(User)getIntent().getSerializableExtra("user");
        iv_xiugaimima_fanhui = ((ImageView) findViewById(R.id.iv_xiugaimima_fanhui));















        iv_xiugaimima_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
