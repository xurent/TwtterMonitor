package com.xj.Server.service;

import java.util.ArrayList;
import java.util.List;

import com.xj.Server.dao.AccountDao;
import com.xj.Server.dao.FactoryDao;
import com.xj.Server.model.Account;

public class AccountServiceImpl implements AccountService {
	
	@SuppressWarnings("static-access")
	AccountDao accountdao= new FactoryDao().getAccountDao();
	@Override
	public Account getAccountByID(String AccountNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAccountAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAccountByState(String State) {
		
		return accountdao.getAccountByState(State);
	}

	@Override
	public int ModifyAccount(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAccount(String AccountNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Account> getAccountByStateRand(String uid,String State, int num) {
		
		return accountdao.getAccountByStateRand(uid,State,num);
	}

	@Override
	public int modifyAccountState(String AccountNum, String State) {
		
		return accountdao.modifyAccountState(AccountNum, State);
	}

	@Override
	public Object getAccountNumsByState(String state) {

		return accountdao.getAccountNumsByState(state);
	}

	@Override
	public Object getAccountNumsBy_S_U(String state, String uid) {
		
		return accountdao.getAccountNumsBy_S_U(state, uid);
	}

	@Override
	public Object getAccountNumsBy_S_U_T(String state, String uid, String type) {
		if(!type.equals("")){ //type不为空			
			if(state.equals(""))	//	
				return accountdao.getAccountNumsByType_U(uid,type);//state为空		
			return accountdao.getAccountNumsBy_S_U_T(state, uid,type);//3个全不为空	
		}else{			
			return getAccountNumsBy_S_U(state,uid); //type为空
		}
	}

	@Override
	public int addUserIDforTwtter(String userId, String accountNum) {
		
		return accountdao.addUserIDforTwtter(userId,accountNum);
	}

	@Override
	public int modifyAccountUseState(String accountNum, String useState) {
		
		return accountdao.modifyAccountUseState(accountNum,useState);
	}

	@Override
	public int deleteTwtterUIDBy_Tid(String t) {
		return accountdao.deleteTwtterUIDBy_Tid(t);
	}

	@Override
	public List<String> getTwtter_ALL_ID() {
		
		List<String> list=new ArrayList<>();
		List<Account>counts=accountdao.getAccountAll();
		if(!counts.isEmpty()){
			for(Account a:counts){
				list.add(a.getAccountNum());
			}
		}
	
		return list;
	}

}
