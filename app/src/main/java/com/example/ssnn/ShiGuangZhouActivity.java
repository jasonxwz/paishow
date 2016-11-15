package com.example.ssnn;

import android.graphics.Color;
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
import com.example.asus.paishow.pojo.UserShiguangzhou;
import com.example.asus.paishow.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

public class ShiGuangZhouActivity extends AppCompatActivity {
    private Integer userId;
    private List<UserShiguangzhou> userShiguangzhous;
    private BaseAdapter adapter;
    private ListView lv_shiguangzhou;
    private ImageView iv_shiguangzhou_fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_guang_zhou);
        lv_shiguangzhou = ((ListView) findViewById(R.id.lv_shiguangzhou));
        iv_shiguangzhou_fanhui = ((ImageView) findViewById(R.id.iv_shiguangzhou_fanhui));
        final User user=(User)getIntent().getSerializableExtra("user");
        userId = user.getUserId();
        adapter = new BaseAdapter() {

            //private TextView tv_shiguangzhou_neirongkuang;
            private TextView tv_shiguangzhou_neirong;
            private TextView tv_shiguangzhou_riqi;
            private ImageView iv_shiguangzhou;

            @Override
            public int getCount() {
                return userShiguangzhous.size();
            }

            @Override
            public Object getItem(int i) {
                return userShiguangzhous.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = View.inflate(getApplicationContext(),R.layout.layout_item_shiguangzhou,null);
                iv_shiguangzhou = ((ImageView) view.findViewById(R.id.iv_shiguangzhou));
                tv_shiguangzhou_riqi = ((TextView) view.findViewById(R.id.tv_shiguangzhou_riqi));
                //tv_shiguangzhou_neirongkuang = ((TextView) view.findViewById(R.id.tv_shiguangzhou_neirongkuang));
                tv_shiguangzhou_neirong = ((TextView) view.findViewById(R.id.tv_shiguangzhou_neirong));
                UserShiguangzhou userShiguangzhou = userShiguangzhous.get(i);
               // String colorString = "#fa"+i;
               // tv_shiguangzhou_neirong.setBackgroundColor(Color.parseColor("#fa0"));
                tv_shiguangzhou_riqi.setText(userShiguangzhou.getsTime());
                tv_shiguangzhou_neirong.setText(userShiguangzhou.getsNeiRong());
                if(userShiguangzhou.getsNeiRong().equals("PK胜利")){
                    tv_shiguangzhou_neirong.setBackgroundColor(Color.parseColor("#00EE76"));
                }else {
                    tv_shiguangzhou_neirong.setBackgroundColor(Color.parseColor("#ff0000"));
                }


                return view;
            }
        };
        RequestParams params = new RequestParams(NetUtil.url_zhuwei + "UserShiguangzhouServlet");
        params.addQueryStringParameter("userId", userId+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<UserShiguangzhou>>() {
                }.getType();
                userShiguangzhous = gson.fromJson(result, type);
                lv_shiguangzhou.setAdapter(adapter);
                adapter.notifyDataSetChanged();




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
        iv_shiguangzhou_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),MineFragment.class);
                finish();
            }
        });
    }

}
