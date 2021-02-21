package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Link_Time;

public interface LinkDao {
	
	/**
	 * 添加一条链接
	 * @param user
	 * @return
	 */
	public int addLink(Link_Time link);
	
	
	/**
	 * 获取所有链接的数据
	 * @return
	 */
	
	public List<Link_Time> getListAll(String name);
	
	
	/**
	 * 更新链接被通知的状态
	 * @return
	 */
	
	public int updateCode(String name);

	public List<Link_Time> getListAll();
	
	/**
	 * 根据用户名获取所有更新数据
	 * @return
	 */
	public List<Link_Time> getListByUser(String user);
	
}
