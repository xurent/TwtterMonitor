package com.xj.Server.service;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.UserInfo;

public interface UserInfoService {
	/**
	 * 通过ID获取个人信息
	 * @param UserID
	 * @return
	 * @throws LoginSQLException 
	 */
	public UserInfo get(String UserID) throws LoginSQLException;
	
	/**
	 * 通过ID删除个人信息
	 * @param UserID
	 * @return
	 */
	public int deleteUserInfoById(String UserID);
	
	/**
	 * 修改个人信息
	 * @param UserInfo
	 * @return
	 */
	public int updateUserById(UserInfo user);
	
	/**
	 * 添加个人信息
	 * @param userInfo
	 * @return
	 */
	public int addUserInfo(UserInfo userInfo);
}
