package com.example.ssnn;


import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.Fragment;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;
import com.example.ssnn.wodefragments.WoDeDongTaiFragment;
import com.example.ssnn.wodefragments.XiangCeFragment;
import com.example.ssnn.wodefragments.YuWoXiangGuanFragment;

import org.xutils.x;

public class GeRenZhuYeActivity extends AppCompatActivity {

    private ImageView iv_zhuye_touxiang;
    private ImageView iv_gerenzhuye_fanhui;
    private String touxiang_url;
    private String beijing_url;
    private ImageView iv_zhuye_beijing;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private RadioGroup rg_dxy;
    private TextView tv_wodedongtai;
    private TextView tv_xiangce;
    private TextView tv_yuwoxiangguan;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private User user;
    private FrameLayout fl_gerenzhuye;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren_zhu_ye);
        user=(User)getIntent().getSerializableExtra("user");
        touxiang_url = NetUtil.url_zhuwei + user.getUserTouxiang();
        beijing_url=NetUtil.url_zhuwei+user.getUserBackground();
        iv_zhuye_touxiang = ((ImageView) findViewById(R.id.iv_zhuye_touxiang));
        iv_gerenzhuye_fanhui = ((ImageView) findViewById(R.id.iv_gerenzhuye_fanhui));
        iv_zhuye_beijing = ((ImageView) findViewById(R.id.iv_zhuye_beijing));
        tv_fensi = ((TextView) findViewById(R.id.tv_fensi));
        tv_guanzhu = ((TextView) findViewById(R.id.tv_guanzhu));
        rg_dxy = ((RadioGroup) findViewById(R.id.rg_dxy));
        tv_wodedongtai = ((TextView) findViewById(R.id.tv_wodedongtai));
        tv_xiangce = ((TextView) findViewById(R.id.tv_xiangce));
        tv_yuwoxiangguan = ((TextView) findViewById(R.id.tv_yuwoxiangguan));
        fl_gerenzhuye = ((FrameLayout) findViewById(R.id.fl_gerenzhuye));
        tv_wodedongtai.setSelected(true);
        xUtilsImageUtils.display(iv_zhuye_touxiang,touxiang_url, true);
        x.image().bind(iv_zhuye_beijing,beijing_url);
        tv_fensi.setText("粉丝："+user.getUserFensi());
        tv_guanzhu.setText("关注："+user.getUserGuanzhu());
        /**
         * 选择Fragment
         */
       initdata();
        fl_gerenzhuye.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

            }
        });




        iv_gerenzhuye_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),MineFragment.class);
                finish();
            }
        });



    }
   private void initdata() {
        switchFragement(new WoDeDongTaiFragment());
        tv_wodedongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wodedongtai.setSelected(true);
                tv_xiangce.setSelected(false);
                tv_yuwoxiangguan.setSelected(false);
                switchFragement(new WoDeDongTaiFragment());
            }
        });
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wodedongtai.setSelected(false);
                tv_xiangce.setSelected(true);
                tv_yuwoxiangguan.setSelected(false);

                XiangCeFragment xiangceFragment = new XiangCeFragment();
                Bundle bundle = new Bundle();
                Integer strValue = user.getUserId();
                bundle.putString("str", strValue+"");
                xiangceFragment.setArguments(bundle);
                //如果transaction  commit（）过  那么我们要重新得到transaction
                manager = getFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_gerenzhuye, xiangceFragment);
                transaction.commit();

                switchFragement(xiangceFragment);
            }
        });
       tv_yuwoxiangguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wodedongtai.setSelected(false);
                tv_xiangce.setSelected(false);
                tv_yuwoxiangguan.setSelected(true);
                switchFragement(new YuWoXiangGuanFragment());
            }
        });



    }

   private void switchFragement(Fragment fragement) {
        this.getFragmentManager().beginTransaction().replace(R.id.fl_gerenzhuye,fragement).commit();
    }
}
