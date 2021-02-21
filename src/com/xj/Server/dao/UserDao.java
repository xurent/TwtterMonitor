package com.xj.Server.dao;

import java.sql.Connection;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;

public interface UserDao {
	/**
	 * �޸��˺�����
	 * @param user
	 * @return
	 */
	public int modifypasswd(User user);
	/**
	 * ͨ��idɾ���˺�
	 * @param id
	 * @return
	 */
	public int deleteUserById(String id);
	/**
	 * �޸��˺�
	 * @param user
	 * @return
	 */
	public int updateUserById(User user);
	/**
	 * ͨ��id��ȡ�˺Ŷ���
	 * @param id
	 * @return
	 * @throws LoginSQLException 
	 */
	public User get(String id) throws LoginSQLException;
	/**
	 * ��ȡ�����˺Ŷ���
	 * @return
	 */
	public List<User> getListAll();
	
	public User get(Connection conn,String id);
	
	/**
	 * ͨ��Ȩ�޲�ѯ
	 * @param Type
	 * @return
	 */
	public List<User>query(String Type);
	
	/**
	 * ��½
	 * @param user
	 * @return
	 * @throws LoginSQLException 
	 */
	public User login(User user) throws LoginSQLException;
	
	/**
	 * ͨ���û�ID��ѯ�û�����
	 * @param userID
	 * @return
	 * @throws LoginSQLException 
	 */
	public User getType(String userID) throws LoginSQLException;
	
}
