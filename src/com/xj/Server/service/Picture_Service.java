package com.xj.Server.service;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public interface Picture_Service {
	/**
	 * �ϴ�ͼƬ
	 * @param video ����
	 * @return
	 */
	public int uploadPicture(Picture_ picture);
	
	/**
	 * ͨ����ǩ��������ͼƬ
	 * @param tag
	 * @return
	 */
	public List<Picture_> downloadPicture_ByTag(String tag);
	
	/**
	 * ͨ��IDɾ�����ݿ��ͼƬ��Ϣ
	 * @param Vid
	 * @return
	 * @throws LoginSQLException 
	 */
	public int deletePicture_ByPid(String Pid) throws LoginSQLException;
	
	/**
	 * ͨ��ID��ȡ��Ӧ��ͼƬ��Ϣ
	 * @param Pid
	 * @return
	 * @throws LoginSQLException 
	 */
	public Picture_ getPicture_By_Pid(String Pid) throws LoginSQLException;
	
	/**
	 * ͨ�����ͼƬID����ͼƬ
	 * @param pid
	 * @return
	 */
	public List<Picture_> downloadPicture_ByID(String pid);
}
