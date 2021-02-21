package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Tuser_Account;

public interface Tuser_AccountService {
	
	/**
	 * 通过用户id 和推特state状态 获取满足条件的推特账号信息表
	 * @param uid  用户id
	 * @param state 推特状态
	 * @return
	 */
	public List<Tuser_Account> FindAll_BY_UID_S(String uid,String state);
	
	
	/**
	 * 批量修改推特表+推特信息表
	 * @param tuser_account
	 * @return
	 */
	public int modify_BY_UID(Tuser_Account tuser_accountArr);

	/**
	 * 批量导入推特数据
	 * @param tuser_Account
	 * @return
	 */
	public int insertTwtterData(Tuser_Account tuser_Account);
}
