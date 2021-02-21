package com.xj.Server.model;

import java.util.List;


public class MonitorUser {

	private String username;
	private int num;
	private String name;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public MonitorUser(String username, int num) {
		super();
		this.username = username;
		this.num = num;
	}
	public MonitorUser() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MonitorUser(String username, int num, String name) {
		super();
		this.username = username;
		this.num = num;
		this.name = name;
	}
	
	
	
}
