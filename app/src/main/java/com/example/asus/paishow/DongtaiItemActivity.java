package com.example.asus.paishow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.NovEight.CommentAdapter;
import com.example.asus.paishow.NovEight.CommentBean;
import com.example.asus.paishow.NovEight.NoTouchLinearLayout;
import com.example.asus.paishow.NovEight.ReplyBean;
import com.example.asus.paishow.fragments.MainFragment;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.view.NineGridTestLayout;

import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DongtaiItemActivity extends AppCompatActivity {

    ListActivityBean.Dongtai dongtai;
    @InjectView(R.id.iv_returntohome)
    ImageView ivReturntohome;
    @InjectView(R.id.id_prod_list_tv)
    TextView idProdListTv;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.dongtai_title)
    LinearLayout dongtaiTitle;
    @InjectView(R.id.iv_touxiang)
    ImageView ivTouxiang;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_text)
    TextView tvText;
    //    @InjectView(R.id.iv_ha)
//    ImageView ivHa;
//    @InjectView(R.id.iv_shi)
//    ImageView ivShi;
//    @InjectView(R.id.iv_qi)
//    ImageView ivQi;
//    @InjectView(R.id.iv_hashiqi)
//    ImageView ivHashiqi;
//    @InjectView(R.id.ll_zhuanfa)
//    LinearLayout llZhuanfa;
//    @InjectView(R.id.ll_pinglun)
//    LinearLayout llPinglun;
//    @InjectView(R.id.ll_zan)
//    LinearLayout llZan;
//    @InjectView(R.id.gongneng)
//    LinearLayout gongneng;
    private NineGridTestLayout layout_nine_grid;
    private ListView mListData;
    private LinearLayout mLytCommentVG;
    private NoTouchLinearLayout mLytEdittextVG;
    private EditText mCommentEdittext;
    private Button mSendBut;
    private List<CommentBean> list;
    private CommentAdapter adapter;
    private int count;                    //记录评论ID
    private String comment = "";        //记录对话框中的内容
    private int position;                //记录回复评论的索引
    private boolean isReply;            //是否是回复，true代表回复

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dongtai_item_click);
        ButterKnife.inject(this);
        initData();
        initViews();
        adapter = new CommentAdapter(this, getCommentData(), R.layout.comment_item_list, handler);
        mListData.setAdapter(adapter);
    }

    //初始化界面
    public void initData() {
        //获取传过来的dongtaiInfo
        layout_nine_grid =(NineGridTestLayout)findViewById(R.id.layout_nine_grid);
        Intent intent = this.getIntent();
        dongtai = (ListActivityBean.Dongtai) intent.getSerializableExtra("dongtaiInfo");
        if (dongtai != null) {
            x.image().bind(ivTouxiang, dongtai.userImg);
            try {
                tvName.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
                tvText.setText(URLDecoder.decode(dongtai.text,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            tvTime.setText(dongtai.getFabutime().toString());

//            ivHa.setImageResource(R.drawable.ha);
//            x.image().bind(ivHa,dongtai.imgs);
            List<String> urlsList = new ArrayList<String>();
            if(dongtai.getImgs()!=null && !"".equals(dongtai.getImgs())) {
                String[] urls = dongtai.getImgs().split(",");

                if (urls.length > 0) {
                    int length=urls.length;
                    for (int i = 0; i < urls.length; i++) {
                        urlsList.add(urls[i]);

                    }

                    layout_nine_grid.setIsShowAll(true);
                }
            }else if ("".equals(dongtai.getImgs()) && dongtai.getImgs()==null){
                layout_nine_grid.notifyDataSetChanged();

            }
            layout_nine_grid.setUrlList(urlsList);

        }

    }
    /**
     * 初始化控件
     */
    private void initViews() {
        mListData = (ListView) findViewById(R.id.list_data);
        mLytCommentVG = (LinearLayout) findViewById(R.id.comment_vg_lyt);
        mLytEdittextVG = (NoTouchLinearLayout) findViewById(R.id.edit_vg_lyt);
        mCommentEdittext = (EditText) findViewById(R.id.edit_comment);
        mSendBut = (Button) findViewById(R.id.but_comment_send);

        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);
        mLytCommentVG.setOnClickListener(cl);

    }

    /**
     * 获取评论列表数据
     */
    private List<CommentBean> getCommentData() {
        list = new ArrayList<>();
        count = 5;
        for (int i = 0; i < 4; i++) {
            CommentBean bean = new CommentBean();
            bean.setId(i);
            bean.setCommentNickname("周杰伦");
            bean.setCommentTime("13:" + i + "5");
            bean.setCommnetAccount("1234" + i);
            bean.setCommentContent("你拍的好美");
            bean.setReplyList(getReplyData());
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取回复列表数据
     */
    private List<ReplyBean> getReplyData() {
        List<ReplyBean> replyList = new ArrayList<>();
        return replyList;
    }

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        mCommentEdittext.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    mCommentEdittext.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
                    mLytCommentVG.setVisibility(View.VISIBLE);
                    mLytEdittextVG.setVisibility(View.GONE);
                }
            }
        }, 100);
    }

    /**
     * 点击屏幕其他地方收起输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 50;
            int top = leftTop[1] - 50;
            int bottom = top + v.getHeight() + 300;
            int right = left + v.getWidth() + 120;
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = mCommentEdittext.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空~", Toast.LENGTH_SHORT).show();
            return false;
        }
        mCommentEdittext.setText("");
        return true;
    }

    /**
     * 发表评论
     */
    private void publishComment() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        CommentBean bean = new CommentBean();
        bean.setId(count);
        bean.setCommentNickname("童琦seven&周杰伦");

        bean.setCommentTime(str);
        bean.setCommnetAccount("12345" + count);
        bean.setCommentContent(comment);
        list.add(0, bean);//加载到list的最前面
        adapter.addMap(count);
        count++;
        adapter.notifyDataSetChanged();
    }

    private void DelectComment(int postion) {
        list.remove(postion);
        adapter.notifyDataSetChanged();
    }


    /**
     * 回复评论
     */
    private void replyComment() {
        ReplyBean bean = new ReplyBean();
        bean.setId(count + 10);
        bean.setCommentNickname(list.get(position).getCommentNickname());
        bean.setReplyNickname("童琦seven");
        bean.setReplyContent(comment);
        adapter.getReplyComment(bean, position);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 10:
                    isReply = true;
                    position = (Integer) msg.obj;
                    mLytCommentVG.setVisibility(View.GONE);
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    break;
                case 11:
                    isReply = false;
                    position = (Integer)msg.obj;
                    DelectComment(position);
                    break;

            }

        }
    };

    /**
     * 事件点击监听器
     */
    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_send:        //发表评论按钮
                    if (isEditEmply()) {        //判断用户是否输入内容
                        if (isReply) {
                            replyComment();
                        } else {
                            publishComment();
                        }
                        mLytCommentVG.setVisibility(View.VISIBLE);
                        mLytEdittextVG.setVisibility(View.GONE);
                        onFocusChange(false);
                    }
                    break;
                case R.id.comment_vg_lyt:        //底部评论按钮
                    isReply = false;
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    mLytCommentVG.setVisibility(View.GONE);
                    onFocusChange(true);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //判断控件是否显示
        if (mLytEdittextVG.getVisibility() == View.VISIBLE) {
            mLytEdittextVG.setVisibility(View.GONE);
            mLytCommentVG.setVisibility(View.VISIBLE);
        }
    }



    @OnClick({R.id.iv_returntohome, R.id.iv_share, R.id.iv_touxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_returntohome:
//                Intent intent =new Intent(DongtaiItemActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.iv_share:
                Void_ share = new Void_();
                share.showShare(DongtaiItemActivity.this, null, true);
                break;
            case R.id.iv_touxiang:
                break;
//            case R.id.ll_zhuanfa:
//                Void_ share1 = new Void_();
//                share1.showShare(DongtaiItemActivity.this, null, true);
//                break;
//            case R.id.ll_pinglun:
//                break;
//            case R.id.ll_zan:
//                break;
        }
    }
}

