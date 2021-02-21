package com.xj.Server.model;

import java.net.Socket;
import java.sql.Date;

/**
 * 在线用户信息
 * @author 何旭杰
 *
 */
public class UserInfoList extends UserInfo {
	private Socket socket;
	private String type;  //权限
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public UserInfoList() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public UserInfoList(String userID, String sex, int age, String region, String type, Date registTime,
			Socket socket) {
		super(userID, sex, age, region, type, registTime);
		this.socket = socket;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserInfoList [socket=" + socket + ", UserID=" + getUserID() + ", Sex=" + getSex()
				+ ", Age=" + getAge() + ", Region=" + getRegion() + ", Type=" + getType()
				+ ", RegistTime=" + getRegistTime() +"]";
	}
	
	
}
