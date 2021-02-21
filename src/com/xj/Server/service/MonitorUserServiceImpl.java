package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.MonitorUserDao;
import com.xj.Server.model.Link_Time;
import com.xj.Server.model.MonitorUser;

public class MonitorUserServiceImpl implements MonitorUserService {

	MonitorUserDao monitorUserdao= FactoryDao.getMonitorUserDao();
	
	

	@Override
	public int add(MonitorUser user) {
		// TODO Auto-generated method stub
		return monitorUserdao.add(user);
	}

	@Override
	public int updateUser(MonitorUser user) {
		// TODO Auto-generated method stub
		return monitorUserdao.updateUser(user);
	}

	@Override
	public int getNum(String username,String user) {
		// TODO Auto-generated method stub
		return monitorUserdao.getNum(username,user);
	}


	

	

}
