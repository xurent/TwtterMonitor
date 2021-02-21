package com.xj.Server.model;

import java.sql.Date;

/**
 * 用户资料表
 * @author 何旭杰
 *
 */
public class UserInfo {
	
	private String UserID;
	private String Sex;
	private int Age;
	private String Region;
	private String Type;
	private Date  RegistTime;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Date getRegistTime() {
		return RegistTime;
	}
	public void setRegistTime(Date registTime) {
		RegistTime = registTime;
	}
	public UserInfo(String userID, String sex, int age, String region, String type, Date registTime) {
		super();
		UserID = userID;
		Sex = sex;
		Age = age;
		Region = region;
		Type = type;
		RegistTime = registTime;
	}
	public UserInfo() {
		super();
	}
	@Override
	public String toString() {
		return "UserInfo [UserID=" + UserID + ", Sex=" + Sex + ", Age=" + Age + ", Region=" + Region + ", Type=" + Type
				+ ", RegistTime=" + RegistTime + "]";
	}
	public UserInfo(String userID, String sex, int age, String region) {
		super();
		UserID = userID;
		Sex = sex;
		Age = age;
		Region = region;
	}
	
}
