package com.example.asus.paishow.listview;

import android.view.View;

import java.util.Date;

/**
 * Created by asus on 2016/10/29.
 */

public class UserInfo {

    public int userId;
    public String userName;
    public String userPsd;
    public String userSex;
    public String userLocation;
    public String userTouxiang;
    public String userBackground;
    public String userJianjie;
    public int userRank;
    public Date userBirthday;
    public int userDengji;
    public int userRenqi;
    public String userShenglv;
    public boolean isLike=false;

    public UserInfo(){

    }

    public UserInfo(int userId, String userName, String userPsd, String userSex, String userLocation, String userTouxiang, String userBackground, String userJianjie, int userRank, Date userBirthday, int userDengji, int userRenqi, String userShenglv, boolean isLike) {
        this.userId = userId;
        this.userName = userName;
        this.userPsd = userPsd;
        this.userSex = userSex;
        this.userLocation = userLocation;
        this.userTouxiang = userTouxiang;
        this.userBackground = userBackground;
        this.userJianjie = userJianjie;
        this.userRank = userRank;
        this.userBirthday = userBirthday;
        this.userDengji = userDengji;
        this.userRenqi = userRenqi;
        this.userShenglv = userShenglv;
        this.isLike = isLike;
    }
}
