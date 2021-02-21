package com.xj.Server.model;

import java.sql.Date;

public class Spread_record {
	
	private int Account_Num;//账号
	private String UserID;		//所属用户
	private String Plaform;		//所属平台
	private String Interlinking;//推广链接
	private String Content;		//内容
	private String Screenshot;	//截图
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
	public String getInterlinking() {
		return Interlinking;
	}
	public void setInterlinking(String interlinking) {
		Interlinking = interlinking;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getScreenshot() {
		return Screenshot;
	}
	public void setScreenshot(String screenshot) {
		Screenshot = screenshot;
	}
	public Date getSend_time() {
		return Send_time;
	}
	public void setSend_time(Date send_time) {
		Send_time = send_time;
	}
	public Spread_record(int account_Num, String userID, String plaform, String interlinking, String content,
			String screenshot, Date send_time) {
		super();
		Account_Num = account_Num;
		UserID = userID;
		Plaform = plaform;
		Interlinking = interlinking;
		Content = content;
		Screenshot = screenshot;
		Send_time = send_time;
	}
	public Spread_record() {
		super();
	}
	@Override
	public String toString() {
		return "Spread_record [Account_Num=" + Account_Num + ", UserID=" + UserID + ", Plaform=" + Plaform
				+ ", Interlinking=" + Interlinking + ", Content=" + Content + ", Screenshot=" + Screenshot
				+ ", Send_time=" + Send_time + "]";
	}
	
	
}
