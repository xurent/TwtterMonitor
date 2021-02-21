package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Account;

public interface AccountDao {
	/**
	 * ͨ�������˺Ż�ȡ�������˺�������Ϣ
	 * @param Account_Num ���ص�ID
	 * @return
	 * @throws LoginSQLException 
	 */
	public Account getAccountByID(String AccountNum) throws LoginSQLException;
	/**
	 * ��ȡ���������˺�
	 * @return
	 */
	public List<Account> getAccountAll();
	/**
	 * ͨ�����ص�״̬��ȡ�������
	 * @param State
	 * @return
	 */
	public List<Account> getAccountByState(String State);
	
	/**
	 * �޸������˺�
	 * @param account
	 */
	public int ModifyAccount(Account account);
	
	/**
	 * ͨ�������˺�ɾ��������
	 * @param Account_Num
	 * @return
	 */
	public int deleteAccount(String AccountNum);
	/**
	 * �����ȡָ����������
	 * @param state ״̬
	 * @param num  ����
	 * @return
	 */
	public List<Account> getAccountByStateRand(String uid,String state, int num);
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
	 * 
	 * @param userId
	 * @param accountNum
	 * @return
	 */
	public int addUserIDforTwtter(String userId, String accountNum);
	
	/**
	 * ͨ��ָ���û�������״̬��ȡ������Ϣ��
	 * @param uid
	 * @param state
	 * @return
	 */
	public List<Account> getAccountAll_By_U_S(String uid, String state);
	/**
	 * ͨ��ָ���û����������ͻ�ȡ������Ϣ��
	 * @param uid
	 * @param type
	 * @return
	 */
	public Object getAccountNumsByType_U(String uid, String type);
	
	/**
	 * �޸�������Ϣ��ʹ��״̬
	 * @param accountNum
	 * @param useState
	 * @return
	 */
	public int modifyAccountUseState(String accountNum, String useState);
	
	/**
	 * �������ر�ʱ��ͬʱ����������Ϣ����
	 * @param account
	 * @return
	 */
	public int InsertupdateAccount(Account account);
	/**
	 * ��������UserIDΪ��
	 * @param tid ����ID
	 * @return
	 */
	public int deleteTwtterUIDBy_Tid(String tid);
	

}
