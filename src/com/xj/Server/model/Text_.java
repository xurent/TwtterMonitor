package com.xj.Server.model;

import java.util.Date;

public class Text_ {
	
	private long Text_ID; //����ID(�ļ���)
	private String Text_; //����
	private String Tag;		//��ǩ
	private String Upload_N;//�ϴ�������
	private Date Time;		//�ϴ�ʱ��
	public long getText_ID() {
		return Text_ID;
	}
	public void setText_ID(long text_ID) {
		Text_ID = text_ID;
	}
	public String getText_() {
		return Text_;
	}
	public void setText_(String text_) {
		Text_ = text_;
	}
	public String getTag() {
		return Tag;
	}
	public void setTag(String tag) {
		Tag = tag;
	}
	public String getUpload_N() {
		return Upload_N;
	}
	public void setUpload_N(String upload_N) {
		Upload_N = upload_N;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public Text_(long text_ID, String text_, String tag, String upload_N, Date time) {
		super();
		Text_ID = text_ID;
		Text_ = text_;
		Tag = tag;
		Upload_N = upload_N;
		Time = time;
	}
	public Text_() {
		super();
	}
	@Override
	public String toString() {
		return "Text_ [Text_ID=" + Text_ID + ", Text_=" + Text_ + ", Tag=" + Tag + ", Upload_N=" + Upload_N + ", Time="
				+ Time + "]";
	}
	
	//��ȡ��ǩ�� ����
	public String getText_String(){
		return "{\"Tag\":\""+Tag+"\",\"Text_\":\""+Text_+"\",\"Text_ID\":\""+Text_ID+"\"}";
	}
}
