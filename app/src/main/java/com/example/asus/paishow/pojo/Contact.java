package com.example.asus.paishow.pojo;

import java.io.Serializable;

/**
 * Created by msi on 2016/11/3.
 */

public class Contact implements Serializable {

    private String userTouxiang;
    private String userName;
    private String phoneNum;

    public Contact(String phoneNum, String userTouxiang, String userName) {
        this.phoneNum = phoneNum;
        this.userTouxiang = userTouxiang;
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getContactImg() {
        return userTouxiang;
    }

    public String getContactName() {
        return userName;
    }

    public void setContactName(String userName) {
        this.userName = userName;
    }
}
