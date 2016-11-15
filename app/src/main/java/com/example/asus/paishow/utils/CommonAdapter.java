package com.example.asus.paishow.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    Context context;
    List<T> lists;
    int layoutId;
    public CommonAdapter(Context context, List<T> lists, int layoutId){

        this.context=context;
        this.lists=lists;
        this.layoutId=layoutId;

    }

    @Override
    public int getCount() {
        return (lists!=null)? lists.size():0;
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //找到控件，赋值
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=ViewHolder.get(context,layoutId,convertView,parent);
        convert(viewHolder,lists.get(i),i);
        return viewHolder.getConverView();
    }
    //取出控件，赋值
    public abstract void convert(ViewHolder viewHolder,T t,int i);
}
