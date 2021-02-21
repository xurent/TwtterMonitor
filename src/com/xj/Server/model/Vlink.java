package com.xj.Server.model;

import java.util.Date;

public class Vlink {
	public Vlink() {
		super();
	}
	public Vlink(String vLink_ID, String vLink, String tag, String upload_N, Date time) {
		super();
		VLink_ID = vLink_ID;
		VLink = vLink;
		Tag = tag;
		Upload_N = upload_N;
		Time = time;
	}
	private String VLink_ID;
	private String VLink;
	private String Tag;
	private String Upload_N;
	private Date Time;
	public String getVLink_ID() {
		return VLink_ID;
	}
	public void setVLink_ID(String vLink_ID) {
		VLink_ID = vLink_ID;
	}
	public String getVLink() {
		return VLink;
	}
	public void setVLink(String vLink) {
		VLink = vLink;
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
	@Override
	public String toString() {
		return "Vlink [VLink_ID=" + VLink_ID + ", VLink=" + VLink + ", Tag=" + Tag + ", Upload_N=" + Upload_N
				+ ", Time=" + Time + "]";
	}
	
	public String getVlinkString(){
		
		return "{\"VLink_ID\":\""+VLink_ID+"\",\"VLink\":\""+VLink+"\",\"Tag\":\""+Tag+"\"}";
	}
}
