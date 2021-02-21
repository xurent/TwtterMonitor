package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.UserDao;
import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;

public class UserServiceImpl implements UserService {
	
	@SuppressWarnings("static-access")
	UserDao userdao=new FactoryDao().getUserDao();
	@Override
	public int modifypasswd(User user) {
		
		return userdao.modifypasswd(user);
	}

	@Override
	public int deleteUserById(String id) {
		return userdao.deleteUserById(id);
	}

	@Override
	public int updateUserById(User user) {
		
		return userdao.updateUserById(user);
	}

	

	@Override
	public List<User> getListAll() {
		
		return userdao.getListAll();
	}

	

	@Override
	public List<User> query(String Type) {	
		return userdao.query(Type);
	}

	@Override
	public String login(User user) throws LoginSQLException {
		
		//System.out.println("login:"+user);
		String uid=userdao.login(user).getUserID();	
		return uid;
	}

	
	@Override
	public User get(String id) throws LoginSQLException {
		return userdao.get(id);
	}

	@Override
	public String getType(String userID) {
		
		try {
			return userdao.getType(userID).getType();
		} catch (LoginSQLException e) {

			e.printStackTrace();
		}
		return "数据库获取失败!";
	}

	

}
