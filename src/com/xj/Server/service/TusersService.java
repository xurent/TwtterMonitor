package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Tusers;

public interface TusersService {
	/**
	 * 通过推特状态随机获取指定个数的推特
	 * @param uid	用户ID
	 * @param state  推特号状态
	 * @param num  要获取的推特个数
	 * @return
	 */
	public List<Tusers> getAccountByStateRand(String uid,String state, int num);
	
	/**
	 * 获取所有推特号
	 * @return
	 */
	public List<Tusers> getTuserAll();
	
	/**
	 * 获取用户所拥有的所有推特
	 * @param uid  用户ID
	 * @return
	 */
	public List<Tusers> getTuserAllByUid(String uid);
	
	/**
	 * 修改推特密码
	 * @param tuser
	 * @return
	 */
	public int modifyTuserPWD(Tusers tuser);
	
	/**
	 * 查看推特的总数量
	 * @return
	 */
	public Object TuserSum();
	/**
	 * 通过以下四个参数随机获取一个推特号
	 * @param uid 用户id
	 * @param state 推特状态
	 * @param useState 推特使用状态
	 * @param i  要获取的数量
	 * @return
	 */
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, int i);
	
	/**
	 * 通过推特ID删除推特
	 * @param t 推特ID
	 * @return
	 */
	public int deleteTwtterBy_Tid(String t);
	
	
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState,String type, int i);
	
	
}
