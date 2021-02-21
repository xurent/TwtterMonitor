package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tag;

public interface TagDao {
	
	/**
	 * 获取所有标签
	 * @return
	 */
	public List<Tag> getAlltag();
	
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
