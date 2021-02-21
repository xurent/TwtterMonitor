package com.xj.Server.model;

import java.util.Date;

public class Build_record {
	
	private String Account_Num;	//�˺�
	private String UserID;		//�����û�
	private String Plaform;		//����ƽ̨
	private int Floor;			//¥��
	private int Next_floor;		//��һ��¥�����һ��¥��ʾ0
	private String Type;		//(��ע��ɳ�����߸�¥)
	private String Interlinking;//�ƹ�����
	private String Content;		//����
	private String Screenshot;	//��ͼ
	private Date Send_time;		//����ʱ��
	public String getAccount_Num() {
		return Account_Num;
	}
	public void setAccount_Num(String account_Num) {
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
	public int getFloor() {
		return Floor;
	}
	public void setFloor(int floor) {
		Floor = floor;
	}
	public int getNext_floor() {
		return Next_floor;
	}
	public void setNext_floor(int next_floor) {
		Next_floor = next_floor;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
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
	public Build_record(String account_Num, String userID, String plaform, int floor, int next_floor, String type,
			String interlinking, String content, String screenshot, Date send_time) {
		super();
		Account_Num = account_Num;
		UserID = userID;
		Plaform = plaform;
		Floor = floor;
		Next_floor = next_floor;
		Type = type;
		Interlinking = interlinking;
		Content = content;
		Screenshot = screenshot;
		Send_time = send_time;
	}
	public Build_record() {
		super();
	}
	@Override
	public String toString() {
		return "Build_record [Account_Num=" + Account_Num + ", UserID=" + UserID + ", Plaform=" + Plaform + ", Floor="
				+ Floor + ", Next_floor=" + Next_floor + ", Type=" + Type + ", Interlinking=" + Interlinking
				+ ", Content=" + Content + ", Screenshot=" + Screenshot + ", Send_time=" + Send_time + "]";
	}
	
	
}
