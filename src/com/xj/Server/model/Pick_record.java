package com.xj.Server.model;

import java.sql.Date;

public class Pick_record {
	
	private int Account_Num; //账号
	private String UserID;		//所属用户
	private String Plaform;		//所属平台
	private String Pick_link;	//推广链接
	private Date Send_time;		//发送时间
	public int getAccount_Num() {
		return Account_Num;
	}
	public void setAccount_Num(int account_Num) {
		Account_Num = account_Num;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPlaform() {
		return Plaform;
	}
	public void setPlaform(String plaform) {
		Plaform = plaform;
	}
	public String getPick_link() {
		return Pick_link;
	}
	public void setPick_link(String pick_link) {
		Pick_link = pick_link;
	}
	public Date getSend_time() {
		return Send_time;
	}
	public void setSend_time(Date send_time) {
		Send_time = send_time;
	}
	public Pick_record(int account_Num, String userID, String plaform, String pick_link, Date send_time) {
		super();
		Account_Num = account_Num;
		UserID = userID;
		Plaform = plaform;
		Pick_link = pick_link;
		Send_time = send_time;
	}
	public Pick_record() {
		super();
	}
	@Override
	public String toString() {
		return "Pick_record [Account_Num=" + Account_Num + ", UserID=" + UserID + ", Plaform=" + Plaform
				+ ", Pick_link=" + Pick_link + ", Send_time=" + Send_time + "]";
	}
	
	
}
