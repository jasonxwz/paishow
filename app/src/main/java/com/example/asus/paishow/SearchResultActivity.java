package com.example.asus.paishow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.utils.MyAdapter;
import com.example.asus.paishow.utils.ViewHolder;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.view.NineGridTestLayout;
import com.example.asus.paishow.widiget.SildingFinishLayout;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ListView lv_searchresult;
    private BaseAdapter adapter;
    private EditText et_search;
    private String tempName;
    final ArrayList< ListActivityBean.Dongtai> dongtaiListBean=new ArrayList< ListActivityBean.Dongtai>();



//    手指向右滑动时的最小速度
//    private static final int XSPEED_MIN = 1000;
//
//    //手指向右滑动时的最小距离
//    private static final int XDISTANCE_MIN = 150;
//
//    //记录手指按下时的横坐标。
//    private float xDown;
//
//    //记录手指移动时的横坐标。
//    private float xMove;
//
//    //用于计算手指滑动的速度。
//  private VelocityTracker mVelocityTracker;

   // private com.example.asus.paishow.widiget.SildingFinishLayout sf_cehua;
    private Button btn_back;
    private TextView tv_searchtext;
    private Button btn_backtosearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent =this.getIntent();
        tempName = intent.getStringExtra("tempName");



       // System.out.println(tempName);
        initView();
        initData();
        initEvent();

    }




    private void initView() {
        lv_searchresult = ((ListView) findViewById(R.id.lv_searchresult));
      //  sfl = ((com.example.asus.paishow.widiget.SildingFinishLayout) findViewById(R.id.sfl));
        btn_back = ((Button) findViewById(R.id.btn_back));
        tv_searchtext = ((TextView) findViewById(R.id.tv_searchtext));
        btn_backtosearch = ((Button) findViewById(R.id.btn_backtosearch));


        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sf_cehua);

        mSildingFinishLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {


                    @Override
                    public void onSildingFinish() {

                            SearchResultActivity.this.finish();
                             // overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                        }

                });
        mSildingFinishLayout.setTouchView(lv_searchresult);//绑定的一个控件，这里我用的是布局里面定义ListView,


    }

    private void initEvent() {
        adapter=new SearchAdapter();
        lv_searchresult.setAdapter(adapter);


        lv_searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到动态详情页面
               // Log.i(TAG, "onItemClick: ==============");
                Intent intent=new Intent(getApplicationContext(), DongtaiXiangxi.class);
                intent.putExtra("dongtaiInfo",dongtaiListBean.get(position));
                System.out.println("dongtaiList.get(position)"+dongtaiListBean.get(position).getImgs());
                startActivity(intent);
            }
        });


        //rl_search.setOnTouchListener(this);
       // lv_searchresult.setOnTouchListener(this);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SearchResultActivity.this,SearchActivity.class);
                finish();
//                startActivity(intent);


            }
        });

        btn_backtosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        RequestParams params=new RequestParams("http://10.40.5.48:8080/paishow/toandroiddongtai");
       // System.out.println(tempName);
        params.addParameter("tempName",tempName);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
               ListActivityBean dongtaiBean= gson.fromJson(result,ListActivityBean.class);
                dongtaiListBean.clear();
                dongtaiListBean.addAll(dongtaiBean.dongtaiList);
                adapter.notifyDataSetChanged();
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

        if(tempName.length()<=10) {
            tv_searchtext.setText(tempName);
        }else if(tempName.length()>10){
            tv_searchtext.setText(tempName.substring(0,10)+"...");
        }
    }



    //设置onTouch事件
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        System.out.println("+++++111++__+++_");
//        createVelocityTracker(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                xDown = event.getRawX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                System.out.println("+++++++__+++_");
//                xMove = event.getRawX();
//                //活动的距离
//                int distanceX = (int) (xMove - xDown);
//                //获取顺时速度
//                int xSpeed = getScrollVelocity();
//                //当滑动的距离大于设定的最小距离且滑动的瞬间速度大于设定的速度时，返回到上一个activity
//                if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
//                    finish();
//                    //设置切换动画，从右边进入，左边退出
//                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                recycleVelocityTracker();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//    /**
//     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
//     *
//     * @param event
//     *
//     */
//    private void createVelocityTracker(MotionEvent event) {
//        if (mVelocityTracker == null) {
//            mVelocityTracker = VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);
//    }
//
//    /**
//     * 回收VelocityTracker对象。
//     */
//    private void recycleVelocityTracker() {
//        mVelocityTracker.recycle();
//        mVelocityTracker = null;
//    }




//    /**
//     * 获取手指在content界面滑动的速度。
//     *
//     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
//     */
//    private int getScrollVelocity() {
//        mVelocityTracker.computeCurrentVelocity(1000);
//        int velocity = (int) mVelocityTracker.getXVelocity();
//        return Math.abs(velocity);
//    }




    //adapter
    public class SearchAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return dongtaiListBean.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolderSearch viewHolder=null;
//           View view= View.inflate(SearchResultActivity.this,R.layout.search_result_item,null);
//            viewHolder.tv_title = ((TextView) view.findViewById(R.id.tv_title));
//            viewHolder.tv_name = ((TextView)view.findViewById(R.id.tv_name));
//            viewHolder.tv_time = ((TextView) view.findViewById(R.id.tv_time));
//            viewHolder.tv_text = ((TextView) view.findViewById(R.id.tv_text));
//            viewHolder.iv_ha = ((ImageView)view.findViewById(R.id.iv_ha));
//            viewHolder.iv_touxiang = ((ImageView) view.findViewById(R.id.iv_touxiang));
//            viewHolder.ll_share = ((LinearLayout)view.findViewById(R.id.ll_share));
//            ListActivityBean.Dongtai dongtaiBean=dongtaiListBean.get(position);
//
//            try {
//                viewHolder.tv_title.setText(URLDecoder.decode(dongtaiBean.title,"utf-8"));
//                viewHolder.tv_name.setText(URLDecoder.decode(dongtaiBean.userName,"utf-8"));
//                viewHolder.tv_time.setText(URLDecoder.decode(dongtaiBean.fabutime,"utf-8"));
//                viewHolder.tv_text.setText(URLDecoder.decode(dongtaiBean.text,"utf-8"));
//                x.image().bind(viewHolder.iv_touxiang,URLDecoder.decode(dongtaiBean.userImg,"utf-8"));
//                viewHolder.iv_ha.setImageResource(R.drawable.ha);
//                viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("++++++++3+++++++++++");
//                        Void_ share = new Void_();
//                        share.showShare(SearchResultActivity.this, null, true);
//                    }
//                });
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            return view;
//        }

            //Log.i(TAG, "加载listview item position:" + position);
            ViewHolder viewHolder=null;
            if (convertView == null) {

                viewHolder = new ViewHolder(getApplicationContext(),R.layout.fragment_homepage,null);

                // 打气筒  view就是指每一个listview item
                convertView = View.inflate(getApplicationContext(), R.layout.fragment_home_list_view_item, null);
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
                        share.showShare(getApplicationContext(), null, true);
                    }
                });
                convertView.setTag(viewHolder);//缓存对象
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            ListActivityBean.Dongtai dongtai = dongtaiListBean.get(position);
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
                    viewHolder.layout.setIsShowAll(dongtaiListBean.get(position).isShowAll);
                }
            }else if ("".equals(dongtai.getImgs()) && dongtai.getImgs()==null){
                viewHolder.layout.notifyDataSetChanged();

            }
            viewHolder.layout.setUrlList(urlsList);


            x.image().bind(viewHolder.iv_touxiang, dongtai.userImg);


            return convertView;
        }
    }

    }
