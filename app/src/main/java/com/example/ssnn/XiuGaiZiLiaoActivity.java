package com.example.ssnn;

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
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;
import com.example.ssnn.xiugaiziliao.XiuGaiBeiJingActivity;
import com.example.ssnn.xiugaiziliao.XiuGaiJianJieActivity;
import com.example.ssnn.xiugaiziliao.XiuGaiNiChengActivity;
import com.example.ssnn.xiugaiziliao.XiuGaiShengRiActivity;
import com.example.ssnn.xiugaiziliao.XiuGaiTouXiangActivity;
import com.example.ssnn.xiugaiziliao.XiuGaiXingBieActivity;

import org.xutils.x;

public class XiuGaiZiLiaoActivity extends AppCompatActivity {
    public final int TOUXIANG=0;
    public final int NICHENG=1;
    public final int XINGBIE=2;
    public final int SHENGRI=3;
    public final int JIANJIE=4;
    public final int BEIJING=5;
    public final int SHOUJIHAO=6;
    public final int SUOZAIDI=7;


    private User user;
    private ImageView iv_xiugaiziliao_fanhui;
    private ImageView iv_xiugaitouxiang;
    private TextView tv_xiugai_nicheng;
    private TextView tv_xiugai_xingbie;
    private TextView tv_xiugai_shengri;
    private TextView tv_xiugai_jianjie;
    private RelativeLayout rl_xiugaitouxiang;
    private RelativeLayout rl_xiugainicheng;
    private RelativeLayout rl_xiugaixingbie;
    private RelativeLayout rl_xiugaishengri;
    private RelativeLayout rl_xiugaijianjie;
    private RelativeLayout rl_xiugaibeijingtupian;
    private RelativeLayout rl_xiugaishoujihao;
    private RelativeLayout rl_xiugaisuozaidi;
    private TextView tv_xiugai_shoujihao;
    private TextView tv_xiugai_suozaidi;
    private ImageView iv_geren_beijing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_zi_liao);
        //final Intent intent = this.getIntent();
        user=(User)getIntent().getSerializableExtra("user");
        rl_xiugaitouxiang = ((RelativeLayout) findViewById(R.id.rl_xiugaitouxiang));
        rl_xiugainicheng = ((RelativeLayout) findViewById(R.id.rl_xiugainicheng));
        rl_xiugaixingbie = ((RelativeLayout) findViewById(R.id.rl_xiugaixingbie));
        rl_xiugaishengri = ((RelativeLayout) findViewById(R.id.rl_xiugaishengri));
        rl_xiugaijianjie = ((RelativeLayout) findViewById(R.id.rl_xiugaijianjie));
        rl_xiugaibeijingtupian = ((RelativeLayout) findViewById(R.id.rl_xiugaibeijingtupian));
        rl_xiugaishoujihao = ((RelativeLayout) findViewById(R.id.rl_xiugaishoujihao));
        rl_xiugaisuozaidi = ((RelativeLayout) findViewById(R.id.rl_xiugaisuozaidi));


        iv_xiugaiziliao_fanhui = ((ImageView) findViewById(R.id.iv_xiugaiziliao_fanhui));

        iv_xiugaitouxiang = ((ImageView) findViewById(R.id.iv_xiugaitouxiang));
        iv_geren_beijing = ((ImageView) findViewById(R.id.iv_geren_beijing));
        tv_xiugai_nicheng = ((TextView) findViewById(R.id.tv_xiugai_nicheng));
        tv_xiugai_xingbie = ((TextView) findViewById(R.id.tv_xiugai_xingbie));
        tv_xiugai_shengri = ((TextView) findViewById(R.id.tv_xiugai_shengri));
        tv_xiugai_jianjie = ((TextView) findViewById(R.id.tv_xiugai_jianjie));
        tv_xiugai_shoujihao = ((TextView) findViewById(R.id.tv_xiugai_shoujihao));
        tv_xiugai_suozaidi = ((TextView) findViewById(R.id.tv_xiugai_suozaidi));




        //修改头像
        x.image().bind(iv_xiugaitouxiang, NetUtil.url_zhuwei+user.getUserTouxiang());
        dianjitiaozhuan(TOUXIANG,rl_xiugaitouxiang,XiuGaiTouXiangActivity.class);
        //修改昵称
        tv_xiugai_nicheng.setText(user.getUserName());
        dianjitiaozhuan(NICHENG,rl_xiugainicheng,XiuGaiNiChengActivity.class);
        //修改性别
        tv_xiugai_xingbie.setText(user.getUserSex().equals("1")?"男":"女");
        dianjitiaozhuan(XINGBIE,rl_xiugaixingbie,XiuGaiXingBieActivity.class);
        //修改生日
        tv_xiugai_shengri.setText(user.getUserShengri());
        dianjitiaozhuan(SHENGRI,rl_xiugaishengri,XiuGaiShengRiActivity.class);
        //修改简介
        tv_xiugai_jianjie.setText(user.getUserJianjie());
        dianjitiaozhuan(JIANJIE,rl_xiugaijianjie, XiuGaiJianJieActivity.class);
        //修改背景
        x.image().bind(iv_geren_beijing,NetUtil.url_zhuwei+user.getUserBackground());
        dianjitiaozhuan(BEIJING,rl_xiugaibeijingtupian, XiuGaiBeiJingActivity.class);


        iv_xiugaiziliao_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = XiuGaiZiLiaoActivity.this.getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putSerializable("user", user);//添加要返回给页面1的数据
                System.out.println("准备返回到我的页面的"+user.getUserName());
                intent.putExtras(bundle);
                XiuGaiZiLiaoActivity.this.setResult(Activity.RESULT_OK, intent);
                XiuGaiZiLiaoActivity.this.finish();
            }
        });
    }

    /**
     * 点击view跳转至cls，并要求返回值
     * @param biaoshi
     * @param view
     * @param cls
     */
    public void dianjitiaozhuan(final int biaoshi,View view, final Class<?> cls){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),cls);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivityForResult(intent,biaoshi);

            }
        });
    }

    /**
     * 获得返回值
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Intent intent = this.getIntent();
        if (requestCode == NICHENG && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userName = user.getUserName();
            tv_xiugai_nicheng.setText(userName);
            System.out.println("修改返回到修改页面的"+user.getUserName());
        }

        if (requestCode == XINGBIE && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userSex = user.getUserSex();
            System.out.println("修改后的"+userSex);
            tv_xiugai_xingbie.setText(userSex.equals("0")?"女":"男");
        }

        if (requestCode == SHENGRI && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userShengri = user.getUserShengri();
            tv_xiugai_shengri.setText(userShengri);
        }

        if (requestCode == JIANJIE && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userJianjie = user.getUserJianjie();
            tv_xiugai_jianjie.setText(userJianjie);
        }

        if (requestCode == TOUXIANG && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userTouxiang = user.getUserTouxiang();
            //xUtilsImageUtils.display(userTouxiang,userTouxiang, true);
            x.image().bind(iv_xiugaitouxiang, NetUtil.url_zhuwei+user.getUserTouxiang());


        }

        if (requestCode == BEIJING && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            String userBeijing = user.getUserBackground();
            //xUtilsImageUtils.display(userTouxiang,userTouxiang, true);
            x.image().bind(iv_geren_beijing, NetUtil.url_zhuwei+user.getUserBackground());


        }

        super.onActivityResult(requestCode, resultCode, data);


    }

}
