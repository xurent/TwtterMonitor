package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Link_Time;
import com.xj.Server.model.MonitorUser;

public interface MonitorUserService {

	
	
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
