package com.xj.Server.dao;

import java.sql.Connection;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public int modifypasswd(User user) {
		String sql="UPDATE `users` SET `Passwd`=? WHERE `UserID`=?;";
		return super.update(sql, user.getPasswd(),user.getUserID());
	}

	@Override
	public int deleteUserById(String id) {
		String sql="DELETE FROM `users` WHERE `UserID`=?;";
		return super.update(sql, id);
	}

	@Override
	public int updateUserById(User user) {
		String sql="UPDATE	`users` SET `UserID`=?,`Passwd`=?,`Type`=? WHERE `UserID`=?;";
		return super.update(sql, user.getUserID(),user.getPasswd(),user.getType(),user.getUserID());
	}

	@Override
	public User get(String id) throws LoginSQLException {
		String sql="SELECT `UserID`,`Passwd`,`Type` FROM `users` WHERE `UserID`=?;";
		return super.get(sql, id);
	}

	@Override
	public List<User> getListAll() {
		String sql="SELECT `UserID`,`Passwd`,`Type` FROM `users`;";
		return super.getList(sql);

	}

	@Override
	public User get(Connection conn, String id) {
		String sql="SELECT * FROM `users` WHERE `UserID`=?;";
		return super.get(conn,sql, id);
	}

	@Override
	public List<User> query(String Type) {
		String sql="SELECT * FROM `users` WHERE `Type`=?;";
		return super.getList(sql,Type);
	}

	@Override
	public User login(User user) throws LoginSQLException {
		String sql="SELECT `UserID` FROM `users` WHERE `UserID`=? AND `Passwd`=?;";
		return super.get(sql, user.getUserID(),user.getPasswd());
		
	}

	@Override
	public User getType(String userID) throws LoginSQLException {
		String sql="SELECT `Type` FROM `users` WHERE `UserID`=?;";
		return super.get(sql, userID);
	}

}
