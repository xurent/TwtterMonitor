package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Vlink;

public interface VlinkDao {
	
	/**
	 * �ϴ���������
	 * @return
	 */
	public int uploadVlink(Vlink Vlink);
	
	/**
	 * ����������������
	 * @return
	 */
	public List<Vlink> downloadVlink();
	
	/**
	 * ͨ��IDɾ������
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadVlink_(String Vlink_ID);
	
	/**
	 * ����ǩ������������
	 * @param tag ��ǩ�б�
	 * @return
	 */
	public List<Vlink> downloadVlink_ByTag(String[] tag);
	
	/**
	 * ͨ���ı�ID�޸�����
	 * @param Vlink  ����
	 * @return
	 */
	public int modifyVlink_ByID(Vlink link);
}
