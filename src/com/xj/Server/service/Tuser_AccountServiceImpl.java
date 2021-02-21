package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.AccountDao;
import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.Tuser_AccountDao;
import com.xj.Server.dao.TusersDao;
import com.xj.Server.model.Account;
import com.xj.Server.model.Tuser_Account;
import com.xj.Server.model.Tusers;

public class Tuser_AccountServiceImpl implements Tuser_AccountService {

	@SuppressWarnings("static-access")
	Tuser_AccountDao tuser_accountdao=new FactoryDao().getTuser_AccountDao();
	
	@Override
	public List<Tuser_Account> FindAll_BY_UID_S(String uid, String state) {
		return tuser_accountdao.FindAll_BY_UID_S(uid, state);
	}

	@Override
	public int modify_BY_UID(Tuser_Account tuser_accountArr) {		
		
		return tuser_accountdao.modify_BY_UID(tuser_accountArr);
				
	}

	@SuppressWarnings({ "static-access" })
	@Override
	public int insertTwtterData(Tuser_Account tuser_Account) {
		
		TusersDao tuserDao= new FactoryDao().getTusersDao();//�������ر�
		AccountDao accountdao= new FactoryDao().getAccountDao();//����������Ϣ��
		//1.����Tusers��
		int finalnum=0;
		Tusers t=new Tusers(tuser_Account.getTuserID(),tuser_Account.getTuserPWD(),
				tuser_Account.getEmail(),tuser_Account.getEmailPassword(),tuser_Account.getType());	
		int flag1=tuserDao.InsertTuser(t);
		if(flag1<=0){ //�������ʧ�ܣ��Ͳ���ִ��������� ��
			return finalnum;
		}
		//2.����account��
		Account a=new Account(tuser_Account.getPlaform(),tuser_Account.getLanguage(),tuser_Account.getUserID(),
				tuser_Account.getState(),tuser_Account.getUseState(),tuser_Account.getTuserID(),tuser_Account.getPhone()
				,tuser_Account.getMain_page());		
		int flag2=accountdao.InsertupdateAccount(a);	
		if(flag1>0)
			finalnum++;
		if(flag2>0)
			finalnum++;
		
		return finalnum;
		
	}	

}

//