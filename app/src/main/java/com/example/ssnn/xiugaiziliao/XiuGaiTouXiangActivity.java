package com.example.ssnn.xiugaiziliao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.OnClick;
public class XiuGaiTouXiangActivity extends Activity  {


    private static String path="/sdcard/myHead/";//sd路径
    //private TextView tv_touxiang_baocun;
    private File file;
    private Uri imageUri;
    String items[]={"相册选择","拍照"};
    public static final int SELECT_PIC=11;
    public static final int TAKE_PHOTO=12;
    public static final int CROP=13;
    private Button bt_xiangce;
    private Button bt_paizhao;
    private User user;
    private String userTouxiang_str;
    private String userTouxiang;
    private ImageView iv_zuizongxiugai_touxiang;
    private ImageView iv_xiugaitouxiang_fanhui;
    private TextView tv_touxiang_baocun;
    private ImageView iv_zuizongxiugai0_touxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_tou_xiang);
        user=(User)getIntent().getSerializableExtra("user");
        final Intent intent = this.getIntent();
        bt_xiangce = ((Button) findViewById(R.id.bt_xiangce));
        bt_paizhao = ((Button) findViewById(R.id.bt_paizhao));
        iv_zuizongxiugai_touxiang = ((ImageView) findViewById(R.id.iv_zuizongxiugai_touxiang));
        iv_xiugaitouxiang_fanhui = ((ImageView) findViewById(R.id.iv_xiugaitouxiang_fanhui));
        tv_touxiang_baocun = ((TextView) findViewById(R.id.tv_touxiang_baocun));
        iv_zuizongxiugai0_touxiang = ((ImageView) findViewById(R.id.iv_zuizongxiugai0_touxiang));
        xUtilsImageUtils.display(iv_zuizongxiugai0_touxiang,NetUtil.url_zhuwei+user.getUserTouxiang(), true);
        // ButterKnife.inject(this);

        //判断sd卡是否存在，存在
        if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED) ){
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            userTouxiang_str = getPhotoFileName();
            userTouxiang="touxiang/"+userTouxiang_str;
            file=new File(Environment.getExternalStorageDirectory(),userTouxiang_str);
            imageUri= Uri.fromFile(file);
            System.out.println("照片的???"+userTouxiang_str);

            //拍照
            bt_paizhao.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(intent2,TAKE_PHOTO);
                }
            });
            //相册选择
            bt_xiangce.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, SELECT_PIC);
                }
            });
        }

        iv_xiugaitouxiang_fanhui.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_touxiang_baocun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserTouxiang(userTouxiang);
                Bundle bundle = intent.getExtras();
                bundle.putSerializable("user", user);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                XiuGaiTouXiangActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                XiuGaiTouXiangActivity.this.finish();

            }
        });
    }



    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }



    public void crop(Uri uri){
        //  intent.setType("image/*");
        //裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //
        switch (requestCode){
            case SELECT_PIC:
                //相册选择
                if (data != null) {
                    crop(data.getData());

                }

                break;
            case TAKE_PHOTO:
                crop(Uri.fromFile(file));
                break;


            case CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {

                        Bitmap bitmap = extras.getParcelable("data");
                        showImage(bitmap);
                    }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    }


    //显示图片，上传服务器
    public void showImage(Bitmap bitmap){
        iv_zuizongxiugai_touxiang.setImageBitmap(bitmap);//iv显示图片
        saveImage(bitmap);//保存文件
        uploadImage();//上传服务器

    }

    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
    }

    //上传图片
    public void uploadImage(){

        RequestParams requestParams=new RequestParams(NetUtil.url_zhuwei+"UploadImageServlet");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file",file);
       // String userId = user.getUserId()+"";
        //requestParams.addBodyParameter("userId",userId);


        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ModifyPersonInfo", "onSuccess: ");
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
}