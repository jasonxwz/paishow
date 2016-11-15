package com.example.ssnn;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.ImagePagerActivity;
import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.UserXiangce;
import com.example.asus.paishow.pojo.UserZhaopian;
import com.example.asus.paishow.utils.NetUtil;

import com.example.asus.paishow.utils.SmoothImageView;
import com.example.asus.paishow.view.HackyViewPager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;



public class ZhaoPianActivity extends AppCompatActivity {
    //private UserZhaopian userZhaopian;
    private List<UserZhaopian> userZhaopians;
    private PullToRefreshGridView gv_zhaopian;
    private ImageView iv_zhaop_fanhui;
    private TextView tv_zhaopian_tianjia;
    private TextView tv_xiangceming;
    private UserXiangce userXiangce;
    private Integer userId;
    private String zhaopian_url;

    private GoogleApiClient client;
//    private HackyViewPager iv_xiangce_zhaopian;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhao_pian);
        //userZhaopians = (List<UserZhaopian>) getIntent().getSerializableExtra("userZhaopians");
        userXiangce = (UserXiangce) getIntent().getSerializableExtra("userXiangce");
        String xiangceMing = userXiangce.getXiangceMing();
        userZhaopians = userXiangce.getUserZhaopians();
        //System.out.println("照片"+userZhaopians.size());
        gv_zhaopian = ((PullToRefreshGridView) findViewById(R.id.gv_zhaopian));
        iv_zhaop_fanhui = ((ImageView) findViewById(R.id.iv_zhaop_fanhui));
        tv_zhaopian_tianjia = ((TextView) findViewById(R.id.tv_zhaopian_tianjia));
        tv_xiangceming = ((TextView) findViewById(R.id.tv_xiangceming));
//        iv_xiangce_zhaopian = ((HackyViewPager) findViewById(R.id.iv_xiangce_zhaopian));

        tv_xiangceming.setText(xiangceMing);
        final BaseAdapter gv_adapter = new BaseAdapter() {
            private ImageView iv_xiangce_zhaopian;

            @Override
            public int getCount() {
                return userZhaopians.size();
            }

            @Override
            public Object getItem(int i) {
                return userZhaopians.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = View.inflate(getApplicationContext(), R.layout.layout_item_zhaopian, null);
                iv_xiangce_zhaopian = ((ImageView) view.findViewById(R.id.iv_xiangce_zhaopian));
                UserZhaopian userZhaopian = userZhaopians.get(i);
                zhaopian_url = NetUtil.url_zhuwei + userZhaopian.getZhaipianUrl();
                x.image().bind(iv_xiangce_zhaopian, zhaopian_url);

                return view;

            }
        };

        gv_zhaopian.setAdapter(gv_adapter);
        gv_adapter.notifyDataSetChanged();

        tv_zhaopian_tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TianJiaZhaoPianActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("userXiangce", userXiangce);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        /**
         * 长按删除
         */
        gv_zhaopian.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private ImageView iv_xiangce_zhaopian;
            private ImageView iv_shanchu_xiaohongdian_zhaopian;
            private RequestParams requestParams;
            private Integer zhaopianId_shan;

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                iv_shanchu_xiaohongdian_zhaopian = ((ImageView) view.findViewById(R.id.iv_shanchu_xiaohongdian_zhaopian));
                iv_xiangce_zhaopian = ((ImageView) view.findViewById(R.id.iv_xiangce_zhaopian));
                iv_shanchu_xiaohongdian_zhaopian.setVisibility(View.VISIBLE);
                tv_zhaopian_tianjia.setText("取消");
                UserZhaopian userZhaopian_shan = userZhaopians.get(i);
                zhaopianId_shan = userZhaopian_shan.getZhaopianId();
                requestParams = new RequestParams(NetUtil.url_zhuwei + "XinJianXiangCeServlet");

                iv_shanchu_xiaohongdian_zhaopian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("点击的小红点");
                        requestParams.addQueryStringParameter("zhaopianId",zhaopianId_shan+"");
                        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                List<UserZhaopian> userZhaopianList = new ArrayList<UserZhaopian>();
                                userZhaopianList.addAll(userZhaopians);
                                userZhaopianList.remove(i);
                                //userXiangce = (UserXiangce) getIntent().getSerializableExtra("userXiangce");
                               //userZhaopianList = userXiangce.getUserZhaopians();
                                userZhaopians.clear();
                                userZhaopians.addAll(userZhaopianList);
                                gv_zhaopian.setAdapter(gv_adapter);
                                gv_adapter.notifyDataSetChanged();
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
                        tv_zhaopian_tianjia.setText("添加");

                    }
                });

                tv_zhaopian_tianjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tv_zhaopian_tianjia.getText().toString().trim().equals("取消")) {
                            //iv_shanchu_xiaohongdian.setVisibility(View.INVISIBLE);
                            gv_adapter.notifyDataSetChanged();
                            tv_zhaopian_tianjia.setText("添加");
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), TianJiaZhaoPianActivity.class);
                            Bundle bundle = new Bundle();

                            bundle.putSerializable("userXiangce", userXiangce);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
                return true;
            }
        });


        iv_zhaop_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gv_zhaopian.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                //Log.e("TAG", "onPullDownToRefresh"); // Do work to
                String label = DateUtils.formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                List<UserZhaopian> userZhaopianList = new ArrayList<UserZhaopian>();
                userZhaopianList.addAll(userZhaopians);

                //userXiangce = (UserXiangce) getIntent().getSerializableExtra("userXiangce");
                //userZhaopianList = userXiangce.getUserZhaopians();
                userZhaopians.clear();
                userZhaopians.addAll(userZhaopianList);
                gv_zhaopian.setAdapter(gv_adapter);
                gv_adapter.notifyDataSetChanged();


                gv_zhaopian.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<GridView> refreshView) {

            }
        });




        gv_zhaopian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         /*       Intent intent = new Intent(getApplicationContext(), DaTuPianActivity.class);
                UserZhaopian userZhaopian = userZhaopians.get(i);
                String zhaopian_url1 = NetUtil.url_zhuwei + userZhaopian.getZhaipianUrl();
                intent.putExtra("zhaopian_url", zhaopian_url1);
                System.out.println("准备放大的照片"+zhaopian_url1);
                startActivity(intent);*/
                List<String> urlList =new ArrayList<String>();
                System.out.println("userZhaopians的大小"+userZhaopians.size());
                for(int j=0;j<userZhaopians.size();j++) {
                    urlList.add(NetUtil.url_zhuwei+userZhaopians.get(j).getZhaipianUrl());
                }
                onClickImage(i,urlList);

            }
            protected void onClickImage(int i,  List<String> urlList) {

                if (!urlList.isEmpty()){
                    Intent intent = new Intent(ZhaoPianActivity.this, ImagePagerActivity.class);
                    intent.putCharSequenceArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList) urlList);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, i);
                    ZhaoPianActivity.this.startActivity(intent);
                }

            }

        });



 /*       gv_zhaopian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PicturePreviewActivity.class);
                intent.putExtra("zhaopian_url", zhaopian_url);
                // intent.putExtra("smallPath", getSmallPath());
                intent.putExtra("indentify", getIdentify());
                startActivity(intent);
            }
        });
*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();





    }

    private int getIdentify() {
        return getResources().getIdentifier("test", "drawable",
                getPackageName());
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ZhaoPian Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
