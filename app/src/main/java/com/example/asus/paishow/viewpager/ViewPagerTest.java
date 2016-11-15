package com.example.asus.paishow.viewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.paishow.LoginActivity;
import com.example.asus.paishow.MainActivity;
import com.example.asus.paishow.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTest extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vp;
    int previoutsPosition_vp =0;
    private Button btn_tiaoguo;

    //保存用户浏览状态
    @Override
    protected void onPause() {
        super.onPause();
        //申明一个偏好设置对象
        SharedPreferences sharedPreferences =getSharedPreferences("save",0);
        //向偏好设置文件中保存用户浏览状态
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean("isFirst",false);
        //保存修改结果
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        //取出用户保存的浏览状态, 0代表当前用户才能访问
        SharedPreferences sharedPreferences =getSharedPreferences("save",0);
        boolean isFirstTime =sharedPreferences.getBoolean("isFirst",true);
        if(!isFirstTime){
            Intent intent =new Intent(ViewPagerTest.this, MainActivity.class);
            startActivity(intent);
            //结束引导页
            finish();
        }

        btn_tiaoguo = ((Button) findViewById(R.id.btn_tiaoguo));
        btn_tiaoguo.setOnClickListener(this);
        final  int[] imgs ={R.id.iv_iv1,R.id.iv_iv2,R.id.iv_iv3,R.id.iv_iv4};

        vp = ((ViewPager) findViewById(R.id.vp));
        List<Integer> imgsrc =new ArrayList<Integer>();
        imgsrc.add(0,R.drawable.start_i1);
        imgsrc.add(1,R.drawable.start_i2);
        imgsrc.add(2,R.drawable.start_i3);
        imgsrc.add(3,R.drawable.start_i4);
        MyPageAdapter pageAdapter =new MyPageAdapter(imgsrc);
        vp.setAdapter(pageAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //将position位置的换成红色，其他都灰色
                /*for(int i =0;i<imgs.length;i++){
                    ((ImageView) findViewById(imgs[i])).setImageResource(R.drawable.point_gray);
                    if(i==position){
                        ((ImageView) findViewById(imgs[i])).setImageResource(R.drawable.point_red);*/
                ((ImageView) findViewById(imgs[position])).setImageResource(R.drawable.point_red);
                ((ImageView) findViewById(imgs[previoutsPosition_vp])).setImageResource(R.drawable.point_gray);

                previoutsPosition_vp=position;

                //处理按钮
                if(position==imgs.length-1){
                    btn_tiaoguo.setVisibility(View.VISIBLE);
                }else{
                    btn_tiaoguo.setVisibility(View.GONE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent(ViewPagerTest.this, LoginActivity.class);
        startActivity(intent);
    }
    private class MyPageAdapter extends PagerAdapter {

        List<Integer> imgsrc;

        public MyPageAdapter(List<Integer> imgsrc){

            this.imgsrc=imgsrc;
        }

        @Override
        public int getCount() {

            return imgsrc.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view =View.inflate(getApplicationContext(),R.layout.viewpager_item,null);
            ImageView iv_vp_item=((ImageView)view.findViewById(R.id.iv_vp_item));
            iv_vp_item.setImageResource(imgsrc.get(position));
            container.addView(view);
            return  view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}