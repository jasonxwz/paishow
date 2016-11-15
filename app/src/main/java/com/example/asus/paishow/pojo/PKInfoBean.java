package com.example.asus.paishow.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2016/11/7.
 */

public class PKInfoBean {
    public List<PKInfo> pklist;

    public static class PKInfo{
        public int id;
        public int pk1Id;
        public String pk1Picture;
        public int pk2Id;
        public String pk2Picture;
        public Date pkTime;
        public int pk1zanNum;
        public int pk2zanNum;
        public String userName;
        public String userTouxiang;
        private boolean isLike=false;

        @Override
        public String toString() {
            return "PKInfo{" +
                    "id=" + id +
                    ", pk1Id=" + pk1Id +
                    ", pk1Picture='" + pk1Picture + '\'' +
                    ", pk2Id=" + pk2Id +
                    ", pk2Picture='" + pk2Picture + '\'' +
                    ", pkTime=" + pkTime +
                    ", pk1zanNum=" + pk1zanNum +
                    ", pk2zanNum=" + pk2zanNum +
                    ", userName='" + userName + '\'' +
                    ", userTouxiang='" + userTouxiang + '\'' +
                    ", isLike=" + isLike +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PKInfoBean{" +
                "pklist=" + pklist +
                '}';
    }
}
