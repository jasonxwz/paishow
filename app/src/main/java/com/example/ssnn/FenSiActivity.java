package com.example.ssnn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FenSiActivity extends AppCompatActivity {

    private ListView lv_fensi;
    List<User> fensis = new ArrayList<User>();
    private User user;
    private String touxiang_url;
    private ImageView iv_fensi_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_si);
        lv_fensi = ((ListView) findViewById(R.id.lv_fensi));
        iv_fensi_fanhui = ((ImageView) findViewById(R.id.iv_fensi_fanhui));
        user=(User)getIntent().getSerializableExtra("user");
        Integer userId = user.getUserId();

        BaseAdapter lv_adapter = new BaseAdapter() {
            private TextView tv_qitayonghu_jianjie;
            private ImageView iv_qitayonghu_touxiang;
            private TextView tv_qitayonghu_xingming;
            private User fensi;
            @Override
            public int getCount() {
                return fensis.size();
            }

            @Override
            public Object getItem(int i) {
                return fensis.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = View.inflate(getApplicationContext(),R.layout.layout_item_fensi,null);
                tv_qitayonghu_xingming = ((TextView) view.findViewById(R.id.tv_qitayonghu_xingming));
                iv_qitayonghu_touxiang = ((ImageView) view.findViewById(R.id.iv_qitayonghu_touxiang));
                tv_qitayonghu_jianjie = ((TextView) view.findViewById(R.id.tv_qitayonghu_jianjie));
                fensi =  fensis.get(i);
                tv_qitayonghu_xingming.setText(fensi.getUserName());
                touxiang_url = NetUtil.url_zhuwei+fensi.getUserTouxiang();
                xUtilsImageUtils.display(iv_qitayonghu_touxiang,touxiang_url, true);
                tv_qitayonghu_jianjie.setText(fensi.getUserJianjie());


                return view;
            }
        };
        getFensisById(userId);
        lv_fensi.setAdapter(lv_adapter);
        lv_adapter.notifyDataSetChanged();

        iv_fensi_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public  void getFensisById(Integer userId){
        RequestParams params = new RequestParams(NetUtil.url_zhuwei + "huodefensiservlet");
        params.addQueryStringParameter("userId",userId+"");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                fensis = gson.fromJson(result,type);
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
