package com.example.asus.paishow.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class ListActivityBean implements Serializable {

    public List<Dongtai> dongtaiList;

    public static  class Dongtai implements Serializable{
        public  boolean isShowAll=false;
        public Integer dongtaiId;
        public int userId;
        public String userImg;
        public String userName;
        public String title;
        public String fabutime;
        public String text;
       public String imgs;


        public Integer getDongtaiId() {
            return dongtaiId;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserImg() {
            return userImg;
        }

        public String getUserName() {
            return userName;
        }

        public String getTitle() {
            return title;
        }

        public String getFabutime() {
            return fabutime;
        }

        public String getText() {
            return text;
        }

        public String getImgs() {
            return imgs;
        }

        @Override
        public String toString() {
            return "Dongtai{" +
                    "dongtaiId=" + dongtaiId +
                    ", userId=" + userId +
                    ", userImg='" + userImg + '\'' +
                    ", userName='" + userName + '\'' +
                    ", title='" + title + '\'' +
                    ", fabutime=" + fabutime +
                    ", text='" + text + '\'' +
                    ", imgs='" + imgs + '\'' +
                    '}';
        }
    }
    }




