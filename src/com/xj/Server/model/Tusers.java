package com.xj.Server.model;

public class Tusers {
	
	private String TuserID; //�����˺�
	private String TuserPWD; //��������
	private String Email;  		//��������ID
	private String EmailPassword; //��������
	private String Type;	//����
	public Tusers() {
		super();
	}
	public Tusers(String tuserID, String tuserPWD, String email, String emailPassword) {
		super();
		TuserID = tuserID;
		TuserPWD = tuserPWD;
		Email = email;
		EmailPassword = emailPassword;
	}
	public Tusers(String tuserID, String tuserPWD, String email, String emailPassword, String type) {
		super();
		TuserID = tuserID;
		TuserPWD = tuserPWD;
		Email = email;
		EmailPassword = emailPassword;
		Type = type;
	}
	
	public String getTuserID() {
		return TuserID;
	}
	public void setTuserID(String tuserID) {
		TuserID = tuserID;
	}
	public String getTuserPWD() {
		return TuserPWD;
	}
	public void setTuserPWD(String tuserPWD) {
		TuserPWD = tuserPWD;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getEmailPassword() {
		return EmailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		EmailPassword = emailPassword;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	public String toString() {
		return "TuserID=" + TuserID + ", TuserPWD=" + TuserPWD + ", Email=" + Email + ", EmailPasswd="
				+ EmailPassword + ", Type=" + Type;
	}
	
	public String getTuser(){
		
		return "TuserID=" + TuserID + ", TuserPWD=" + TuserPWD;
	}
}
