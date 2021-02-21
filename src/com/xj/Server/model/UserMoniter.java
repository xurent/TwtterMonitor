package com.xj.Server.model;

public class UserMoniter {
private String user;
private String name;
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public UserMoniter(String user, String name) {
	super();
	this.user = user;
	this.name = name;
}
	
}
