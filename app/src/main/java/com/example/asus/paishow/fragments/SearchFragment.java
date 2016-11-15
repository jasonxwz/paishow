package com.example.asus.paishow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.R;

import com.example.asus.paishow.SearchActivity;
import com.example.asus.paishow.listview.FoldingCellListAdapter;
import com.example.asus.paishow.listview.UserInfoBean;

import com.example.asus.paishow.ramotion.foldingcell.FoldingCell;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


/**
 * Created by asus on 2016/10/9.
 */
public class SearchFragment extends BaseFragment {

    private int imageIds[];
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private int oldPosition =0;//记录上一次点的位置
   private boolean flag=false;
    Button btn_daren ;
    Button btn_renqi ;
    Button btn_shenglv ;


//    final  ArrayList<ListDaRenBean.DaRenPaiHang> daRenList=new ArrayList<ListDaRenBean.DaRenPaiHang>();
//    final  ArrayList<ListRenQiBean.RenQiPaiHang> renQiList=new ArrayList<ListRenQiBean.RenQiPaiHang>();
//    final ArrayList<ListShengLvBean.ShengLvPaiHang> shengLvList=new ArrayList<ListShengLvBean.ShengLvPaiHang>();
    final  ArrayList<UserInfoBean.UserInfo> userInfoList=new ArrayList<UserInfoBean.UserInfo>();


    ViewHolder viewHolder = new ViewHolder();
    private FoldingCellListAdapter darenAdpter;
//    private FoldingCellListAdapter renqiAdpter;
//    private FoldingCellListAdapter shenglvAdpter;
    private Button btn_tosearch;



    int msgWhat=0;
    private ListView theListView;
    private LinearLayout linear_btn;
    private RelativeLayout rl_line3;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view= inflater.inflate(R.layout.search_fragment,null);


        /**
         * 广告部分
         */

        //获取图片
        imageIds = new int[]{
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e
        };

        //显示图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);

            images.add(imageView);
        }

        //显示点
        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.dot_0));
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
        dots.add(view.findViewById(R.id.dot_3));
        dots.add(view.findViewById(R.id.dot_4));


        mViewPager = (ViewPager)view.findViewById(R.id.viewpager);

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1000);  //当前页是第1000页




        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position%5).setBackgroundResource(R.drawable.dot_focused);
                oldPosition = position%5;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * 排行部分
         */


        theListView = ((ListView) view.findViewById(R.id.mainListView));
        linear_btn = ((LinearLayout) view.findViewById(R.id.linear_btn));
        rl_line3 = ((RelativeLayout) view.findViewById(R.id.rl_line3));
//        View header1=rl_line3;
//        View header1=mViewPager;
//
//        theListView.addHeaderView(header1);
//        theListView.addHeaderView(linear_btn);
//        theListView.addHeaderView(LayoutInflater.from(getActivity()).inflate( R.id.linear_btn, null));


//       theListView.addHeaderView(rl_line3);

        theListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return flag;
            }
        });





        darenAdpter=new FoldingCellListAdapter(getActivity(), userInfoList);
//        renqiAdpter=new FoldingCellListAdapter(getActivity(), userInfoList);
//        shenglvAdpter=new FoldingCellListAdapter(getActivity(), userInfoList);

        darenAdpter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag=true;

                Handler x = new Handler();
                x.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flag=false;
                    }
                }, 1500);
                ((FoldingCell) view).toggle(false);

                darenAdpter.registerToggle(position);
//                renqiAdpter.registerToggle(position);
//                shenglvAdpter.registerToggle(position);
            }
        });


        //拿到button
        btn_daren = (Button) view.findViewById(R.id.btn_daren);//达人排行的button
        btn_renqi = (Button) view.findViewById(R.id.btn_renqi);//人气排行的button
        btn_shenglv = (Button) view.findViewById(R.id.btn_shenglv);//胜率的button

//
        //达人排行
        btn_daren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theListView.setAdapter(darenAdpter);
                btn_daren.setSelected(true);
                btn_renqi.setSelected(false);
                btn_shenglv.setSelected(false);
                getDarenList();

            }
        });
//
//
//
//
//
//        //人气排行
         btn_renqi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 theListView.setAdapter(darenAdpter);
                 btn_daren.setSelected(false);
                 btn_renqi.setSelected(true);
                 btn_shenglv.setSelected(false);
                 getRenqiList();
             }
         });



//        //胜率排行
        btn_shenglv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theListView.setAdapter(darenAdpter);

                btn_daren.setSelected(false);
                btn_renqi.setSelected(false);
                btn_shenglv.setSelected(true);
               getShenglvList();
            }
        });


        //通过搜索按钮跳转到搜索界面
        btn_tosearch = ((Button) view.findViewById(R.id.btn_tosearch));
        btn_tosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });





//
        theListView.setAdapter(darenAdpter);

        getDarenList();
//
        btn_daren.setSelected(true);
        return view;
    }

    //拿数据胜率
    private void getShenglvList() {

        RequestParams params=new RequestParams("http://10.40.5.48:8080/paishow/toandroiduserinfo");

        params.addParameter("orderflag",2+"");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();

               // ListShengLvBean shenglvBean=gson.fromJson(result, ListShengLvBean.class);
                UserInfoBean  userInfoBean=gson.fromJson(result,UserInfoBean.class);
                userInfoList.clear();
                userInfoList.addAll(userInfoBean.userinfoList);
                //通知listview跟新界面

                darenAdpter.notifyDataSetChanged();

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


    //拿数据人气
    private void getRenqiList() {
        RequestParams params=new RequestParams("http://10.40.5.48:8080/paishow/toandroiduserinfo");
        params.addParameter("orderflag",1+"");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
               // ListRenQiBean renqiBean=gson.fromJson(result, ListRenQiBean.class);
                UserInfoBean  userInfoBean=gson.fromJson(result,UserInfoBean.class);
                userInfoList.clear();
                userInfoList.addAll(userInfoBean.userinfoList);
                //通知listview跟新界面

                darenAdpter.notifyDataSetChanged();
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

    //拿数据达人
    private void getDarenList() {
        RequestParams params=new RequestParams("http://10.40.5.48:8080/paishow/toandroiduserinfo");
        params.addParameter("orderflag",0+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
              //  ListDaRenBean darenBean=gson.fromJson(result, ListDaRenBean.class);

                UserInfoBean  userInfoBean=gson.fromJson(result,UserInfoBean.class);
                userInfoList.clear();
                userInfoList.addAll(userInfoBean.userinfoList);
                //通知listview跟新界面
                System.out.println("==================111-========="+userInfoList.size());
                darenAdpter.notifyDataSetChanged();
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


    /**
     * 对应广告切换
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
           return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /**
             * position % imageList.size() 而不是position，是为了防止角标越界异常
             * 因为我们设置了viewpager子页面的数量有Integer.MAX_VALUE，而imageList的数量只是5。
             */

            container.addView(images.get(position % images.size()));
            return images.get(position % images.size());
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        //每隔2秒钟切换一张图片
//        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2, 2, TimeUnit.SECONDS);
//
//
//    }
//
//    private class ViewPagerTask implements Runnable {
//
//        @Override
//        public void run() {
//            currentItem = (currentItem + 1) % imageIds.length;
//            //更新界面
//            handler.obtainMessage().sendToTarget();
//
//        }
//    }
//

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //设置当前页面
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(msgWhat,5000);
        }

    };




    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(msgWhat, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(msgWhat);
    }




    //排行部分

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

////达人adpter
//    public  class  daRenAdapter extends  BaseAdapter{
//
//    @Override
//        public int getCount() {
//            return daRenList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view= View.inflate(getActivity(),R.layout.search_daren_listview,null);
//
//
//
//            viewHolder.tv_userName = ((TextView) view.findViewById(R.id.tv_userName));
//            viewHolder.tv_dengjiNum=((TextView) view.findViewById(R.id.tv_dengjiNum));
//            viewHolder.iv_userImage = ((ImageView) view.findViewById(R.id.iv_userImage));
//            ListDaRenBean.DaRenPaiHang paiHang=daRenList.get(position);
//            try {
//                viewHolder.tv_userName.setText(URLDecoder.decode(paiHang.userName,"utf-8"));
//               // tv_dengjiNum.setText(paiHang.dengJiNum);
//                viewHolder.tv_dengjiNum.setText(URLDecoder.decode(paiHang.dengJiNum+"","utf-8"));
//                x.image().bind(viewHolder.iv_userImage, URLDecoder.decode(paiHang.userImg+"","utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            return view;
//        }
//    }
//
//    //人气adpter
//        private class renQiAdapter extends BaseAdapter{
//
//
//
//        @Override
//        public int getCount() {
//            return renQiList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view=View.inflate(getActivity(),R.layout.search_renqi_listview,null);
//            viewHolder.tv_userName1 = ((TextView) view.findViewById(R.id.tv_userName1));
//            viewHolder.tv_renqiNum = ((TextView) view.findViewById(R.id.tv_renqiNum));
//            viewHolder.iv_userImage1 = ((ImageView) view.findViewById(R.id.iv_userImage1));
//            ListRenQiBean.RenQiPaiHang paiHang=renQiList.get(position);
//            try {
//                viewHolder.tv_userName1.setText(URLDecoder.decode(paiHang.userName,"utf-8"));
//                // tv_dengjiNum.setText(paiHang.dengJiNum);
//                viewHolder.tv_renqiNum.setText(URLDecoder.decode(paiHang.renQiNum+"","utf-8"));
//                x.image().bind(viewHolder.iv_userImage1, URLDecoder.decode(paiHang.userImg,"utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            return view;
//        }
//    }
//
//
//    //胜率adpter
//    private class shengLvAdpter extends BaseAdapter{
//
//
//
//        @Override
//        public int getCount() {
//            return shengLvList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view=View.inflate(getActivity(),R.layout.search_shenglv_listview,null);
//            viewHolder.tv_userName2 = ((TextView) view.findViewById(R.id.tv_userName2));
//            viewHolder.tv_shengLvNum = ((TextView) view.findViewById(R.id.tv_shengLvNum));
//            viewHolder.iv_userImage2 = ((ImageView) view.findViewById(R.id.iv_userImage2));
//            ListShengLvBean.ShengLvPaiHang paiHang= shengLvList.get(position);
//
//            try {
//                viewHolder.tv_userName2.setText(URLDecoder.decode(paiHang.userName,"utf-8"));
//
//                viewHolder.tv_shengLvNum.setText(URLDecoder.decode(paiHang.shengLvNum+"","utf-8"));
//
//                x.image().bind(viewHolder.iv_userImage2, URLDecoder.decode(paiHang.userImg+"","utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            return view;
//        }
//     }


      public class ViewHolder{
          TextView tv_userName;
          TextView tv_dengjiNum;
          TextView tv_userName1;
           TextView tv_renqiNum;
          TextView tv_userName2;
           TextView tv_shengLvNum;
          ImageView iv_userImage;
          ImageView iv_userImage1;
          ImageView iv_userImage2;
      }

    }

