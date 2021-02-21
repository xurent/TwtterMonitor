package com.xj.Server.service;

import java.util.Date;
import java.util.List;

import com.xj.Server.dao.AccountDao;
import com.xj.Server.dao.Build_recordDao;
import com.xj.Server.dao.FactoryDao;
import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Account;
import com.xj.Server.model.Build_record;

public class Build_recordServiceImpl implements Build_recordService {

	private Build_recordDao build_recordDao=FactoryDao.getBuild_recordDao();
	
	@Override
	public int addBuild_record(Build_record build_rd) throws LoginSQLException {
		AccountDao accountdao=FactoryDao.getAccountDao();
		Account account=accountdao.getAccountByID(build_rd.getAccount_Num());
		build_rd.setUserID(account.getUserID()); //…Ë÷√userid
		build_rd.setPlaform(account.getPlaform());
		build_rd.setType(account.getType());
		return build_recordDao.addBuild_record(build_rd);
	}

	@Override
	public List<Build_record> FindBuild_recordBy_Tid(String Tid, Date send_time_s, Date send_time_e) {
			
		return build_recordDao.FindBuild_recordBy_Tid(Tid, send_time_s, send_time_e);
	}

}
