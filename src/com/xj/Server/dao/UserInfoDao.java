package com.xj.Server.dao;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.UserInfo;


public interface UserInfoDao {
	
	/**
	 * ͨ��ID��ȡ������Ϣ
	 * @param UserID
	 * @return
	 * @throws LoginSQLException 
	 */
	public UserInfo get(String UserID) throws LoginSQLException;
	
	/**
	 * ͨ��IDɾ��������Ϣ
	 * @param UserID
	 * @return
	 */
	public int deleteUserInfoById(String UserID);
	
	/**
	 * �޸ĸ�����Ϣ
	 * @param UserInfo
	 * @return
	 */
	public int updateUserById(UserInfo userInfo);
	
	/**
	 * ͨ��ID��Ӹ�����Ϣ
	 * @param userInfo
	 * @return
	 */
	public int addUserInfoByID(UserInfo userInfo);
}
