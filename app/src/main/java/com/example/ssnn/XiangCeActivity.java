package com.example.ssnn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.pojo.UserXiangce;
import com.example.asus.paishow.pojo.UserZhaopian;
import com.example.asus.paishow.utils.NetUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class XiangCeActivity extends AppCompatActivity {
    private Integer userId;
    private BaseAdapter listview_adapter;
    private List<UserZhaopian> userZhaopians;
    private List<UserXiangce> userXiangces;
    private PullToRefreshGridView lv_xiangce;
    private UserZhaopian userZhaopian;
    private User user;
    private TextView tv_xiangce_tianjia;
    private ImageView iv_xiangce_fanhui;
    private ImageView iv_shanchu_xiaohongdian;

    //  private  GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_ce);
        lv_xiangce = ((PullToRefreshGridView) findViewById(R.id.lv_xiangce));
        tv_xiangce_tianjia = ((TextView) findViewById(R.id.tv_xiangce_tianjia));
        iv_xiangce_fanhui = ((ImageView) findViewById(R.id.iv_xiangce_fanhui));
        iv_shanchu_xiaohongdian = ((ImageView) findViewById(R.id.iv_shanchu_xiaohongdian));

        //gridView.setOnItemLongClickListener();
        lv_xiangce.setOnRefreshListener(new OnRefreshListener2<GridView>() {

    @Override
    public void onPullDownToRefresh(
            PullToRefreshBase<GridView> refreshView)
    {
       // Log.e("TAG", "onPullDownToRefresh"); // Do work to
        String label = DateUtils.formatDateTime(
                getApplicationContext(),
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy()
                .setLastUpdatedLabel(label);

       getXiangceBy();
        lv_xiangce.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(
            PullToRefreshBase<GridView> refreshView)
    {

    }
});
        //Bundle bundle = getArguments();//从activity传过来的Bundle
        user=(User)getIntent().getSerializableExtra("user");
        userId = user.getUserId();

        tv_xiangce_tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_xiangce_tianjia.getText().toString().trim().equals("新建")) {
                    Intent intent = new Intent(getApplicationContext(), XinJianXiangCeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        listview_adapter = new BaseAdapter() {
            private TextView tv_zhangshu;
            private ImageView iv_zhaopian;
            //private MyGridView gv_zhaopian;
            private TextView tv_xiangce_riqi;
            private TextView tv_xiangce_biaoti;
            private ImageView iv_shanchu_xiaohongdian;

            @Override
            public int getCount() {
                return userXiangces.size();
            }

            @Override
            public Object getItem(int i) {
                return userXiangces.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {


                view = View.inflate(getApplicationContext(),R.layout.layout_item_xiangce,null);
                tv_xiangce_biaoti = ((TextView) view.findViewById(R.id.tv_xiangce_biaoti));
                tv_xiangce_riqi = ((TextView) view.findViewById(R.id.tv_xiangce_riqi));
                iv_zhaopian = ((ImageView) view.findViewById(R.id.iv_zhaopian));
                tv_zhangshu = ((TextView) view.findViewById(R.id.tv_zhangshu));
                iv_shanchu_xiaohongdian = ((ImageView) findViewById(R.id.iv_shanchu_xiaohongdian));
                //gv_zhaopian = ((MyGridView) view.findViewById(R.id.gv_zhaopian));
                UserXiangce userXiangce = userXiangces.get(i);
                Integer xiangceId = userXiangce.getXiangceId();
                System.out.println("相册"+(i+1)+"的id"+xiangceId);
                tv_xiangce_biaoti.setText(userXiangce.getXiangceMing());
                tv_xiangce_riqi.setText(userXiangce.getXiangceShijian());
                userZhaopians= userXiangce.getUserZhaopians();
                if(userZhaopians.size()!=0) {
                    userZhaopian = userZhaopians.get(0);
                    String zhaopian_url = NetUtil.url_zhuwei+userZhaopian.getZhaipianUrl();
                    x.image().bind(iv_zhaopian, zhaopian_url);
                }

                tv_zhangshu.setText("共"+userZhaopians.size()+"张");
                //gv_zhaopian.setAdapter(gridview_adapter);
               // gridview_adapter.notifyDataSetChanged();







                return view;
            }
        };
        getXiangceBy();
        lv_xiangce.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private ImageView iv_shanchu_xiaohongdian;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iv_shanchu_xiaohongdian = ((ImageView) view.findViewById(R.id.iv_shanchu_xiaohongdian));
                iv_shanchu_xiaohongdian.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), ZhaoPianActivity.class);
                Bundle bundle = new Bundle();
                UserXiangce userXiangce=userXiangces.get(i);
                System.out.println(userZhaopians.toString());
                bundle.putSerializable("userXiangce",(Serializable) userXiangce);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * 长按删除
         */

        lv_xiangce.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private ImageView iv_zhaopian;
            private ImageView iv_shanchu_xiaohongdian;
            private RequestParams requestParams;
            private Integer xiangceId_shan;

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                iv_shanchu_xiaohongdian = ((ImageView) view.findViewById(R.id.iv_shanchu_xiaohongdian));
                iv_zhaopian = ((ImageView) view.findViewById(R.id.iv_zhaopian));
                iv_shanchu_xiaohongdian.setVisibility(View.VISIBLE);
                tv_xiangce_tianjia.setText("取消");
                UserXiangce userXiangce_shan = userXiangces.get(i);
                xiangceId_shan = userXiangce_shan.getXiangceId();
                requestParams = new RequestParams(NetUtil.url_zhuwei + "XinJianXiangCeServlet");

                iv_shanchu_xiaohongdian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("点击的小红点");
                        requestParams.addQueryStringParameter("xiangceId",xiangceId_shan+"");
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
                        tv_xiangce_tianjia.setText("新建");
                        getXiangceBy();
                       // listview_adapter.notifyDataSetChanged();
                    }
                });

                    tv_xiangce_tianjia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (tv_xiangce_tianjia.getText().toString().trim().equals("取消")) {
                                //iv_shanchu_xiaohongdian.setVisibility(View.INVISIBLE);
                                listview_adapter.notifyDataSetChanged();
                                tv_xiangce_tianjia.setText("新建");
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), XinJianXiangCeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });
                return true;
            }
        });

        iv_xiangce_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void getXiangceBy(){
        RequestParams params = new RequestParams(NetUtil.url_zhuwei + "UserXiangceServlet");
        params.addQueryStringParameter("userId", userId+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<UserXiangce>>() {
                }.getType();
                userXiangces = gson.fromJson(result, type);
                System.out.println("照片数量"+userXiangces.get(0).getUserZhaopians().size());
               lv_xiangce.setAdapter(listview_adapter);
                listview_adapter.notifyDataSetChanged();
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
