package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Link_Time;
import com.xj.Server.model.MonitorUser;

public interface MonitorUserService {

	
	
	/**
	 * 添加一个监控对象数据
	 * @param user
	 * @return
	 */
	public int add(MonitorUser user);
	
	/**
	 * 更新监控对象数据
	 * @param user
	 * @return
	 */
	
	public int updateUser(MonitorUser user);
	
	/**
	 * 获取推文数量
	 * @param username
	 * @param user 
	 * @return
	 */
	
	public int getNum(String username, String user);
	
	
	
}
