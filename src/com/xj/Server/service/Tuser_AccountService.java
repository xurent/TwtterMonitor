package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Tuser_Account;

public interface Tuser_AccountService {
	
	/**
	 * ͨ���û�id ������state״̬ ��ȡ���������������˺���Ϣ��
	 * @param uid  �û�id
	 * @param state ����״̬
	 * @return
	 */
	public List<Tuser_Account> FindAll_BY_UID_S(String uid,String state);
	
	
	/**
	 * �����޸����ر�+������Ϣ��
	 * @param tuser_account
	 * @return
	 */
	public int modify_BY_UID(Tuser_Account tuser_accountArr);

	/**
	 * ����������������
	 * @param tuser_Account
	 * @return
	 */
	public int insertTwtterData(Tuser_Account tuser_Account);
}
