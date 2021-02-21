package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.LinkDao;
import com.xj.Server.model.Link_Time;

public class LinkServicelmpl implements LinkService {

	LinkDao linkDao=FactoryDao.getLinkDao();
	@Override
	public int addLink(Link_Time link) {
		// TODO Auto-generated method stub
		return linkDao.addLink(link);
	}

	@Override
	public List<Link_Time> getListAll(String name) {
		// TODO Auto-generated method stub
		return linkDao.getListAll(name);
	}

	@Override
	public int updateCode(String name) {
		// TODO Auto-generated method stub
		return linkDao.updateCode(name);
	}

	@Override
	public List<Link_Time> getListAll() {
		// TODO Auto-generated method stub
		return linkDao.getListAll();
	}

	@Override
	public List<Link_Time> getListByUser(String user) {
		// TODO Auto-generated method stub
		return linkDao.getListByUser(user);
	}

}
