package com.example.ssnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XinJianXiangCeActivity extends AppCompatActivity {
    private User user;
    private Integer userId;
    private String xiangceMing;
    private String xiangceShijian;
    private EditText et_xinjianxiangce_biaoti;
    private ImageView iv_xinjianxiangce_fanhui;
    private TextView tv_xinjianxiangce_baocun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_jian_xiang_ce);
        user=(User)getIntent().getSerializableExtra("user");
        userId=user.getUserId();
        et_xinjianxiangce_biaoti = ((EditText) findViewById(R.id.et_xinjianxiangce_biaoti));
        iv_xinjianxiangce_fanhui = ((ImageView) findViewById(R.id.iv_xinjianxiangce_fanhui));
        tv_xinjianxiangce_baocun = ((TextView) findViewById(R.id.tv_xinjianxiangce_baocun));
        tv_xinjianxiangce_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiangceMing = et_xinjianxiangce_biaoti.getText().toString().trim();
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                xiangceShijian=format.format(date);
                RequestParams requestParams = new RequestParams(NetUtil.url_zhuwei + "XinJianXiangCeServlet");
                requestParams.addQueryStringParameter("userId",userId+"");
                requestParams.addQueryStringParameter("xiangceMing",xiangceMing);
                requestParams.addQueryStringParameter("xiangceShijian",xiangceShijian);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

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

                finish();
            }
        });

        iv_xinjianxiangce_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });










    }

}
