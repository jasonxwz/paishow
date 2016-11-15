package com.example.asus.paishow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.paishow.application.MyApplication;
import com.example.asus.paishow.pojo.Dongtai;
import com.example.asus.paishow.pojo.ListActivityBean;
import com.example.asus.paishow.pojo.ResponseEntity;
import com.example.asus.paishow.utils.DateUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.MediaStoreHelper;
import me.iwf.photopicker.widget.MultiPickResultView;
import me.iwf.photopicker.widget.PhotoAdapter;


public class SentActivity extends AppCompatActivity {



    private boolean isSend=true;

    @InjectView(R.id.iv_returntohome)
    ImageView ivReturntohome;
    //    @InjectView(R.id.tv_text)
//    TextView tvText;
    @InjectView(R.id.iv_sent)
    ImageView ivSent;
    @InjectView(R.id.et_shuru)
    EditText etShuru;


    MultiPickResultView recyclerView;
    private ArrayList<String> photoList;
    List<File> fileList;
    File file;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
//            getPhotoFileName());
//
//    private static final int CAMERA_REQUEST = 1;
//    private static final int PHOTO_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view2);

        recyclerView.init(this, MultiPickResultView.ACTION_SELECT, null);
        ButterKnife.inject(this);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        recyclerView.onActivityResult(requestCode, resultCode, data);

        photoList = recyclerView.getPhotos();
        for (int i = 0; i < photoList.size(); i++) {
            System.out.println("367583" + photoList.get(i));

        }

    }



    @OnClick({R.id.iv_returntohome, R.id.iv_sent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_returntohome:
//                Toast.makeText(getApplicationContext(),"asd",Toast.LENGTH_SHORT).show();
                showDialog("确认退出编辑？");
//                Intent intent=new Intent(SentActivity.this,MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_sent:
                System.out.println("++++++++++++++++++++++");

                if (etShuru.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"评论不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                    doPublish();
                    finish();
                }

                break;

        }
    }

    private void doPublish() {
        RequestParams requestParams = new RequestParams("http://10.40.5.40:8080/paishow/publish");
        Dongtai dongtai = new Dongtai();
        dongtai.setUserId(((MyApplication)getApplication()).getUser().getUserId());
        dongtai.setText(etShuru.getText().toString());



//        dongtai.setImgs("http://10.40.5.40:8080/paishow/imgs/3.jpg");



        Gson gson = new Gson();
        String gsonStr = gson.toJson(dongtai);

        requestParams.addBodyParameter("fileName","fileName");
        if (photoList != null) {
            for (int i = 0; i < photoList.size(); i++) {
                requestParams.addBodyParameter("file", new File(photoList.get(i)));
            }
        }

        requestParams.addQueryStringParameter("dongtai", gsonStr);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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

    private void showDialog(String s) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(s);
        builder.setMessage("");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }


}
