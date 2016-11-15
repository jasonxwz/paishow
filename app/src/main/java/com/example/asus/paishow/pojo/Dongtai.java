package com.example.asus.paishow.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Dongtai implements Serializable {
    public Integer dongtaiId;
    public int userId;
    public String userImg;
    public String userName;
    public String title;
    public Date fabutime;
    public String text;
    public String imgs;

    public Dongtai(){}

    public Dongtai(Integer dongtaiId, int userId, String userImg, String userName,Date fabutime, String text, String imgs) {
        this.dongtaiId = dongtaiId;
        this.userId = userId;
        this.userImg = userImg;
        this.userName = userName;
        this.fabutime = fabutime;
        this.text = text;
        this.imgs = imgs;
    }

    public Integer getDongtaiId() {
        return dongtaiId;
    }

    public void setDongtaiId(Integer dongtaiId) {
        this.dongtaiId = dongtaiId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getFabutime() {
        return fabutime;
    }

    public void setFabutime(Date fabutime) {
        this.fabutime = fabutime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dongtai dongtai = (Dongtai) o;

        if (userId != dongtai.userId) return false;
        if (!dongtaiId.equals(dongtai.dongtaiId)) return false;
        if (!userImg.equals(dongtai.userImg)) return false;
        if (!userName.equals(dongtai.userName)) return false;
        if (!fabutime.equals(dongtai.fabutime)) return false;
        if (!text.equals(dongtai.text)) return false;
        return imgs.equals(dongtai.imgs);

    }

    @Override
    public int hashCode() {
        int result = dongtaiId.hashCode();
        result = 31 * result + userId;
        result = 31 * result + userImg.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + fabutime.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + imgs.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Dongtai{" +
                "dongtaiId=" + dongtaiId +
                ", userId=" + userId +
                ", userImg='" + userImg + '\'' +
                ", userName='" + userName + '\'' +
                ", fabutime=" + fabutime +
                ", text='" + text + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}

