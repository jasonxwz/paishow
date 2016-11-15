package com.example.asus.paishow.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private Integer userId;
	private String userName;
	private String userSex;
	private String userLocation;
	private String userTouxiang;
	private String userBackground;
	private String userJianjie;
	private Integer userRank;
	private Integer userVipFlag;
	private Integer userFensi;
	private Integer userGuanzhu;
	private String userShengri;
	private String token;
	private boolean isLike=false;
	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
	public User() {
		super();
	}

	public User(Integer userId){
		this.userId=userId;
	}

	public User(Integer userId, String userName,String userTouxiang, String token){
		super();
		this.userId = userId;
		this.userName = userName;
		this.userTouxiang = userTouxiang;
		this.token = token;
	}

	public User(Integer userId, String userName, String userSex,
				String userLocation, String userTouxiang, String userBackground,
				String userJianjie, Integer userRank, Integer userVipFlag,
				Integer userFensi, Integer userGuanzhu, String userShengri) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userSex = userSex;
		this.userLocation = userLocation;
		this.userTouxiang = userTouxiang;
		this.userBackground = userBackground;
		this.userJianjie = userJianjie;
		this.userRank = userRank;
		this.userVipFlag = userVipFlag;
		this.userFensi = userFensi;
		this.userGuanzhu = userGuanzhu;
		this.userShengri = userShengri;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getUserTouxiang() {
		return userTouxiang;
	}
	public void setUserTouxiang(String userTouxiang) {
		this.userTouxiang = userTouxiang;
	}
	public String getUserBackground() {
		return userBackground;
	}
	public void setUserBackground(String userBackground) {
		this.userBackground = userBackground;
	}
	public String getUserJianjie() {
		return userJianjie;
	}
	public void setUserJianjie(String userJianjie) {
		this.userJianjie = userJianjie;
	}
	public Integer getUserRank() {
		return userRank;
	}
	public void setUserRank(Integer userRank) {
		this.userRank = userRank;
	}
	public Integer getUserVipFlag() {
		return userVipFlag;
	}
	public void setUserVipFlag(Integer userVipFlag) {
		this.userVipFlag = userVipFlag;
	}
	public Integer getUserFensi() {
		return userFensi;
	}
	public void setUserFensi(Integer userFensi) {
		this.userFensi = userFensi;
	}
	public Integer getUserGuanzhu() {
		return userGuanzhu;
	}
	public void setUserGuanzhu(Integer userGuanzhu) {
		this.userGuanzhu = userGuanzhu;
	}
	public String getUserShengri() {
		return userShengri;
	}
	public void setUserShengri(String userShengri) {
		this.userShengri = userShengri;
	}
	public String getToken() {
		return token;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userSex=" + userSex + ", userLocation=" + userLocation
				+ ", userTouxiang=" + userTouxiang + ", userBackground="
				+ userBackground + ", userJianjie=" + userJianjie
				+ ", userRank=" + userRank + ", userVipFlag=" + userVipFlag
				+ ", userFensi=" + userFensi + ", userGuanzhu=" + userGuanzhu
				+ ", userShengri=" + userShengri + "]";
	}




}