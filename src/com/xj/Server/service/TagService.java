package com.xj.Server.service;

public interface TagService {
	
	/**
	 * 获取所有标签
	 * @return
	 */
	public StringBuffer getAlltag();
	
	/**
	 * 添加标签
	 * @return
	 */
	public int addTag(String addName);
	
	/**
	 * 通过Tag_Name删除标签
	 * @return
	 */
	public int deleteTagByName(String Name);
}
