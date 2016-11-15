package com.example.asus.paishow.paihang;

import java.io.Serializable;

/**
 * Created by asus on 2016/10/19.
 */
public class ShengLvPaiHang implements Serializable {
    public Integer userId;
    public String  userName;
    public String  shengLvNum;
    public String userImg;


    public ShengLvPaiHang(){

    }
    public ShengLvPaiHang(Integer userId, String userName, String shengLvNum, String userImg) {
        this.userId = userId;
        this.userName = userName;
        this.shengLvNum = shengLvNum;
        this.userImg = userImg;
    }
}