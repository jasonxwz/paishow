package com.example.asus.paishow.pubuliu;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.pojo.PKInfoBean;
import com.example.asus.paishow.utils.ViewHolder;
import com.example.asus.paishow.utils.Void_;

import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/25.
 */
public class PuBuAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;


    private static final String TAG = "PuBuAdapter";
    Context context;

    List<PKInfoBean.PKInfo> lists;



    public PuBuAdapter(Context context, List<PKInfoBean.PKInfo> lists) {
        this.context = context;
        this.lists = lists;
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int[] a=new int[]{590,810};
        Log.i(TAG, "加载listview item position:" + position);
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder = new ViewHolder(context,R.layout.fragment_homepage,null);

            // 打气筒  view就是指每一个listview item
            convertView = View.inflate(context, R.layout.pk_item, null);

            viewHolder.tv_name = ((TextView) convertView.findViewById(R.id.tv_name));

            viewHolder.tv_text = ((TextView) convertView.findViewById(R.id.tv_text));

            viewHolder.iv_touxiang = ((ImageView) convertView.findViewById(R.id.iv_touxiang));
            viewHolder.iv_pk1picture = ((ImageView) convertView.findViewById(R.id.iv_pk1picture));


            convertView.setTag(viewHolder);//缓存对象
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        LinearLayout.LayoutParams lay=(LinearLayout.LayoutParams)viewHolder.iv_pk1picture.getLayoutParams();
       if (position%2==0){
           lay.height=a[0];
       }else {
           lay.height=a[1];
       }
        viewHolder.iv_pk1picture.setLayoutParams(lay);
        try {
            viewHolder.tv_name.setText(URLDecoder.decode(((lists.get(position).userName)),"utf-8"));
          //  viewHolder.tv_text.setText(URLDecoder.decode(((lists.get(position).text)),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.image().bind(viewHolder.iv_touxiang, lists.get(position).userTouxiang);
        x.image().bind( viewHolder.iv_pk1picture,lists.get(position).pk1Picture);


        return convertView;
    }

    }






