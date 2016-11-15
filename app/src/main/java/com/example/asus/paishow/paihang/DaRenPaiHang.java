package com.example.asus.paishow.paihang;

import java.io.Serializable;

/**
 * Created by asus on 2016/10/19.
 */
public class DaRenPaiHang implements Serializable {
    public Integer userId;
    public String  userName;
    public int  dengJiNum;
    public String userImg;


    public DaRenPaiHang(){

    }

    public DaRenPaiHang(Integer userId, String userName, int dengJiNum, String userImg) {
        this.userId = userId;
        this.userName = userName;
        this.dengJiNum = dengJiNum;
        this.userImg = userImg;
    }
}
