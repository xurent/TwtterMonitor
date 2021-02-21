package com.xj.Server.model;

public class Link_Time {
	private String username;
	private String link;
	private String time;
	private String code;
	
	public Link_Time( String link, String time) {
		super();
		this.link = link;
		this.time = time;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Link_Time [link=" + link + ", time=" + time + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Link_Time() {
		super();
	}
	public Link_Time(String username, String link, String time) {
		super();
		this.username = username;
		this.link = link;
		this.time = time;
	}
	
	
}
