package com.example.asus.paishow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.AddContactActivity;
import com.example.asus.paishow.R;
import com.example.asus.paishow.fragments.message.ContantListFragment;
import com.example.asus.paishow.fragments.message.ConversationListDynamicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/9.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {


    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem;
//    private ArrayList<View> texts;
    private TextView[] texts;
    private TextView tv_addContact;
    private TextView tv_message;
    private TextView tv_contacts;
    private ViewPager mViewPage;
    private List<Fragment> mFragments = new ArrayList<>(2);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, null);
        mViewPage = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPage.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));

        texts = new TextView[2];
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_contacts = (TextView) view.findViewById(R.id.tv_contacts);
        tv_addContact = (TextView) view.findViewById(R.id.tv_add_contact);
        tv_message.setOnClickListener(this);
        tv_contacts.setOnClickListener(this);
        tv_addContact.setOnClickListener(this);
        texts[0] = tv_message;
        texts[1] = tv_contacts;


        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    texts[0].setSelected(false);
                    texts[0].setTextSize(15);
                    texts[1].setSelected(true);
                    texts[1].setTextSize(18);
                }else {
                    texts[1].setSelected(false);
                    texts[1].setTextSize(15);
                    texts[0].setSelected(true);
                    texts[0].setTextSize(18);
                }
                oldPosition = position;
                currentItem = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_message:
                mViewPage.setCurrentItem(0, false);
                texts[0].setTextSize(18);
                texts[1].setTextSize(15);
                break;
            case R.id.tv_contacts:
                mViewPage.setCurrentItem(1, false);
                texts[0].setTextSize(15);
                texts[1].setTextSize(18);
                break;
            case R.id.tv_add_contact:
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);

        }

    }

    public class PagerAdapter extends FragmentPagerAdapter {


        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments.add(ConversationListDynamicFragment.getInstance());
            mFragments.add(ContantListFragment.getInstance());
        }

        @Override
        public Fragment getItem(int i) {
            System.out.println(i);
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
