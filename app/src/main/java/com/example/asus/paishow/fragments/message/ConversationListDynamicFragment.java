package com.example.asus.paishow.fragments.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.paishow.R;
import com.example.asus.paishow.fragments.BaseFragment;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


/**
 * Created by msi on 2016/10/21.
 */
public class ConversationListDynamicFragment extends BaseFragment {

    public static ConversationListDynamicFragment instance = null;

    private String phoneNum = "";
    private String userImg = "";
    private String userName = "";

    public static ConversationListDynamicFragment getInstance() {
        if (instance == null){
            instance = new ConversationListDynamicFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_message, container, false);
        initData();
        System.out.println(userImg + "123546879");
//12        RongIM.getInstance().refreshUserInfoCache(new UserInfo(phoneNum, userName, Uri.parse(userImg)));
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rong_content, conversationListFragment);
        fragmentTransaction.commit();

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
        Intent intent = getActivity().getIntent();
        userName = intent.getStringExtra("userName");
        userImg = intent.getStringExtra("userImg");
        phoneNum = intent.getStringExtra("phoneNum");
    }
}
