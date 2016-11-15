package com.example.asus.paishow.application;

import android.app.Application;


import com.example.asus.paishow.pojo.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.BuildConfig;
import org.xutils.x;

import io.rong.imkit.RongIM;


/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application {

    private User user = new User(1);

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        RongIM.init(this);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
}
