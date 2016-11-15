package com.example.ssnn.wodefragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.pojo.UserShiguangzhou;
import com.example.asus.paishow.pojo.UserXiangce;
import com.example.asus.paishow.pojo.UserZhaopian;
import com.example.asus.paishow.utils.MyGridView;
import com.example.asus.paishow.utils.MyListView;
import com.example.asus.paishow.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;


public class XiangCeFragment extends Fragment {
    private String userId;
    private BaseAdapter  listview_adapter;
    private List<UserZhaopian> userZhaopians;
    private List<UserXiangce> userXiangces;
    private ListView lv_xiangce;
    private UserZhaopian userZhaopian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_xiang_ce,null);
        lv_xiangce = ((ListView) view.findViewById(R.id.lv_xiangce));
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            userId = bundle.getString("str");
            System.out.println("相册里面的："+userId);
        }



        listview_adapter = new BaseAdapter() {
            private GridView gv_zhaopian;
            private TextView tv_xiangce_riqi;
            private TextView tv_xiangce_biaoti;

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
                BaseAdapter gridview_adapter =new BaseAdapter() {
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
                        view=View.inflate(getActivity(),R.layout.layout_item_zhaopian,null);
                        iv_xiangce_zhaopian = ((ImageView) view.findViewById(R.id.iv_xiangce_zhaopian));
                        UserZhaopian userZhaopian = userZhaopians.get(i);
                        String zhaopian_url = NetUtil.url_zhuwei+userZhaopian.getZhaipianUrl();
                        if(iv_xiangce_zhaopian.getDrawable()==null) {
                            x.image().bind(iv_xiangce_zhaopian, zhaopian_url);
                        }

                        return view;
                    }
                };

                view = View.inflate(getActivity(),R.layout.layout_item_xiangce,null);
                tv_xiangce_biaoti = ((TextView) view.findViewById(R.id.tv_xiangce_biaoti));
                tv_xiangce_riqi = ((TextView) view.findViewById(R.id.tv_xiangce_riqi));
//                gv_zhaopian = ((GridView) view.findViewById(R.id.gv_zhaopian));
                UserXiangce userXiangce = userXiangces.get(i);
                String xiangceId = userXiangce.getXiangceId().toString();
                System.out.println("相册"+(i+1)+"的id"+xiangceId);
                tv_xiangce_biaoti.setText(userXiangce.getXiangceMing());
                tv_xiangce_riqi.setText(userXiangce.getXiangceShijian());
                userZhaopians= userXiangce.getUserZhaopians();

                gv_zhaopian.setAdapter(gridview_adapter);
                gridview_adapter.notifyDataSetChanged();





                return view;
            }
        };

        RequestParams params = new RequestParams(NetUtil.url_zhuwei + "UserXiangceServlet");
        params.addQueryStringParameter("userId", userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<UserXiangce>>() {
                }.getType();
                userXiangces = gson.fromJson(result, type);
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
        return view;
    }
}
