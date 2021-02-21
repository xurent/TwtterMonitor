package com.xj.Server.dao;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.MonitorUser;

public class MonitorUserDaolmpl extends BaseDao<MonitorUser> implements MonitorUserDao {

	@Override
	public int add(MonitorUser user) {
		String sql="insert into monitor(username,num) values(?,?)";
		return super.update(sql, user.getUsername(),user.getNum());
	}

	@Override
	public int updateUser(MonitorUser user) {
		String sql="update monitor set num=? where username=?";
		return super.update(sql, user.getNum(),user.getUsername());
	}

	
	

	@Override
	public int getNum(String username,String user) {
		String sql="select * from monitor where username=? and name=?";
		MonitorUser monitorUser = null;
		try {
			monitorUser = super.get(sql, username,user);
			if(monitorUser == null) {
				return -1;
			}
		} catch (LoginSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return monitorUser.getNum();
	}

	

}
