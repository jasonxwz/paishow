package com.example.asus.paishow.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.example.asus.paishow.view.NineGridTestLayout;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ViewHolder {

    View converView;
    SparseArray<View> sparseArray; //key:int;value:View
    public TextView tv_title;
    public TextView tv_name;
    public TextView tv_time;
    public TextView tv_text;
    //public ImageView iv_ha;
    public ImageView iv_touxiang;
    public LinearLayout ll_share;
    public ImageView iv_pkbtn;
    public CheckBox ll_zan;
    public ImageView iv_pk1picture;

    public NineGridTestLayout layout;
    public ImageView iv_pinglun;
    //返回viewholder关联的convertview
    public View getConverView(){
        return converView;
    }

    public ViewHolder(Context context, int layoutId, ViewGroup parent){
        //解析布局文件
        this.converView= LayoutInflater.from(context).inflate(layoutId,null);
        converView.setTag(this);
        sparseArray =new SparseArray<View>();

    }

    //获取viewholder对象
    public static ViewHolder get(Context context, int layoutId, View converView, ViewGroup parent){
        ViewHolder viewHolder;
        if(converView==null){
            viewHolder=new ViewHolder(context,layoutId,parent);//创建对象

        }else {
            viewHolder =(ViewHolder)converView.getTag();//获取viewHolder
        }
        return viewHolder;
    }
    //根据id查找view
    public <T extends View>T getViewById(int resourceId){
        View v =sparseArray.get(resourceId);
        //没有找到view
        if(v==null){
            v=converView.findViewById(resourceId);
            sparseArray.put(resourceId,v);
        }
        return (T)v;
    }
}
