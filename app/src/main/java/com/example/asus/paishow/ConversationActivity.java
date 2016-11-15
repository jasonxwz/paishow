package com.example.asus.paishow;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConversationActivity extends FragmentActivity {
    private TextView mName;
    private LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        String name = getIntent().getData().getQueryParameter("title");
        String id = getIntent().getData().getQueryParameter("targetId");
        Log.e("userMsg", "onCreate: " + name +" id" + id );
        mName = (TextView) findViewById(R.id.tv_contact_name);
        ll_back = (LinearLayout) findViewById(R.id.ll_con_back);
        if (!TextUtils.isEmpty(name)){
            mName.setText(name);
        }else {

        }
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
