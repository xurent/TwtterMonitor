package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Vlink;

public interface VlinkService {
	/**
	 * �ϴ���������
	 * @return
	 */
	public int uploadVlink(Vlink link);
	
	/**
	 * ������������
	 * @return
	 */
	public List<Vlink> downloadText_();
	
	/**
	 * ͨ��ID����ɾ������
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadVlink(String Vlink_ID);
	
	/**
	 * ����ǩ������������
	 * @param tag ��ǩ�ַ���
	 * @return
	 */
	public List<String> downloadVlink_ByTag(String tag);
	
	/**
	 * ����һ����������
	 * @param text_
	 * @return 
	 */
	public int modifyVlink_ByID(Vlink link);
}
