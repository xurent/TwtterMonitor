package com.xj.Server.dao;

import java.sql.Connection;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;

public interface UserDao {
	/**
	 * 修改账号密码
	 * @param user
	 * @return
	 */
	public int modifypasswd(User user);
	/**
	 * 通过id删除账号
	 * @param id
	 * @return
	 */
	public int deleteUserById(String id);
	/**
	 * 修改账号
	 * @param user
	 * @return
	 */
	public int updateUserById(User user);
	/**
	 * 通过id获取账号对象
	 * @param id
	 * @return
	 * @throws LoginSQLException 
	 */
	public User get(String id) throws LoginSQLException;
	/**
	 * 获取所有账号对象
	 * @return
	 */
	public List<User> getListAll();
	
	public User get(Connection conn,String id);
	
	/**
	 * 通过权限查询
	 * @param Type
	 * @return
	 */
	public List<User>query(String Type);
	
	/**
	 * 登陆
	 * @param user
	 * @return
	 * @throws LoginSQLException 
	 */
	public User login(User user) throws LoginSQLException;
	
	/**
	 * 通过用户ID查询用户类型
	 * @param userID
	 * @return
	 * @throws LoginSQLException 
	 */
	public User getType(String userID) throws LoginSQLException;
	
}
