package com.example.asus.paishow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.paishow.fragments.MainFragment;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.view.NineGridTestLayout;

import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DongtaiXiangxi extends AppCompatActivity {

    ListActivityBean.Dongtai dongtai;
    @InjectView(R.id.iv_returntohome)
    ImageView ivReturntohome;
    @InjectView(R.id.id_prod_list_tv)
    TextView idProdListTv;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.dongtai_title)
    LinearLayout dongtaiTitle;
    @InjectView(R.id.iv_touxiang)
    ImageView ivTouxiang;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_text)
    TextView tvText;
    //    @InjectView(R.id.iv_ha)
//    ImageView ivHa;
//    @InjectView(R.id.iv_shi)
//    ImageView ivShi;
//    @InjectView(R.id.iv_qi)
//    ImageView ivQi;
//    @InjectView(R.id.iv_hashiqi)
//    ImageView ivHashiqi;
//    @InjectView(R.id.ll_zhuanfa)
//    LinearLayout llZhuanfa;
//    @InjectView(R.id.ll_pinglun)
//    LinearLayout llPinglun;
//    @InjectView(R.id.ll_zan)
//    LinearLayout llZan;
//    @InjectView(R.id.gongneng)
//    LinearLayout gongneng;
    private NineGridTestLayout layout_nine_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dongtai_item_click);
        ButterKnife.inject(this);
        initData();
    }

    //初始化界面
    public void initData() {
        //获取传过来的dongtaiInfo
        layout_nine_grid =(NineGridTestLayout)findViewById(R.id.layout_nine_grid);
        Intent intent = this.getIntent();
        dongtai = (ListActivityBean.Dongtai) intent.getSerializableExtra("dongtaiInfo");
        if (dongtai != null) {
            x.image().bind(ivTouxiang, dongtai.userImg);
            try {
                tvName.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
                tvText.setText(URLDecoder.decode(dongtai.text,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            tvTime.setText(dongtai.getFabutime().toString());

//            ivHa.setImageResource(R.drawable.ha);
//            x.image().bind(ivHa,dongtai.imgs);
            List<String> urlsList = new ArrayList<String>();
            if(dongtai.getImgs()!=null && !"".equals(dongtai.getImgs())) {
                String[] urls = dongtai.getImgs().split(",");

                if (urls.length > 0) {
                    int length=urls.length;
                    for (int i = 0; i < urls.length; i++) {
                        urlsList.add(urls[i]);

                    }

                    layout_nine_grid.setIsShowAll(true);
                }
            }else if ("".equals(dongtai.getImgs()) && dongtai.getImgs()==null){
                layout_nine_grid.notifyDataSetChanged();

            }
            layout_nine_grid.setUrlList(urlsList);

        }

    }

    @OnClick({R.id.iv_returntohome, R.id.iv_share, R.id.iv_touxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_returntohome:
//                Intent intent =new Intent(DongtaiItemActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.iv_share:
                Void_ share = new Void_();
                share.showShare(DongtaiXiangxi.this, null, true);
                break;
            case R.id.iv_touxiang:
                break;
//            case R.id.ll_zhuanfa:
//                Void_ share1 = new Void_();
//                share1.showShare(DongtaiItemActivity.this, null, true);
//                break;
//            case R.id.ll_pinglun:
//                break;
//            case R.id.ll_zan:
//                break;
        }
    }
}

//
//    @OnClick({R.id.iv_returntohome, R.id.iv_share, R.id.iv_touxiang, R.id.btn_zan, R.id.btn_pinglun})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_returntohome:
//
//                break;
//            case R.id.iv_share:
//                break;
//            case R.id.iv_touxiang:
//                break;
//            case R.id.btn_zan:
//                btnZan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {}});
////                        if(count%2==0) {
////                            System.out.println(count+""+(count%2==0));
////                            v.setSelected(true);
////
////                        }else{
////                            v.setSelected(false);
////                        }
////                    count++;
////                    }
////                });
//                break;
//            case R.id.btn_pinglun:
//                break;
//        }
//    }
//}
