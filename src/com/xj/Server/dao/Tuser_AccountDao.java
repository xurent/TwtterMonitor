package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tuser_Account;

public interface Tuser_AccountDao {
	
	/**
	 * 通过指定用户和指定推特状态获取推特表+推特信息表数据
	 * @param uid
	 * @param state
	 * @return
	 */
	public List<Tuser_Account> FindAll_BY_UID_S(String uid,String state);
	
	/**
	 * 通过推特ID修改推特表+推特信息表数据
	 * @param TuserID
	 * @return
	 */
	public int modify_BY_UID(Tuser_Account tuser_account);
	
	
}
