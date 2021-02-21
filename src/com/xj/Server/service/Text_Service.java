package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Text_;

public interface Text_Service {
	
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
	 * ͨ��ID����ɾ���ı�
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadText_(String Text_ID);
	
	/**
	 * ����ǩ���������ı�
	 * @param tag ��ǩ�ַ���
	 * @return
	 */
	public List<String> downloadText_ByTag(String tag);
	
	/**
	 * ����һ���ı�����
	 * @param text_
	 * @return 
	 */
	public int modifyText_ByID(Text_ text_);

	
}
