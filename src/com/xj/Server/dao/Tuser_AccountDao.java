package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tuser_Account;

public interface Tuser_AccountDao {
	
	/**
	 * ͨ��ָ���û���ָ������״̬��ȡ���ر�+������Ϣ������
	 * @param uid
	 * @param state
	 * @return
	 */
	public List<Tuser_Account> FindAll_BY_UID_S(String uid,String state);
	
	/**
	 * ͨ������ID�޸����ر�+������Ϣ������
	 * @param TuserID
	 * @return
	 */
	public int modify_BY_UID(Tuser_Account tuser_account);
	
	
}
