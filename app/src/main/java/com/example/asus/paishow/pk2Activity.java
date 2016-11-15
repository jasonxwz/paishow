package com.example.asus.paishow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.asus.paishow.pojo.PKInfo;
import com.example.asus.paishow.pojo.PKInfoBean;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.view.TimeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.internal.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class pk2Activity extends AppCompatActivity {
    private TimeView timeView;
    @InjectView(R.id.iv_returntohome)
    ImageView ivReturntohome;
    @InjectView(R.id.id_prod_list_tv)
    TextView idProdListTv;
    @InjectView(R.id.iv_sent)
    ImageView ivSent;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.dongtai_title)
    LinearLayout dongtaiTitle;
    @InjectView(R.id.tv_leizhu)
    TextView tvLeizhu;
    @InjectView(R.id.btn_container_main)
    RelativeLayout btnContainerMain;
    @InjectView(R.id.tv_tiaozhanzhe)
    TextView tvTiaozhanzhe;
    @InjectView(R.id.btn_container_message)
    RelativeLayout btnContainerMessage;
    @InjectView(R.id.main_linear)
    LinearLayout mainLinear;
    @InjectView(R.id.iv_left)
    ImageView ivLeft;
    @InjectView(R.id.rl_left)
    RelativeLayout rlLeft;
    @InjectView(R.id.iv_right)
    ImageView ivRight;
    @InjectView(R.id.rl_right)
    RelativeLayout rlRight;
    @InjectView(R.id.pk_picture)
    LinearLayout pkPicture;
    @InjectView(R.id.iv_pk_left)
    ImageView ivPkLeft;
    @InjectView(R.id.rl_pk_left)
    RelativeLayout rlPkLeft;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.rl_time)
    RelativeLayout rlTime;
    @InjectView(R.id.iv_pk_right)
    ImageView ivPkRight;
    @InjectView(R.id.rl_pk_right)
    RelativeLayout rlPkRight;
    @InjectView(R.id.pktime)
    LinearLayout pktime;
    @InjectView(R.id.lv_duojipinglun)
    ListView lvDuojipinglun;
    @InjectView(R.id.loadmore)
    TextView loadmore;
    @InjectView(R.id.comment_content)
    EditText commentContent;
    @InjectView(R.id.comment_commit)
    Button commentCommit;
    @InjectView(R.id.area_commit)
    LinearLayout areaCommit;
    @InjectView(R.id.iv_loveleft)
    ImageView iv_loveleft;
    @InjectView(R.id.iv_loveright)
    ImageView iv_loveright;
    @InjectView(R.id.tv_pk_userName_left)
    TextView tv_pk_userName_left;
    @InjectView(R.id.tv_pk_userName_right)
    TextView tv_pk_userName_Right;
    private Integer zan1Num;
    private Integer zan2Num = 0;
    private boolean love = false;
    private boolean isSelect = true;//默认允许点赞
    int pkId = 0;
    int pk1Id=0;//左边用户id
    int pk2Id=0;//右边用户id
    int userId = 1;//模拟当前用户id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_item_layout);
        ButterKnife.inject(this);
        initdata();

        timeView = (TimeView) findViewById(R.id.time_demo);
        timeView.setTime(86400);


    }
    public void update(View view) {
        timeView.reStart(100);
    }
    public void update1(View view) {
        //回到设置的事件
        timeView.reStart();
    }

    private void initdata() {
        pkId = getIntent().getIntExtra("id", 0);
        pk1Id=getIntent().getIntExtra("pk1Id",0);
        Log.e("result11", "pkId: " + pkId);
        //从sharePrefernce读取当前用户id

        connectInit(pkId, userId);
    }

    /**
     * 初始化数据
     *
     * @param pkId
     * @param userId
     */
    public void connectInit(int pkId, int userId) {
        RequestParams params = new RequestParams(NetUtil.GET_ZAN_USERID);
        params.addParameter("pkId", pkId);
        params.addParameter("userId", userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PKInfo pkinfo = gson.fromJson(result, PKInfo.class);
                setDateFromNet(pkinfo);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("result1111", "exception: " + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @OnClick({R.id.iv_returntohome, R.id.iv_sent, R.id.iv_pk_left, R.id.iv_pk_right, R.id.comment_commit, R.id.tv_time, R.id.iv_loveleft,R.id.iv_loveright ,R.id.iv_right,R.id.iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_returntohome:
                finish();
                break;
            case R.id.iv_sent:
                Void_ share = new Void_();
                share.showShare(this, null, true);
                break;
            case R.id.iv_pk_left:
                break;
            case R.id.iv_pk_right:
                Log.e("Click", "onClick:right " );
                break;
            case R.id.comment_commit:
                break;
            case R.id.tv_time:
                break;
            case R.id.iv_loveleft:
                dianzan(pk1Id,iv_loveleft,0);
                break;
            case R.id.iv_loveright:
                Log.e("Click", "onClick:love " );
                dianzan(pk2Id,iv_loveright,1);
                break;
            case R.id.iv_right:
                break;
            case R.id.iv_left:
                break;
        }
    }

    private void setDateFromNet(PKInfo pkinfo) {

        if (pkinfo.getUsers().size() == 2) {
            pk2Id=pkinfo.getUsers().get(1).getUserId();
            x.image().bind(ivRight, pkinfo.pk2Picture);//右边标题图片
            x.image().bind(ivPkRight, pkinfo.getUsers().get(1).getUserTouxiang());
            tv_pk_userName_Right.setVisibility(View.VISIBLE);
            iv_loveright.setVisibility(View.VISIBLE);
            ivPkRight.setVisibility(View.VISIBLE);
            if (pkinfo.getUsers().get(1).isLike()) {
                isSelect = false;
                iv_loveright.setImageResource(R.drawable.lovered);
            }
            tv_pk_userName_Right.setText(pkinfo.getUsers().get(1).getUserName());
        }else {
            ivRight.setImageResource(R.drawable.wait);
            tv_pk_userName_Right.setVisibility(View.GONE);
            iv_loveright.setVisibility(View.GONE);
            ivPkRight.setVisibility(View.GONE);
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(pk2Activity.this,CameraActivity.class);
                    intent.putExtra("isJoin",true);
                    intent.putExtra("pkId",pkId);
                    startActivityForResult(intent,0);
                }
            });
        }
        x.image().bind(ivLeft, pkinfo.pk1Picture);//左边标题图片
        x.image().bind(ivPkLeft, pkinfo.getUsers().get(0).getUserTouxiang());
        if (pkinfo.getUsers().get(0).isLike()) {
            isSelect = false;
            iv_loveleft.setImageResource(R.drawable.lovered);
        }
        tv_pk_userName_left.setText(pkinfo.getUsers().get(0).getUserName());
    }

    /**
     *
     * @param targetId 目标id
     * @param iv 点赞空间
     * @param flag 左边还有右边
     */
    private void dianzan(int targetId,ImageView iv,int flag) {
        if (isSelect) {
            iv.setImageResource(R.drawable.lovered);
            RequestParams params = new RequestParams(NetUtil.DIAN_ZAN);
            params.addParameter("pkId",pkId);
            params.addParameter("targetId",targetId);
            params.addParameter("userId",userId);
            params.addParameter("flag",flag);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if(!result.equals("0")){
                        isSelect=false;
                        Toast.makeText(pk2Activity.this, "点在成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(pk2Activity.this, "点赞失败", Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(pk2Activity.this, "您已经点过赞，不能再次点击", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                connectInit(pkId,userId);//
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
