package com.example.asus.paishow.paihang;

import java.io.Serializable;

/**
 * Created by asus on 2016/10/17.
 */
public class DenQiPaiHang implements Serializable{
    public Integer userId;
    public String  userName;
    public int  renQiNum;
    public String userImg;

    public DenQiPaiHang(){

    }
    public DenQiPaiHang(Integer userId, String userName, int renQiNum, String userImg) {
        this.userId = userId;
        this.userName = userName;
        this.renQiNum = renQiNum;
        this.userImg = userImg;
    }
}
