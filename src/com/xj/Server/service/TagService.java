package com.xj.Server.service;

public interface TagService {
	
	/**
	 * ��ȡ���б�ǩ
	 * @return
	 */
	public StringBuffer getAlltag();
	
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
