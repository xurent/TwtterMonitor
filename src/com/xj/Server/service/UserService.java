package com.xj.Server.service;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;

public interface UserService {
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
		public String login(User user) throws LoginSQLException;
		
		/**
		 * ��ȡ�û�����
		 * @param userID
		 * @return
		 */
		public String getType(String userID);
}
