package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Account;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {

	@Override
	public Account getAccountByID(String AccountNum) throws LoginSQLException {
		String sql="SELECT * FROM `account` WHERE `AccountNum`=?;";
				
		return super.get(sql, AccountNum);
	}

	@Override
	public List<Account> getAccountAll() {
		String sql="SELECT * FROM `account` ;";
		return super.getList(sql);
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
	public List<Account> getAccountByState(String State) {
		String sql="SELECT * FROM `account` WHERE `State`=? ;";
		return super.getList(sql, State);
	}

	@Override
	public List<Account> getAccountByStateRand(String uid,String state, int num) {	
		String sql="SELECT * FROM `account` WHERE `State`=? AND `UserID`=? order by rand() limit "+num+";";
		return super.getList(sql, state);
	}

	@Override
	public int modifyAccountState(String AccountNum, String State) {
		String sql="UPDATE  `account` SET `State`=? WHERE `AccountNum`=?;";
		return super.update(sql, State,AccountNum);
	}

	@Override
	public Object getAccountNumsByState(String state) {
		String sql="SELECT COUNT(*) FROM `account` WHERE `State`=?;";
		return super.getValue(sql, state);
	}

	@Override
	public Object getAccountNumsBy_S_U(String state, String uid) {
		String sql="SELECT COUNT(*) FROM `account` WHERE `State`=? AND `UserID`=?;";
		return super.getValue(sql, state,uid);
	}

	@Override
	public Object getAccountNumsBy_S_U_T(String state, String uid, String type) {
		String sql="SELECT COUNT(*) FROM `account` WHERE `State`=? AND `UserID`=? AND `Type`=?;";
		return super.getValue(sql, state,uid,type);
	}

	@Override
	public int addUserIDforTwtter(String userId, String accountNum) {
		String sql="UPDATE `account` SET `UserID` =? WHERE `AccountNum`=?;";
		return super.update(sql, userId,accountNum);
	}

	@Override
	public List<Account> getAccountAll_By_U_S(String uid, String state) {
		String sql="SELECT * FROM `account` WHERE `State`=? AND `UserID`=?;";
		return super.getList(sql, state,uid);
	}

	@Override
	public Object getAccountNumsByType_U(String uid, String type) {
		String sql="SELECT COUNT(*) FROM `account` WHERE `Type`=? AND `UserID`=?;";
		return super.getValue(sql, type,uid);
	}

	@Override
	public int modifyAccountUseState(String accountNum, String useState) {
		String sql="UPDATE  `account` SET `UseState`=? WHERE `AccountNum`=?;";
		return super.update(sql, useState,accountNum);
	}

	@Override
	public int InsertupdateAccount(Account account) {
		String sql="UPDATE account SET Plaform=?,`Language`=?,UserID=?,State=?,UseState=?,phone=?,Main_page=? WHERE account.AccountNum=?;";
		return super.update(sql, account.getPlaform(),account.getLanguage(),account.getUserID(),
				account.getState(),account.getUseState(),account.getPhone(),account.getMain_page(),account.getAccountNum());
	}

	@Override
	public int deleteTwtterUIDBy_Tid(String tid) {
		String sql="UPDATE  `account` SET `UserID`=NULL WHERE `AccountNum`=?";
		return super.update(sql, tid);
	}

	
}
