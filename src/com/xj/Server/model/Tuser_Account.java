package com.xj.Server.model;

public class Tuser_Account extends Tusers{
	private String UserID;
	private String Plaform;
	private String Language;
	private String State;
	private String UseState;
	private String phone;
	private String Main_page;
	
	public String getMain_page() {
		return Main_page;
	}
	public void setMain_page(String main_page) {
		Main_page = main_page;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Tuser_Account() {
		super();
	}
	public Tuser_Account(String tuserID, String tuserPWD, String email, String emailPassword, String type,
			String userID, String plaform, String language, String state, String useState,String phone,String Main_page) {
		super(tuserID, tuserPWD, email, emailPassword, type);
		UserID = userID;
		Plaform = plaform;
		Language = language;
		State = state;
		UseState = useState;
		this.phone=phone;
		this.Main_page=Main_page;
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
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getUseState() {
		return UseState;
	}
	public void setUseState(String useState) {
		UseState = useState;
	}
	
	public String toString() {
		return "{\"UserID\":\"" + UserID + "\", \"Plaform\":\"" + Plaform + "\", \"Language\":\"" + Language + "\", \"State\":\""
				+ State + "\", \"TuserID\":\"" + getTuserID() + "\", \"TuserPWD\":\"" + getTuserPWD() + "\", \"Email\":\""
				+ getEmail() + "\", \"EmailPasswd\":\"" + getEmailPassword() + "\", \"Type\":\"" + getType() + 
				"\",\"UseState\":\""+getUseState()+"\",\"phone\":\""+phone+"\",\"Main_page\":\""+Main_page+"\"}$";
	}
		
	
}
