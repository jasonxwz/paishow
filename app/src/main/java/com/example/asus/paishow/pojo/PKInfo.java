package com.example.asus.paishow.pojo;

import com.example.asus.paishow.listview.UserInfoBean;

import java.util.Date;
import java.util.List;

public class PKInfo {

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
	public List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public PKInfo(){
		
	}
	public PKInfo(int pk1Id) {
		this.pk1Id = pk1Id;
	}

	public PKInfo(int id, int pk1Id, String pk1Picture, int pk2Id, String pk2Picture, Date pkTime, int pk1zanNum, int pk2zanNum, String userName, String userTouxiang) {
		this.id = id;
		this.pk1Id = pk1Id;
		this.pk1Picture = pk1Picture;
		this.pk2Id = pk2Id;
		this.pk2Picture = pk2Picture;
		this.pkTime = pkTime;
		this.pk1zanNum = pk1zanNum;
		this.pk2zanNum = pk2zanNum;
		this.userName = userName;
		this.userTouxiang = userTouxiang;
	}


	public int getPk1Id() {
		return pk1Id;
	}

	public void setPk1Id(int pk1Id) {
		this.pk1Id = pk1Id;
	}

	public String getPk1Picture() {
		return pk1Picture;
	}

	public void setPk1Picture(String pk1Picture) {
		this.pk1Picture = pk1Picture;
	}

	public int getPk2Id() {
		return pk2Id;
	}

	public void setPk2Id(int pk2Id) {
		this.pk2Id = pk2Id;
	}

	public String getPk2Picture() {
		return pk2Picture;
	}

	public void setPk2Picture(String pk2Picture) {
		this.pk2Picture = pk2Picture;
	}

	public Date getPkTime() {
		return pkTime;
	}

	public void setPkTime(Date pkTime) {
		this.pkTime = pkTime;
	}

	public int getPk1zanNum() {
		return pk1zanNum;
	}

	public void setPk1zanNum(int pk1zanNum) {
		this.pk1zanNum = pk1zanNum;
	}

	public int getPk2zanNum() {
		return pk2zanNum;
	}

	public void setPk2zanNum(int pk2zanNum) {
		this.pk2zanNum = pk2zanNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTouxiang() {
		return userTouxiang;
	}

	public void setUserTouxiang(String userTouxiang) {
		this.userTouxiang = userTouxiang;
	}
}
