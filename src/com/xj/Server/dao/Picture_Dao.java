package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public interface Picture_Dao {
	
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
	public List<Picture_> downloadPicture_ByTag(String[] tag);
	
	/**
	 * ͨ��IDɾ��ͼƬ
	 * @param Vid
	 * @return
	 */
	public int deletePicture_ByPid(String Pid);
	
	/**
	 * ͨ��Pid��ȡͼƬ��Ϣ
	 * @param pid
	 * @return
	 * @throws LoginSQLException 
	 */
	public Picture_ getPicture_By_Pid(String pid) throws LoginSQLException;

	
}
