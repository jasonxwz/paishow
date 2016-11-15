package com.example.asus.paishow.pojo;

public class UserShiguangzhou {
private String userId;
private String sTime;
private String sNeiRong;
public UserShiguangzhou(String userId, String sTime, String sNeiRong) {
	super();
	this.userId = userId;
	this.sTime = sTime;
	this.sNeiRong = sNeiRong;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getsTime() {
	return sTime;
}
public void setsTime(String sTime) {
	this.sTime = sTime;
}
public String getsNeiRong() {
	return sNeiRong;
}
public void setsNeiRong(String sNeiRong) {
	this.sNeiRong = sNeiRong;
}
@Override
public String toString() {
	return "UserShiguangzhou [userId=" + userId + ", sTime=" + sTime + ", sNeiRong=" + sNeiRong + "]";
}


}
