package com.example.asus.paishow.pojo;

import java.io.Serializable;

public class UserZhaopian implements Serializable{
     private Integer zhaopianId;
     private String zhaipianUrl;
	public boolean isShowAll;
	public UserZhaopian(Integer zhaopianId, String zhaipianUrl) {
		super();
		this.zhaopianId = zhaopianId;
		this.zhaipianUrl = zhaipianUrl;
	}
	public Integer getZhaopianId() {
		return zhaopianId;
	}
	public void setZhaopianId(Integer zhaopianId) {
		this.zhaopianId = zhaopianId;
	}
	public String getZhaipianUrl() {
		return zhaipianUrl;
	}
	public void setZhaipianUrl(String zhaipianUrl) {
		this.zhaipianUrl = zhaipianUrl;
	}

	@Override
	public String toString() {
		return "UserZhaopian{" +
				"zhaopianId='" + zhaopianId + '\'' +
				", zhaipianUrl='" + zhaipianUrl + '\'' +
				'}';
	}
}
