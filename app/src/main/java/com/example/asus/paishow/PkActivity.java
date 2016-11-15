package com.example.asus.paishow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.asus.paishow.listview.UserInfoBean;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.pojo.PKInfoBean;
import com.example.asus.paishow.pubuliu.PuBuAdapter;
import com.example.asus.paishow.pubuliu.PullToRefreshStaggeredGridView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PkActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener<StaggeredGridView> {
    @InjectView(R.id.iv_returntohome)
    ImageView ivReturntohome;
    @InjectView(R.id.id_prod_list_tv)
    TextView idProdListTv;
    @InjectView(R.id.iv_rules)
    ImageView ivRules;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.dongtai_title)
    LinearLayout dongtaiTitle;
    @InjectView(R.id.pull_grid_view)
    PullToRefreshStaggeredGridView pullGridView;
    @InjectView(R.id.activity_pupo)
    RelativeLayout activityPupo;
    private StaggeredGridView mDongTaiGridView;

    private PullToRefreshStaggeredGridView mPullToRefreshStaggerdGridView;

    private int page = 1;

   // List<ListActivityBean.Dongtai> dongtaiList = new ArrayList<ListActivityBean.Dongtai>();
    List<PKInfoBean.PKInfo> pkList = new ArrayList<PKInfoBean.PKInfo>();
    PuBuAdapter puBuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk);
        ButterKnife.inject(this);
//        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
//        adapter = new MyAdapter(PkActivity.this);
//
//        mGridView.setAdapter(adapter);
        mPullToRefreshStaggerdGridView = (PullToRefreshStaggeredGridView) findViewById(R.id.pull_grid_view);
        mPullToRefreshStaggerdGridView.setMode(PullToRefreshBase.Mode.BOTH);

        mPullToRefreshStaggerdGridView.setScrollingWhileRefreshingEnabled(true);


        mPullToRefreshStaggerdGridView.setOnRefreshListener(this);

        mDongTaiGridView = mPullToRefreshStaggerdGridView.getRefreshableView();

        puBuAdapter = new PuBuAdapter(getApplicationContext(), pkList);

        mDongTaiGridView.setAdapter(puBuAdapter);

        getData();

        initEvent();



    }

    private void initEvent() {
        mDongTaiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick", "onItemClick: "+pkList.get(position) );

                Intent intent = new Intent(PkActivity.this, pk2Activity.class);
                intent.putExtra("id",pkList.get(position).id);
                intent.putExtra("pk1Id",pkList.get(position).pk1Id);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {

//异步任务拿数据

        PullToRefreshBase.Mode mode = mPullToRefreshStaggerdGridView.getCurrentMode();

        // View viewRefresh = null;

        if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
            getData();


        } else if (mode == PullToRefreshBase.Mode.PULL_FROM_END) {

            page++;
            getDataTwo(page);


        }


    }

    private void getData() {
        RequestParams params = new RequestParams("http://10.40.5.48:8080/paishow/toandroidpk");
        params.addBodyParameter("page", 1 + "");
        x.http().get(params, new Callback.CommonCallback<String>() {//返回数据的类型
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PKInfoBean bean = gson.fromJson(result, PKInfoBean.class);

                System.out.println(bean.toString()+"12333333123123213");

                //获得商品列表
                pkList.clear();
                pkList.addAll(bean.pklist);
                //再通知适配器
                puBuAdapter.notifyDataSetChanged();

                mPullToRefreshStaggerdGridView.onRefreshComplete();


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

    private void getDataTwo(int page) {
        RequestParams params = new RequestParams("http://10.40.5.48:8080/paishow/toandroidpk");

        params.addBodyParameter("page", 1 + "");

        x.http().get(params, new Callback.CommonCallback<String>() {//返回数据的类型
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PKInfoBean bean = gson.fromJson(result, PKInfoBean.class);


                //获得商品列表
                pkList.clear();
                pkList.addAll(bean.pklist);
                //再通知适配器
                puBuAdapter.notifyDataSetChanged();

                mPullToRefreshStaggerdGridView.onRefreshComplete();

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

    @OnClick({R.id.iv_returntohome, R.id.iv_rules})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_returntohome:
                Intent intent =new Intent(this,RuleActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_rules:
                Intent intent1=new Intent(this,CameraActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
