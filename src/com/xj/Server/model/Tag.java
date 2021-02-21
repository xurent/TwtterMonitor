package com.xj.Server.model;

public class Tag {
	
	private int Tag_ID; //±êÇ©ID
	private String Tag_Name; //±êÇ©Ãû×Ö
	public int getTag_ID() {
		return Tag_ID;
	}
	public void setTag_ID(int tag_ID) {
		Tag_ID = tag_ID;
	}
	public String getTag_Name() {
		return Tag_Name;
	}
	public void setTag_Name(String tag_Name) {
		Tag_Name = tag_Name;
	}
	public Tag(int tag_ID, String tag_Name) {
		super();
		Tag_ID = tag_ID;
		Tag_Name = tag_Name;
	}
	public Tag() {
		super();
	}
	@Override
	public String toString() {
		return "Tag [Tag_ID=" + Tag_ID + ", Tag_Name=" + Tag_Name + "]";
	}
	
	public String getTags(){
		
		return Tag_Name;
	}
}
