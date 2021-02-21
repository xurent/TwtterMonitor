package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Account;

public interface AccountService {
	/**
	 * ͨ�������˺Ż�ȡ�������˺�������Ϣ
	 * @param Account_Num ���ص�ID
	 * @return
	 */
	public Account getAccountByID(String AccountNum);
	/**
	 * ��ȡ���������˺���Ϣ
	 * @return
	 */
	public List<Account> getAccountAll();
	/**
	 * ͨ�����ص�״̬��ȡȫ��������Ϣ
	 * @param State
	 * @return
	 */
	public List<Account> getAccountByState(String State);
	
	/**
	 * �޸������˺���Ϣ
	 * @param account
	 */
	public int ModifyAccount(Account account);
	
	/**
	 * ͨ�������˺�ɾ����������Ϣ
	 * @param Account_Num
	 * @return
	 */
	public int deleteAccount(String AccountNum);
	/**
	 * 
	 * ��ȡ�û�ӵ�е�����
	 * @param State
	 * @param num �����ȡ������
	 * @return
	 */
	public List<Account> getAccountByStateRand(String uid,String State, int num);
	
	/**
	 * �޸�ָ�����ص�״̬State
	 * @param AccountNum ָ���������˺�
	 * @param State		Ҫ�޸ĵ�״̬
	 * @return
	 */
	public int modifyAccountState(String AccountNum,String State);
	
	/**
	 * ͨ��״̬��ȡ���ص�����
	 * @param state ���ص�״̬
	 * @return
	 */
	public Object getAccountNumsByState(String state);
	
	/**
	 * ͨ��ָ���û�������״̬��ȡ��������
	 * @param state  ����״̬
	 * @param uid    ʹ�õ��û�ID
	 * @return
	 */
	public Object getAccountNumsBy_S_U(String state,String uid);
	/**
	 * ͨ��ָ���û�������״̬���������ͻ�ȡ��������
	 * @param state ����״̬
	 * @param uid  �û�id
	 * @param type ��������
	 * @return
	 */
	public Object getAccountNumsBy_S_U_T(String state, String uid, String type);
	
	/**
	 * ����ԱΪĳ���û��������
	 * @param userId  ����Աָ���û���ID
	 * @param accountNum ����Աָ��������ID
	 * @return
	 */
	public int addUserIDforTwtter(String userId, String accountNum);
	
	/**
	 * �޸����ص�ʹ��״̬
	 * @param accountNum ����ID
	 * @param useState ʹ��״̬
	 * @return
	 */
	public int modifyAccountUseState(String accountNum, String useState);
	
	/**
	 * ɾ������ӵ����
	 * @param t ����ID
	 * @return
	 */
	public int deleteTwtterUIDBy_Tid(String t);
	
	/**
	 * ��ȡ����������Ϣ��account��ID
	 */
	public List<String> getTwtter_ALL_ID();
}
