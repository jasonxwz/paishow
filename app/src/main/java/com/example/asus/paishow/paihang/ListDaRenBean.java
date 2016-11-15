package com.example.asus.paishow.paihang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/18.
 */
public class ListDaRenBean {
    public int status;
    public ArrayList<DaRenPaiHang> paihanglist;


    public static class DaRenPaiHang{
        public int userId;
        public String  userName;
        public int  dengJiNum;
        public String userImg;

        @Override
        public String toString() {
            return "DaRenPaiHang{" +
                    "daRenPaiHangId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", dengJiNum=" + dengJiNum +
                    ", userImage='" + userImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ListDaRenBean{" +
                "status=" + status +
                ", darenpaihanglist=" + paihanglist +
                '}';
    }


}
