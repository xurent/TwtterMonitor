package com.xj.Server.model;

import java.util.Date;

public class Picture_ {
	private String Pic_ID;
	private String Pic_old_name;
	private String Pic_size;
	private String Pic_path;
	private String Tag;
	private String Upload_N;
	private Date Time;
	public String getPic_ID() {
		return Pic_ID;
	}
	public void setPic_ID(String pic_ID) {
		Pic_ID = pic_ID;
	}
	public String getPic_old_name() {
		return Pic_old_name;
	}
	public void setPic_old_name(String pic_old_name) {
		Pic_old_name = pic_old_name;
	}
	public String getPic_size() {
		return Pic_size;
	}
	public void setPic_size(String pic_size) {
		Pic_size = pic_size;
	}
	public String getPic_path() {
		return Pic_path;
	}
	public void setPic_path(String pic_path) {
		Pic_path = pic_path;
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
	public Picture_(String pic_ID, String pic_old_name, String pic_size, String pic_path, String tag, String upload_N,
			Date time) {
		super();
		Pic_ID = pic_ID;
		Pic_old_name = pic_old_name;
		Pic_size = pic_size;
		Pic_path = pic_path;
		Tag = tag;
		Upload_N = upload_N;
		Time = time;
	}
	public Picture_() {
		super();
	}
	@Override
	public String toString() {
		return "Picture_ [Pic_ID=" + Pic_ID + ", Pic_old_name=" + Pic_old_name + ", Pic_size=" + Pic_size
				+ ", Pic_path=" + Pic_path + ", Tag=" + Tag + ", Upload_N=" + Upload_N + ", Time=" + Time + "]";
	}
	
	
}
