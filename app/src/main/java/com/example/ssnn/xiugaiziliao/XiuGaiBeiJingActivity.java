package com.example.ssnn.xiugaiziliao;

import android.app.Activity;
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
import android.widget.TextView;

import com.example.asus.paishow.R;
import com.example.asus.paishow.pojo.User;
import com.example.asus.paishow.utils.NetUtil;
import com.example.asus.paishow.utils.xUtilsImageUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XiuGaiBeiJingActivity extends AppCompatActivity {

    private static String path="/sdcard/myHead/";//sd路径
    //private TextView tv_touxiang_baocun;
    private File file;
    private Uri imageUri;
    String items[]={"相册选择","拍照"};
    public static final int SELECT_PIC=11;
    public static final int TAKE_PHOTO=12;
    public static final int CROP=13;
    private Button bt_xiangce_beijing;
    private Button bt_paizhao_beijing;
    private User user;
    private String userBeijing_str;
    private String userBackground;
    private ImageView iv_zuizongxiugai_beijing;
    private ImageView iv_xiugaibeijing_fanhui;
    private TextView tv_beijing_baocun;
   // private ImageView iv_zuizongxiugai0_beijing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_bei_jing);
        user=(User)getIntent().getSerializableExtra("user");
        final Intent intent = this.getIntent();
        bt_xiangce_beijing = ((Button) findViewById(R.id.bt_xiangce_beijing));
        bt_paizhao_beijing = ((Button) findViewById(R.id.bt_paizhao_beijing));
        iv_zuizongxiugai_beijing = ((ImageView) findViewById(R.id.iv_zuizongxiugai_beijing));
        iv_xiugaibeijing_fanhui = ((ImageView) findViewById(R.id.iv_xiugaibeijing_fanhui));
        tv_beijing_baocun = ((TextView) findViewById(R.id.tv_beijing_baocun));
        //iv_zuizongxiugai_beijing = ((ImageView) findViewById(R.id.iv_zuizongxiugai_beijing));
        x.image().bind(iv_zuizongxiugai_beijing, NetUtil.url_zhuwei+user.getUserBackground());
        // ButterKnife.inject(this);

        //判断sd卡是否存在，存在
        if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED) ){
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            userBeijing_str = getPhotoFileName();
            userBackground="beijing/"+userBeijing_str;
            file=new File(Environment.getExternalStorageDirectory(),userBeijing_str);
            imageUri= Uri.fromFile(file);
            System.out.println("照片的???"+userBeijing_str);

            //拍照
            bt_paizhao_beijing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(intent2,TAKE_PHOTO);
                }
            });
            //相册选择
            bt_xiangce_beijing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, SELECT_PIC);
                }
            });
        }

        iv_xiugaibeijing_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_beijing_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserBackground(userBackground);
                Bundle bundle = intent.getExtras();
                bundle.putSerializable("user", user);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);//返回页面1
                finish();

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

        intent.putExtra("aspectX", 400);
        intent.putExtra("aspectY", 300);

        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
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
        iv_zuizongxiugai_beijing.setImageBitmap(bitmap);//iv显示图片
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

        RequestParams requestParams=new RequestParams(NetUtil.url_zhuwei+"UploadBeiJingServlet");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file",file);

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
