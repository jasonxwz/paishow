package com.example.asus.paishow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.paishow.CameraActivity;
import com.example.asus.paishow.DongtaiItemActivity;
import com.example.asus.paishow.PkActivity;
import com.example.asus.paishow.R;
import com.example.asus.paishow.SearchActivity;
import com.example.asus.paishow.SentActivity;
import com.example.asus.paishow.ext.SatelliteMenu;
import com.example.asus.paishow.ext.SatelliteMenuItem;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.pulltorefresh.RefreshableView;
import com.example.asus.paishow.utils.ViewHolder;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.view.NineGridTestLayout;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2016/10/9.
 */
public class MainFragment extends BaseFragment{
    private static final String TAG = "MainPageFragment";
    public List<ListActivityBean.Dongtai> dongtaiList = new ArrayList<ListActivityBean.Dongtai>(); //动态信息
    private View view;
    private ListView lv_dongtai;
    private BaseAdapter adapter;
    private ImageView iv_share;
    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter1;
//    NineGridTestModel nineGridTestModel;
    Map<Integer,Boolean> flag = new HashMap<>();
    //记录选中的位置 checkbox 点赞
    Map<Integer,Boolean> checkStatus = new HashMap<>();
    //记录likenum的位置
    Map<Integer,Integer> likenums = new HashMap<>();
    //有没有点过
    Map<Integer,Boolean> isliked=new HashMap<>();
    //是不是最新
    Map<Integer,Boolean> is_now=new HashMap<>();
    Integer likeNum;
    private Button btn_tosearch_home;

    //    List<String> popContents=new ArrayList<String>();
//    private NineGridTestAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, null);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final SatelliteMenu menu = (SatelliteMenu)view.findViewById(R.id.menu);
        System.out.println("++++++++++1+++++++++");
        refreshableView = (RefreshableView)view.findViewById(R.id.refreshable_view);
        listView = (ListView)view.findViewById(R.id.lv_dongtai);
        listView.setAdapter(adapter1);
//        xUtilsImageUtils.display((ImageView) view.findViewById(R.id.iv_touxiang),url,true);  圆型头像
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                    getDongtaiList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

        System.out.println("++++++++++2+++++++++");
//        iv_share = ((ImageView) view.findViewById(R.id.iv_share));
//        iv_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Void_ share = new Void_();
//              share.showShare(getActivity(), null, true);
//            }
//        });

//		  Set from XML, possible to programmatically set
//        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
//        menu.setSatelliteDistance((int) distance);
//        menu.setExpandDuration(500);
//        menu.setCloseItemsOnClick(false);
//        menu.setTotalSpacingDegree(60);

        btn_tosearch_home = ((Button) view.findViewById(R.id.btn_tosearch_home));
        btn_tosearch_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        final List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(6, R.drawable.pk));
//        items.add(new SatelliteMenuItem(5, R.drawable.care));
//        items.add(new SatelliteMenuItem(4, R.drawable.hot));
//        items.add(new SatelliteMenuItem(3, R.drawable.friend));
        items.add(new SatelliteMenuItem(2, R.drawable.text));
//        items.add(new SatelliteMenuItem(1, R.drawable.camera));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);

        menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);
        switch (id){
            case 6:
                Intent intent =new Intent(getActivity(), PkActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent1=new Intent(getActivity(), SentActivity.class);
                startActivity(intent1);
                break;
//            case 1:
//                Intent intent2=new Intent(getActivity(), CameraActivity.class);
//                startActivity(intent2);
//                break;
        }
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_dongtai = ((ListView) view.findViewById(R.id.lv_dongtai));

        adapter = new BaseAdapter() {
            private CheckBox ll_zan;

            @Override
            public int getCount() {
                return dongtaiList.size();
            }

            @Override
            public Object getItem(int i) {
                return dongtaiList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                Log.i(TAG, "加载listview item position:" + position);
                ViewHolder viewHolder=null;
                if (convertView == null) {

                    viewHolder = new ViewHolder(getContext(),R.layout.fragment_homepage,null);

                    // 打气筒  view就是指每一个listview item
                    convertView = View.inflate(getActivity(), R.layout.fragment_home_list_view_item, null);
                    viewHolder.tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
                    viewHolder.tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
                    viewHolder.tv_text = ((TextView) convertView.findViewById(R.id.tv_text));
                    viewHolder.layout = (NineGridTestLayout) convertView.findViewById(R.id.layout_nine_grid);
                    viewHolder.iv_touxiang = ((ImageView) convertView.findViewById(R.id.iv_touxiang));
                    viewHolder.ll_share = ((LinearLayout)convertView.findViewById(R.id.ll_share));
//                    viewHolder.ll_zan = ((CheckBox) convertView.findViewById(R.id.ll_zan));
//                    ll_zan.setOnClickListener();
                    System.out.println("+_+_+_+_+_5+_+_+_+_"+viewHolder.ll_share);
                    viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("++++++++3+++++++++++");
                            Void_ share = new Void_();
                            share.showShare(getActivity(), null, true);
                        }
                    });
                    convertView.setTag(viewHolder);//缓存对象
                }else{
                    viewHolder = (ViewHolder)convertView.getTag();
                }
                ListActivityBean.Dongtai dongtai = dongtaiList.get(position);
//                try {
//                    viewHolder.tv_title.setText(URLDecoder.decode(dongtai.text,"utf-8"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                try {
                    viewHolder.tv_text.setText(URLDecoder.decode(dongtai.text,"utf-8"));
                    viewHolder.tv_name.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
                    System.out.println("======================="+viewHolder.tv_name.getText());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                viewHolder.tv_time.setText(dongtai.fabutime.toString());


//                viewHolder.iv_ha.setImageResource(R.drawable.ha);
             //  urls = new String[9];
                List<String> urlsList = new ArrayList<String>();
                if(dongtai.getImgs()!=null && !"".equals(dongtai.getImgs())) {
                    String[] urls = dongtai.getImgs().split(",");

                    if (urls.length > 0) {
                        int length=urls.length;
                        for (int i = 0; i < urls.length; i++) {
                            urlsList.add(urls[i]);

                        }
                        viewHolder.layout.setIsShowAll(dongtaiList.get(position).isShowAll);
                    }
                }else if ("".equals(dongtai.getImgs()) && dongtai.getImgs()==null){
                    viewHolder.layout.notifyDataSetChanged();

                }
                viewHolder.layout.setUrlList(urlsList);


                    x.image().bind(viewHolder.iv_touxiang, dongtai.userImg);


                return convertView;
            }


//        };

        };
        lv_dongtai.setAdapter(adapter);
        getDongtaiList();
        //lv_dongtai的item点击事件
        lv_dongtai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到动态详情页面
                Log.i(TAG, "onItemClick: ==============");
                Intent intent=new Intent(getActivity(), DongtaiItemActivity.class);
                intent.putExtra("dongtaiInfo",dongtaiList.get(position));
                System.out.println("dongtaiList.get(position)"+dongtaiList.get(position).getImgs());
                startActivity(intent);
            }
        });



    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
//        //lv_dongtai的item点击事件
//        lv_dongtai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //跳转到动态详情页面
//                Intent intent=new Intent(getActivity(), DongtaiItemActivity.class);
//                intent.putExtra("dongtaiInfo",dongtaiList.get(position));
//                startActivity(intent);
//    }
//        });

    }

    @Override
    public void initData() {
//        popContents.add("价格从高到底");
//        popContents.add("价格从低到高");
    }



    private void  getDongtaiList() {
        RequestParams params = new RequestParams("http://10.40.5.40:8080/paishow/toandroid");

        //tempName
        params.addQueryStringParameter("tempName","");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                Gson gson = new Gson();
                dongtaiList.clear();
                ListActivityBean bean = gson.fromJson(result, ListActivityBean.class);
                dongtaiList.addAll(bean.dongtaiList);
                System.out.println("============================="+dongtaiList);
                Log.i("MainpageFragment", "dongtais: " + dongtaiList.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=============================");
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