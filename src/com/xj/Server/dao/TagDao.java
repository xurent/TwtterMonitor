package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tag;

public interface TagDao {
	
	/**
	 * ��ȡ���б�ǩ
	 * @return
	 */
	public List<Tag> getAlltag();
	
	/**
	 * ��ӱ�ǩ
	 * @return
	 */
	public int addTag(String addName);
	
	/**
	 * ͨ��Tag_Nameɾ����ǩ
	 * @return
	 */
	public int deleteTagByName(String Name);
}
