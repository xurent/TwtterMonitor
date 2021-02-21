package com.xj.Server.dao;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.UserInfo;

public class UserInfoDaoImpl extends BaseDao<UserInfo>implements UserInfoDao {

	@Override
	public UserInfo get(String UserID) throws LoginSQLException {
		String sql="SELECT `UserID`,`Sex`,`Age`,`Region`,`Type`,`RegistTime` FROM `userinfo` WHERE "
				+ "`UserID`=?";
		return super.get(sql, UserID);
	}

	@Override
	public int deleteUserInfoById(String UserID) {
		String sql="DELECT FROM `userinfo` WHERE `UserID`=?;";
		return super.update(sql, UserID);
	}

	@Override
	public int updateUserById(UserInfo userInfo) {
		String sql="UPDATE `userinfo` SET `Sex`=?, `Age`=?,`Region`=? WHERE `UserID`=?;";
		return super.update(sql, userInfo.getSex(),userInfo.getAge(),userInfo.getRegion(),userInfo.getUserID());
	}

	@Override
	public int addUserInfoByID(UserInfo userInfo) {
		String sql="INSERT INTO `userinfo`(Sex,Age,Region,Type,RegistTime)VALUES(?,?,?,?,?);";
		return super.update(sql, userInfo.getSex(),userInfo.getAge(),userInfo.getAge(),userInfo.getRegion());
	}

	
}
