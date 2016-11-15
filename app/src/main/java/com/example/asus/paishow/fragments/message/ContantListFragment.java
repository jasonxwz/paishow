package com.example.asus.paishow.fragments.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.paishow.DongtaiItemActivity;
import com.example.asus.paishow.R;
import com.example.asus.paishow.fragments.BaseFragment;
import com.example.asus.paishow.pojo.Contact;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.ViewHolder;
import com.example.asus.paishow.utils.Void_;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by msi on 2016/10/21.
 */
public class ContantListFragment extends BaseFragment {


    private static final String TAG = "ContantListFragment";

    List<Contact> contacts = new ArrayList<Contact>();
    String phone = "";
    String userId = "";
    private ListView lv_message;
    private BaseAdapter adapter;

    public static ContantListFragment instance = null;

    public static ContantListFragment getInstance() {
        if (instance == null){
            instance = new ContantListFragment();
        }
        return instance;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_contact, container, false);

        initData();

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_message = ((ListView) getView().findViewById(R.id.lv_message));
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return contacts.size();
            }

            @Override
            public Object getItem(int i) {
                return contacts.get(i);
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
                    viewHolder = new ViewHolder(getContext(),R.layout.layout_contact,null);

                    // 打气筒  view就是指每一个listview item
                    convertView = View.inflate(getActivity(), R.layout.layout_item_list_contact, null);
                    viewHolder.iv_touxiang = (ImageView) convertView.findViewById(R.id.iv_contact_touxiang);
                    viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_contact_nickname);

                    convertView.setTag(viewHolder);//缓存对象
                }else{
                    viewHolder = (ViewHolder)convertView.getTag();
                }
                ImageOptions options = new ImageOptions.Builder()
                        .setCircular(true).setUseMemCache(true).build();
                Contact contact = contacts.get(position);
                viewHolder.tv_name.setText(contact.getContactName());
                x.image().bind(viewHolder.iv_touxiang, contact.getContactImg(), options);


                return convertView;
            }


//        };

        };
        lv_message.setAdapter(adapter);
        getAllContact();
        //lv_dongtai的item点击事件
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Contact bean = contacts.get(position - 1);
                phone = contacts.get(position).getPhoneNum();
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(contacts.get(position).getPhoneNum(), contacts.get(position).getContactName(), Uri.parse(contacts.get(position).getContactImg())));
                RongIM.getInstance().startPrivateChat(getActivity(), phone, contacts.get(position).getContactName());
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Intent intent = getActivity().getIntent();
        userId = String.valueOf(intent.getIntExtra("userId", 0));
    }

    private void getAllContact() {
        RequestParams params = new RequestParams("http://10.40.5.57:8080/MyPaishow/getallcontact");
        params.addQueryStringParameter("userId", userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + result);
                Gson gson = new Gson();
                contacts.clear();
                System.out.println("1111111111111111111111" );
                contacts = gson.fromJson(result, new TypeToken<List<Contact>>(){}.getType());
                System.out.println("22222222222222222222222222222222");
                System.out.println("0000000000000000000000000"+contacts.get(0).getPhoneNum());
                System.out.println("0000000000000000000000000"+contacts.get(0).getContactName());
                System.out.println("0000000000000000000000000"+contacts.get(0).getContactImg());
                Log.i("ContantListFragment", "contactlist: " + contacts.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=============================" + ex);
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
