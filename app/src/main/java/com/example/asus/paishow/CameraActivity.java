package com.example.asus.paishow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.paishow.application.MyApplication;
import com.example.asus.paishow.pojo.Dongtai;
import com.example.asus.paishow.pojo.PKInfo;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    private Button photoButton;
    private Button cameraButton;
    private ImageView mImageView;


    //头像的存储完整路径
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
            getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private Button send_img;
    private Context myApplication;
    PKInfo pk;
    boolean isJoin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        photoButton = (Button) findViewById(R.id.main_btn_photo);
        cameraButton = (Button) findViewById(R.id.main_btn_camera);
        mImageView = (ImageView) findViewById(R.id.main_img);
        send_img = ((Button) findViewById(R.id.send_img));
        photoButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        isJoin=getIntent().getBooleanExtra("isJoin",false);
        if(isJoin){
            send_img.setText("加入PK");

        }
        send_img.setOnClickListener(this);
        //加载图片
//        x.image().bind(((ImageView) findViewById(R.id.iv_photo)), HttpUtils.host+"upload/photo1.png");
//        System.out.println(HttpUtils.host+"upload/photo1.png");

//        xUtilsImageUtils.display(((ImageView) findViewById(R.id.iv_photo1)), NetUtil.url + "imgs/photo1.png");
//        xUtilsImageUtils.display(((ImageView) findViewById(R.id.iv_photo2)), NetUtil.url + "imgs/photo1.png", 5);
//        xUtilsImageUtils.display(((ImageView) findViewById(R.id.iv_photo3)), NetUtil.url + "imgs/photo1.png", true);


    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        System.out.println("============" + UUID.randomUUID());
        return sdf.format(date) + "_" + UUID.randomUUID() + ".png";
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_btn_camera:
                getPicFromCamera();
                break;

            case R.id.main_btn_photo:
                getPicFromPhoto();
                break;

            case R.id.send_img:
                sendImg();
                Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
    }

    private void sendImg() {
        RequestParams params = new RequestParams(NetUtil.PK_FABU);//upload 是你要访问的servlet
        int pk1Id=((MyApplication)getApplication()).getUser().getUserId();
        pk = new PKInfo(pk1Id);
        Gson gson = new Gson();
        String gsonStr = gson.toJson(pk);
        params.addQueryStringParameter("pk1Id",gsonStr);
        params.addBodyParameter("fileName", "fileName");
        params.addBodyParameter("file", file);
        Log.e("isJoin", "sendImg11: "+isJoin );
        if(isJoin){
            Log.e("isJoin", "sendImg: "+isJoin );
            params.addQueryStringParameter("isJoin","1");
//            params.addBodyParameter("isJoin","true");
            params.addQueryStringParameter("pkId",getIntent().getIntExtra("pkId",0)+"");
        }
//        params.addBodyParameter("file",file1);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("isJoin", "onError: "+ex );
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera===========" + file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST" + file.getAbsolutePath());
                        if (file.exists()) {
                            photoClip(Uri.fromFile(file));
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());

                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getApplication(), photo);//保存bitmap到本地
                        mImageView.setImageBitmap(photo);


                    }
                }
                break;
            default:
                break;
        }

    }

    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

}
