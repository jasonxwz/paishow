package com.example.ssnn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.paishow.LoginActivity;
import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.DataCleanManager;

public class SheZhiActivity extends AppCompatActivity {

    private ImageView iv_shezhi_fanhui;
    private RelativeLayout rl_qingchuhuancun;
    private RelativeLayout rl_gengxinbanben;
    private TextView tv_qingchuhuanchun_daxiao;
    private TextView tv_gengxinbanben_banbenhao;
    private TextView tv_tuichudenglu;
    private RelativeLayout rl_xiugaimima;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
        user=(User)getIntent().getSerializableExtra("user");
        iv_shezhi_fanhui = ((ImageView) findViewById(R.id.iv_shezhi_fanhui));
        rl_qingchuhuancun = ((RelativeLayout) findViewById(R.id.rl_qingchuhuancun));
        rl_gengxinbanben = ((RelativeLayout) findViewById(R.id.rl_gengxinbanben));
        tv_qingchuhuanchun_daxiao = ((TextView) findViewById(R.id.tv_qingchuhuanchun_daxiao));
        tv_gengxinbanben_banbenhao = ((TextView) findViewById(R.id.tv_gengxinbanben_banbenhao));
        tv_tuichudenglu = ((TextView) findViewById(R.id.tv_tuichudenglu));
        rl_xiugaimima = ((RelativeLayout) findViewById(R.id.rl_xiugaimima));

        final Context context = getApplicationContext();

        //查询缓存
        try {
            String huancun = DataCleanManager.getTotalCacheSize(context);
            System.out.println("缓存**************************"+huancun);
            tv_qingchuhuanchun_daxiao.setText(huancun);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //清除缓存
        rl_qingchuhuancun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCleanManager.clearAllCache(context);

                try {
                    String huancun = DataCleanManager.getTotalCacheSize(context);
                    System.out.println("缓存**************************"+huancun);
                    tv_qingchuhuanchun_daxiao.setText(huancun);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //修改密码
        rl_xiugaimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),XiuGaiMiMaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //退出登录
        tv_tuichudenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                Bundle bundle = new Bundle();

                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });



        iv_shezhi_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

