package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Text_;

public interface Text_Dao {
	
	/**
	 * �ϴ��ı�����
	 * @return
	 */
	public int uploadText_(Text_ text_);
	
	/**
	 * �����ı�����
	 * @return
	 */
	public Text_ downloadText_();
	
	/**
	 * ͨ��IDɾ���ı�
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadText_(long Text_ID);
	
	/**
	 * ����ǩ���������ı�
	 * @param tag ��ǩ�б�
	 * @return
	 */
	public List<Text_> downloadText_ByTag(String[] tag);
	
	/**
	 * ͨ���ı�ID�޸�����
	 * @param text_  ����
	 * @return
	 */
	public int modifyText_ByID(Text_ text_);
}
