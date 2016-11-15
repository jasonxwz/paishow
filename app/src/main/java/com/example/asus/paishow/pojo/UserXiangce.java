package com.example.asus.paishow.pojo;

import java.io.Serializable;
import java.util.List;

public class UserXiangce implements Serializable{
	private Integer xiangceId;
	private String xiangceMing;
	private String xiangceShijian;
	private List<UserZhaopian> userZhaopians;
	public UserXiangce(Integer xiangceId, String xiangceMing, String xiangceShijian, List<UserZhaopian> userZhaopians) {
		super();
		this.xiangceId = xiangceId;
		this.xiangceMing = xiangceMing;
		this.xiangceShijian = xiangceShijian;
		this.userZhaopians = userZhaopians;
	}
	public Integer getXiangceId() {
		return xiangceId;
	}
	public void setXiangceId(Integer xiangceId) {
		this.xiangceId = xiangceId;
	}
	public String getXiangceMing() {
		return xiangceMing;
	}
	public void setXiangceMing(String xiangceMing) {
		this.xiangceMing = xiangceMing;
	}
	public String getXiangceShijian() {
		return xiangceShijian;
	}
	public void setXiangceShijian(String xiangceShijian) {
		this.xiangceShijian = xiangceShijian;
	}
	public List<UserZhaopian> getUserZhaopians() {
		return userZhaopians;
	}
	public void setUserZhaopians(List<UserZhaopian> userZhaopians) {
		this.userZhaopians = userZhaopians;
	}
	@Override
	public String toString() {
		return "UserXiangce [xiangceId=" + xiangceId + ", xiangceMing=" + xiangceMing + ", xiangceShijian="
				+ xiangceShijian + ", userZhaopians=" + userZhaopians + "]";
	}

}
