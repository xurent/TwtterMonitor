package com.xj.Server.model;

import java.util.Date;

public class Video_ {
	private String Vid_ID;
	private String Vold_name;
	private String Vsize;
	private String Vid_Path;
	private String Tag;
	private String Upload_N;
	private Date Time;
	public String getVid_ID() {
		return Vid_ID;
	}
	public void setVid_ID(String vid_ID) {
		Vid_ID = vid_ID;
	}
	public String getVold_name() {
		return Vold_name;
	}
	public void setVold_name(String vold_name) {
		Vold_name = vold_name;
	}
	public String getVsize() {
		return Vsize;
	}
	public void setVsize(String vsize) {
		Vsize = vsize;
	}
	public String getVid_Path() {
		return Vid_Path;
	}
	public void setVid_Path(String vid_Path) {
		Vid_Path = vid_Path;
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
	public Video_(String vid_ID, String vold_name, String vsize, String vid_Path, String tag, String upload_N,
			Date time) {
		super();
		Vid_ID = vid_ID;
		Vold_name = vold_name;
		Vsize = vsize;
		Vid_Path = vid_Path;
		Tag = tag;
		Upload_N = upload_N;
		Time = time;
	}
	public Video_() {
		super();
	}
	@Override
	public String toString() {
		return "Video [Vid_ID=" + Vid_ID + ", Vold_name=" + Vold_name + ", Vsize=" + Vsize + ", Vid_Path=" + Vid_Path
				+ ", Tag=" + Tag + ", Upload_N=" + Upload_N + ", Time=" + Time + "]";
	}
	
	
}
