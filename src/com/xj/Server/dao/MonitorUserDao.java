package com.xj.Server.dao;

import com.xj.Server.model.MonitorUser;


public interface MonitorUserDao {
	
	
	/**
	 * ���һ����ض�������
	 * @param user
	 * @return
	 */
	public int add(MonitorUser user);
	
	/**
	 * ���¼�ض�������
	 * @param user
	 * @return
	 */
	
	public int updateUser(MonitorUser user);
	
	/**
	 * ��ȡ��������
	 * @param username
	 * @param user 
	 * @return
	 */
	
	public int getNum(String username, String user);
	
	
	
}
