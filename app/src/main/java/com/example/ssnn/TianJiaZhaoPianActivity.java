package com.example.ssnn;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.UserXiangce;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.PhotoAdapter;
import com.example.asus.paishow.utils.RecyclerItemClickListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.widget.MultiPickResultView;
;

public class TianJiaZhaoPianActivity extends AppCompatActivity {
    private UserXiangce userXiangce;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    List<File> fileList;
    private int currentClickId = -1;
    private TextView tv_shangchuanzhangpian_shangchuan;
    private ImageView iv_shangchuanzhaopian_fanhui;
    private Integer xiangceId;
    //private Button button;
    private MultiPickResultView recycler_view1;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> photoList;


    //List<String> mresults=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tian_jia_xiang_ce);
        userXiangce = (UserXiangce) getIntent().getSerializableExtra("userXiangce");
       // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        tv_shangchuanzhangpian_shangchuan = ((TextView) findViewById(R.id.tv_shangchuanzhangpian_shangchuan));
        iv_shangchuanzhaopian_fanhui = ((ImageView) findViewById(R.id.iv_shangchuanzhaopian_fanhui));
        recycler_view1 = ((MultiPickResultView) findViewById(R.id.recycler_view1));
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        xiangceId = userXiangce.getXiangceId();
        System.out.println("相册Id1111111" + xiangceId);
        //button = ((Button) findViewById(R.id.button));

    /*    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

*/

        recycler_view1.init(this, MultiPickResultView.ACTION_SELECT, null);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);











/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
/*        findViewById(R.id.button_no_camera).setOnClickListener(v -> onClick(v.getId()));
        findViewById(R.id.button_one_photo).setOnClickListener(v -> onClick(v.getId()));
        findViewById(R.id.button_photo_gif).setOnClickListener(v -> onClick(v.getId()));
        findViewById(R.id.button_multiple_picked).setOnClickListener(v -> onClick(v.getId()));*/

/*
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(TianJiaZhaoPianActivity.this);
            }
        }));
*/


//
        tv_shangchuanzhangpian_shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
                Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        iv_shangchuanzhaopian_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
/*    public void upload(View view){
        upload();
    }*/

    private void upload() {
        RequestParams requestParams = new RequestParams(NetUtil.url_zhuwei + "upload");

       requestParams.addBodyParameter("fileName","fileName");
        for (int i = 0; i < photoList.size(); i ++){
            requestParams.addBodyParameter("file",new File(photoList.get(i)));
        }
            System.out.println("相册Id22222222" + xiangceId);
            requestParams.addQueryStringParameter("xiangceId", xiangceId+"");
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






    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                 // mresults=data.getStringArrayListExtra(PhotoPickerActivity.KEY_
/*        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        fileList  =new ArrayList<File>();
            File file=null;
            for(String photo:photos){
                file=new File(photo);
                fileList.add(file);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }*/

        recycler_view1.onActivityResult(requestCode, resultCode, data);


        photoList = recycler_view1.getPhotos();
        for (int i = 0; i < photoList.size(); i++) {
            System.out.println("367583" + photoList.get(i));

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (currentClickId != -1) onClick(currentClickId);
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        // No need to explain to user as it is obvious
        return false;
    }

    private void onClick(@IdRes int viewId) {
        switch (viewId) {
            case R.id.button: {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(4)
                        .start(this);
                break;
            }
/*
            case R.id.button_no_camera: {
                PhotoPicker.builder()
                        .setPhotoCount(7)
                        .setShowCamera(false)
                        .setPreviewEnabled(false)
                        .start(this);
                break;
            }

            case R.id.button_one_photo: {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .start(this);
                break;
            }

            case R.id.button_photo_gif : {
                PhotoPicker.builder()
                        .setShowCamera(true)
                        .setShowGif(true)
                        .start(this);
                break;
            }

            case R.id.button_multiple_picked:{
                PhotoPicker.builder()
                        .setPhotoCount(4)
                        .setShowCamera(true)
                        .setSelected(selectedPhotos)
                        .start(this);
                break;
            }*/
        }

        currentClickId = viewId;
    }
}
