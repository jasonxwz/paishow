package com.example.ssnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.ImageView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.utils.TouchImageView;

import org.xutils.x;




public class DaTuPianActivity extends AppCompatActivity {
    private String zhaopian_url;
    private TouchImageView iv_datupian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_tu_pian);
        zhaopian_url =  (String) getIntent().getSerializableExtra("zhaopian_url");
        iv_datupian = ((TouchImageView) findViewById(R.id.iv_datupian));
        x.image().bind(iv_datupian,zhaopian_url);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);

//        iv_datupian.initImageView(dm.widthPixels, dm.heightPixels - 80);
//        iv_datupian.


    }


}
