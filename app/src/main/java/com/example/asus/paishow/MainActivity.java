package com.example.asus.paishow;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.paishow.application.MyApplication;
import com.example.asus.paishow.fragments.MainFragment;
import com.example.asus.paishow.fragments.MessageFragment;
import com.example.asus.paishow.fragments.MineFragment;
import com.example.asus.paishow.fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {
    Fragment[] fragments;
    MainFragment mainFragment;//首页
    MessageFragment messageFragment;//消息
    SearchFragment searchFragment;//搜索
    MineFragment mineFragment;//我的
    //按钮的数组，一开始第一个按钮被选中

    Button[] tabs;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    private long exitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化fragment
        mainFragment = new MainFragment();
        messageFragment = new MessageFragment();
        searchFragment = new SearchFragment();
        mineFragment = new MineFragment();

        //所有fragment的数组
        fragments = new Fragment[]{mainFragment, messageFragment, searchFragment, mineFragment};


        //设置按钮的数组
        tabs = new Button[4];

        tabs[0] = (Button) findViewById(R.id.btn_main);//主页的button
        tabs[1] = (Button) findViewById(R.id.btn_message);//消息的button
        tabs[2] = (Button) findViewById(R.id.btn_search);//搜索的button
        tabs[3] = (Button) findViewById(R.id.btn_mine);//我的button

        //界面初始显示第一个fragment;添加第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).commit();
        //初始时，按钮1选中`
        tabs[0].setSelected(true);



    }


    //按钮的点击事件:选中不同的按钮，不同的fragment显示
    public void onTabClicked(View view) {

        //点击按钮时，表示选中不同的项
        switch (view.getId()) {
            case R.id.btn_main:
                newIndex = 0;//选中第一项
                break;

            case R.id.btn_message:
                newIndex = 1;//选中第二项
                break;
            case R.id.btn_search:
                newIndex = 2;//选中第三项
                break;
            case R.id.btn_mine:
                newIndex = 3;//选中第四项
                break;


        }
        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if (newIndex != oldIndex) {

            transaction = getSupportFragmentManager().beginTransaction();

            transaction.hide(fragments[oldIndex]);//隐藏当前显示项


            //如果选中项没有加过，则添加
            if (!fragments[newIndex].isAdded()) {
                //添加fragment
                transaction.add(R.id.fragment_container, fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);


        //当前选择项变为选中项
        oldIndex = newIndex;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}