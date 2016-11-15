package com.example.asus.paishow.paihang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/18.
 */
public class ListShengLvBean {
    public int status;
    public ArrayList<ShengLvPaiHang> paihanglist;


    public static class ShengLvPaiHang{
        public Integer userId;
        public String  userName;
        public String  shengLvNum;
        public String userImg;


        @Override
        public String toString() {
            return "ShengLvPaiHang{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", shengLvNum='" + shengLvNum + '\'' +
                    ", userImage='" + userImg + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "ListShengLvBean{" +
                "status=" + status +
                ", paiHangList=" + paihanglist +
                '}';
    }
}
