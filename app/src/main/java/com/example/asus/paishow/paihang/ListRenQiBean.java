package com.example.asus.paishow.paihang;

import java.util.ArrayList;

/**
 * Created by asus on 2016/10/18.
 */
public class ListRenQiBean {
    public int status;
    public ArrayList<RenQiPaiHang> paihanglist;

    public static class RenQiPaiHang{
        public Integer userId;
        public String  userName;
        public int  renQiNum;
        public String userImg;

        @Override
        public String toString() {
            return "RenQiPaiHang{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", renQiNum=" + renQiNum +
                    ", userImage='" + userImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ListRenQiBean{" +
                "status=" + status +
                ", paihanglist=" + paihanglist +
                '}';
    }
}
