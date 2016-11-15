package com.example.asus.paishow.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by acer on 2016/10/8.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgc.length;
    }

    @Override
    public Object getItem(int position) {
        return imgc[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(context);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(lp);
        }
        //
        ImageView view = (ImageView) convertView;
       view.setImageResource(imgc[position]);
        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        lp.height = (int) (getPositionRatio(position) * 600);
        view.setLayoutParams(lp);
        return view;
    }
    private final static int[] imgc=new int[]{
            R.drawable.kk, R.drawable.hh,R.drawable.kk,R.drawable.hh,R.drawable.kk,
            R.drawable.hh,R.drawable.kk,R.drawable.hh,R.drawable.kk,R.drawable.kk,R.drawable.kk,
            R.drawable.hh,R.drawable.kk,R.drawable.hh,R.drawable.kk,R.drawable.kk
    };

    //根据位置随机获得高度
  private final Random mRandom = new Random();
   private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
       if (ratio == 0) {
            ratio = getRandomHeightRatio();
           sPositionHeightRatios.append(position, ratio);
       }
       return ratio;
    }

    private double getRandomHeightRatio() {
       return (mRandom.nextDouble() / 4) + 1.0; // height will be 1.0 - 1.5 the width
   }
}
