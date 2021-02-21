package com.xj.Server.model;

/**
 * 登陆用户表
 * @author 何旭杰
 *
 */
public class User {
	
	private String UserID;
	private String Passwd;
	private String Type;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public User(String userID, String passwd, String type) {
		super();
		UserID = userID;
		Passwd = passwd;
		Type = type;
	}
	
	public User(String userID, String passwd) {
		super();
		UserID = userID;
		Passwd = passwd;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", Passwd=" + Passwd + ", Type=" + Type + "]";
	}
	
}
