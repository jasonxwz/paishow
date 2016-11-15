package com.example.asus.paishow.listview;



import android.view.View;

import java.util.ArrayList;

/**
 * Created by asus on 2016/10/29.
 */

public class UserInfoBean {
    public int status;
    public ArrayList<UserInfo> userinfoList;

    public static class UserInfo{
        public int userId;
        public String userName;
        public String userPsd;
        public String userSex;
        public String userLocation;
        public String userTouxiang;
        public String userBackground;
        public String userJianjie;
        public int userRank;
        public String userBirthday;
        public int userDengji;
        public int userRenqi;
        public String userShenglv;
        private View.OnClickListener requestBtnClickListener;
        public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
            this.requestBtnClickListener = requestBtnClickListener;
        }
        public View.OnClickListener getRequestBtnClickListener() {
            return requestBtnClickListener;
        }
        @Override
        public String toString() {
            return "UserInfo{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userPsd='" + userPsd + '\'' +
                    ", userSex='" + userSex + '\'' +
                    ", userLocation='" + userLocation + '\'' +
                    ", userTouxiang='" + userTouxiang + '\'' +
                    ", userBackground='" + userBackground + '\'' +
                    ", userJianjie='" + userJianjie + '\'' +
                    ", userRank=" + userRank +
                    ", userBirthday=" + userBirthday +
                    ", userDengji=" + userDengji +
                    ", userRenqi=" + userRenqi +
                    ", userShenglv='" + userShenglv + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "status=" + status +
                ", userinfoList=" + userinfoList +
                '}';
    }
}
