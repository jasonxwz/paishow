package com.example.ssnn.xiugaiziliao;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XiuGaiShengRiActivity extends AppCompatActivity {

    private TextView tv_xuanze_shengri;
    private DatePicker dp_xuanze_shengri;
    private User user;
    private String userShengri;
    private TextView tv_shengri_baocun;
    private ImageView iv_xiugaishengri_fanhui;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_sheng_ri);
        final Intent intent = this.getIntent();
        user=(User)getIntent().getSerializableExtra("user");

        tv_xuanze_shengri = ((TextView) findViewById(R.id.tv_xuanze_shengri));
        dp_xuanze_shengri = ((DatePicker) findViewById(R.id.dp_xuanze_shengri));
        tv_shengri_baocun = ((TextView) findViewById(R.id.tv_shengri_baocun));
        iv_xiugaishengri_fanhui = ((ImageView) findViewById(R.id.iv_xiugaishengri_fanhui));

        tv_xuanze_shengri.setText(user.getUserShengri());
            userShengri = user.getUserShengri();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;

            try {
                date = format.parse(userShengri);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("%%%%%%%%%%%%%%%%"+date);
            int year =  date.getYear();
            int month = date.getMonth();
            int day = date.getDay()-1;




            dp_xuanze_shengri.init(year,month,day, new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    userShengri = i+"-"+(i1+1)+"-"+i2;
                    tv_xuanze_shengri.setText(userShengri);
                    user.setUserShengri(userShengri);
                }
            });
            tv_shengri_baocun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = intent.getExtras();
                    bundle.putSerializable("user", user);//添加要返回给页面1的数据
                    intent.putExtras(bundle);
                    XiuGaiShengRiActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                    XiuGaiShengRiActivity.this.finish();
                }
            });
            iv_xiugaishengri_fanhui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }
    }

