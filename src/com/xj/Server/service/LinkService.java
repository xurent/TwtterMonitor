package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Link_Time;

public interface LinkService {

	/**
	 * ���һ������
	 * @param user
	 * @return
	 */
	public int addLink(Link_Time link);
	
	
	/**
	 * ��ȡ�������ӵ�����
	 * @return
	 */
	
	public List<Link_Time> getListAll(String name);
	
	
	/**
	 * �������ӱ�֪ͨ��״̬
	 * @return
	 */
	
	public int updateCode(String name);
	
	public List<Link_Time> getListAll();
	/**
	 * �����û�����ȡ���и��¶��������
	 * @param user
	 * @return
	 */
	public List<Link_Time> getListByUser(String user);
	
}
