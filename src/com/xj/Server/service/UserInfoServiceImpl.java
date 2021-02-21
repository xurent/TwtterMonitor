package com.xj.Server.service;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.UserInfoDao;
import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.UserInfo;

public class UserInfoServiceImpl implements UserInfoService {
	
	@SuppressWarnings("static-access")
	UserInfoDao userInfoDao= new FactoryDao().getUserInfoDao();

	@Override
	public UserInfo get(String UserID) throws LoginSQLException {
		
		return userInfoDao.get(UserID);
	}

	@Override
	public int deleteUserInfoById(String UserID) {
		
		return userInfoDao.deleteUserInfoById(UserID);
	}

	@Override
	public int updateUserById(UserInfo user) {
		
		return userInfoDao.updateUserById(user);
	}

	@Override
	public int addUserInfo(UserInfo userInfo) {
		
		return userInfoDao.addUserInfoByID(userInfo);
	}
	
}
