package com.example.asus.paishow.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;
import com.example.ssnn.CaoGaoXiangActivity;
import com.example.ssnn.DengJiChengHaoActivity;
import com.example.ssnn.FenSiActivity;
import com.example.ssnn.SheZhiActivity;
import com.example.ssnn.ShiGuangZhouActivity;
import com.example.ssnn.XiangCeActivity;
import com.example.ssnn.XiuGaiZiLiaoActivity;
import com.example.ssnn.YuWoXiangGuanActivity;
import com.example.ssnn.GuanZhuActivity;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MineFragment extends BaseFragment{

    private TextView tv_name;
    private TextView tv_jianjie;
    private ImageView iv_touxiang;
    public User user;
    public User user1;
    private Integer userId;
    private String touxiang_url;
    //private TextView tv_gerenzhuye;
    private TextView tv_dengjichenghao;
    //private ImageView iv_vip;
    private String beijing_url;
    private RelativeLayout rl_geren_beijing;
    private ImageView iv_geren_beijing;
    private TextView tv_shiguangzhou;
    private TextView tv_caogaoxiang;
    private TextView tv_shezhi;
    private ImageView iv_sex;
    private TextView tv_xiangce;
   // private TextView tv_yuwoxiangguan;
    private TextView tv_wodedongtai_xianshi;
    private TextView tv_fensi_xianshi;
    private TextView tv_guanzhi_xianshi;
    private RelativeLayout rl_wodexiangce;
   // private RelativeLayout rl_yuwoxiangguan;
    private RelativeLayout rl_dengjichenghao;
    private RelativeLayout rl_shiguangzhou;
    private RelativeLayout rl_cangaoxiang;
    private RelativeLayout rl_shezhi;
    private TextView tv_rank;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.mine_fragment, null);
        iv_touxiang = ((ImageView) view.findViewById(R.id.iv_touxiang));
        tv_name = ((TextView) view.findViewById(R.id.tv_name));
        tv_jianjie = ((TextView) view.findViewById(R.id.tv_jianjie));
        //tv_gerenzhuye = ((TextView) view.findViewById(R.id.tv_gerenzhuye));
        tv_dengjichenghao = ((TextView) view.findViewById(R.id.tv_dengjichenghao));
        //iv_vip = ((ImageView) view.findViewById(R.id.iv_vip));
        rl_geren_beijing = ((RelativeLayout) view.findViewById(R.id.rl_geren_beijing));
        iv_geren_beijing = ((ImageView) view.findViewById(R.id.iv_geren_beijing));
        tv_shiguangzhou = ((TextView) view.findViewById(R.id.tv_shiguangzhou));
        tv_caogaoxiang = ((TextView) view.findViewById(R.id.tv_caogaoxiang));
        tv_shezhi = ((TextView) view.findViewById(R.id.tv_shezhi));
        iv_sex = ((ImageView) view.findViewById(R.id.iv_sex));
        tv_xiangce = ((TextView) view.findViewById(R.id.tv_xiangce));
        //tv_yuwoxiangguan = ((TextView) view.findViewById(R.id.tv_yuwoxiangguan));
        tv_wodedongtai_xianshi = ((TextView) view.findViewById(R.id.tv_wodedongtai_xianshi));
        tv_fensi_xianshi = ((TextView) view.findViewById(R.id.tv_fensi_xianshi));
        tv_guanzhi_xianshi = ((TextView) view.findViewById(R.id.tv_guanzhi_xianshi));
        rl_wodexiangce = ((RelativeLayout) view.findViewById(R.id.rl_wodexiangce));
       // rl_yuwoxiangguan = ((RelativeLayout) view.findViewById(R.id.rl_yuwoxiangguan));
        rl_dengjichenghao = ((RelativeLayout) view.findViewById(R.id.rl_dengjichenghao));
        rl_shiguangzhou = ((RelativeLayout) view.findViewById(R.id.rl_shiguangzhou));
        rl_cangaoxiang = ((RelativeLayout) view.findViewById(R.id.rl_cangaoxiang));
        rl_shezhi = ((RelativeLayout) view.findViewById(R.id.rl_shezhi));
        tv_rank = ((TextView) view.findViewById(R.id.tv_rank));


        userId=1;

        /**
         * 向后台传userId，获得用户信息
         */
        RequestParams params = new RequestParams(NetUtil.url_zhuwei + "UserServlet");
        System.out.println("++++++++++++++++++++++++++++++++");
        params.addQueryStringParameter("userId",userId+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               Resources res = getResources(); //resource handle
                System.out.println("前台拿到的"+result);
                Gson gson = new Gson();
                user = gson.fromJson(result,User.class);
                user1=user;
                System.out.println("前台拿到的"+user.toString());
                tv_name.setText(user.getUserName());
                tv_jianjie.setText("简介："+user.getUserJianjie());
                touxiang_url = NetUtil.url_zhuwei+user.getUserTouxiang();
                beijing_url=NetUtil.url_zhuwei+user.getUserBackground();
                xUtilsImageUtils.display(iv_touxiang,touxiang_url, true);
                tv_rank.setText("Lv"+user.getUserRank());
                //iv_vip.setSelected((user.getUserVipFlag()==1?false:true));

                x.image().bind(iv_geren_beijing,beijing_url);
                System.out.println("^^^^^^^^^^^^^^^^^^^^^"+user.getUserSex());
                iv_sex.setSelected((user.getUserSex().equals("0"))?true:false);
                tv_wodedongtai_xianshi.setText("11");
                tv_fensi_xianshi.setText(user.getUserFensi()+"");
                tv_guanzhi_xianshi.setText(user.getUserGuanzhu()+"");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("*********************************");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        /**
         * 跳转至个人主页,请求返回user
         */
  /*      tv_gerenzhuye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GeRenZhuYeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
        /**
         * 跳转至粉丝
         */
      /*  tv_fensi_xianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FenSiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
        /**
         * 跳转至关注
         */
      /*  tv_guanzhi_xianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GuanZhuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        /**
         * 跳转至相册
         */
        rl_wodexiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), XiangCeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 跳转至与我相关
         */
 /*       rl_yuwoxiangguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YuWoXiangGuanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        */
     /*   *//***//*
         * 跳转至等级称号
         */
        rl_dengjichenghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DengJiChengHaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 跳转至时光轴
         */
        rl_shiguangzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ShiGuangZhouActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 跳转至草稿箱
         */
        rl_cangaoxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CaoGaoXiangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 跳转至设置
         */
        rl_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SheZhiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 跳转至修改资料
         */
        iv_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),XiuGaiZiLiaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivityForResult(intent,40);
            }
        });
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
    public void getUserById(String userId){


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("*****"+requestCode);
        if (requestCode == 40 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            user = (User) bundle.getSerializable("user");
            tv_name.setText(user.getUserName());
            tv_jianjie.setText("简介："+user.getUserJianjie());
            touxiang_url = NetUtil.url_zhuwei+user.getUserTouxiang();
            beijing_url=NetUtil.url_zhuwei+user.getUserBackground();
            xUtilsImageUtils.display(iv_touxiang,touxiang_url, true);
            //iv_vip.setSelected((user.getUserVipFlag()==1?false:true));
            x.image().bind(iv_geren_beijing,beijing_url);
            iv_sex.setSelected((user.getUserSex().equals("0"))?true:false);

            Integer userId=user.getUserId();
            String userName=user.getUserName();
            String userSex=user.getUserSex();
            String userLocation=user.getUserLocation();
            String userTouxiang=user.getUserTouxiang();
            String userBackground=user.getUserBackground();
            String userJianjie=user.getUserJianjie();
            Integer userRank=user.getUserRank();
            Integer userVipFlag=user.getUserVipFlag();
            Integer userFensi=user.getUserFensi();
            Integer userGuanzhu=user.getUserGuanzhu();
            String userShengri=user.getUserShengri();

            RequestParams params = new RequestParams(NetUtil.url_zhuwei + "XiuGaiUserZiLiaoServlet");
            params.addQueryStringParameter("userId",userId+"");
            params.addQueryStringParameter("userName",userName);
            params.addQueryStringParameter("userSex",userSex);
            params.addQueryStringParameter("userLocation",userLocation);
            params.addQueryStringParameter("userTouxiang",userTouxiang);
            params.addQueryStringParameter("userBackground",userBackground);
            params.addQueryStringParameter("userJianjie",userJianjie);
            params.addQueryStringParameter("userRank",userRank+"");
            params.addQueryStringParameter("userVipFlag",userVipFlag+"");
            params.addQueryStringParameter("userFensi",userFensi+"");
            params.addQueryStringParameter("userGuanzhu",userGuanzhu+"");
            params.addQueryStringParameter("userShengri",userShengri);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if(!user.toString().equals(user1.toString())){
                    Toast.makeText(getActivity(),"资料修改成功啦～",Toast.LENGTH_SHORT).show();
                        user1=user;
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





    }
}
