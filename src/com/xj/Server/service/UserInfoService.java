package com.xj.Server.service;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.UserInfo;

public interface UserInfoService {
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
	public int updateUserById(UserInfo user);
	
	/**
	 * ��Ӹ�����Ϣ
	 * @param userInfo
	 * @return
	 */
	public int addUserInfo(UserInfo userInfo);
}
