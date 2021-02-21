package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Account;

public interface AccountService {
	/**
	 * 通过推特账号获取该推特账号所有信息
	 * @param Account_Num 推特的ID
	 * @return
	 */
	public Account getAccountByID(String AccountNum);
	/**
	 * 获取所有推特账号信息
	 * @return
	 */
	public List<Account> getAccountAll();
	/**
	 * 通过推特的状态获取全部推特信息
	 * @param State
	 * @return
	 */
	public List<Account> getAccountByState(String State);
	
	/**
	 * 修改推特账号信息
	 * @param account
	 */
	public int ModifyAccount(Account account);
	
	/**
	 * 通过推特账号删除该推特信息
	 * @param Account_Num
	 * @return
	 */
	public int deleteAccount(String AccountNum);
	/**
	 * 
	 * 获取用户拥有的推特
	 * @param State
	 * @param num 随机获取的条数
	 * @return
	 */
	public List<Account> getAccountByStateRand(String uid,String State, int num);
	
	/**
	 * 修改指定推特的状态State
	 * @param AccountNum 指定的推特账号
	 * @param State		要修改的状态
	 * @return
	 */
	public int modifyAccountState(String AccountNum,String State);
	
	/**
	 * 通过状态获取推特的数量
	 * @param state 推特的状态
	 * @return
	 */
	public Object getAccountNumsByState(String state);
	
	/**
	 * 通过指定用户和推特状态获取推特数量
	 * @param state  推特状态
	 * @param uid    使用的用户ID
	 * @return
	 */
	public Object getAccountNumsBy_S_U(String state,String uid);
	/**
	 * 通过指定用户和推特状态和推特类型获取推特数量
	 * @param state 推特状态
	 * @param uid  用户id
	 * @param type 推特类型
	 * @return
	 */
	public Object getAccountNumsBy_S_U_T(String state, String uid, String type);
	
	/**
	 * 管理员为某个用户添加推特
	 * @param userId  管理员指定用户的ID
	 * @param accountNum 管理员指定的推特ID
	 * @return
	 */
	public int addUserIDforTwtter(String userId, String accountNum);
	
	/**
	 * 修改推特的使用状态
	 * @param accountNum 推特ID
	 * @param useState 使用状态
	 * @return
	 */
	public int modifyAccountUseState(String accountNum, String useState);
	
	/**
	 * 删除推特拥有者
	 * @param t 推特ID
	 * @return
	 */
	public int deleteTwtterUIDBy_Tid(String t);
	
	/**
	 * 获取所有推特信息表account的ID
	 */
	public List<String> getTwtter_ALL_ID();
}
