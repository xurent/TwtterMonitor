package com.xj.Server.model;

import java.sql.Date;

public class Pick_record {
	
	private int Account_Num; //�˺�
	private String UserID;		//�����û�
	private String Plaform;		//����ƽ̨
	private String Pick_link;	//�ƹ�����
	private Date Send_time;		//����ʱ��
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
