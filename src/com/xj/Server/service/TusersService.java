package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Tusers;

public interface TusersService {
	/**
	 * ͨ������״̬�����ȡָ������������
	 * @param uid	�û�ID
	 * @param state  ���غ�״̬
	 * @param num  Ҫ��ȡ�����ظ���
	 * @return
	 */
	public List<Tusers> getAccountByStateRand(String uid,String state, int num);
	
	/**
	 * ��ȡ�������غ�
	 * @return
	 */
	public List<Tusers> getTuserAll();
	
	/**
	 * ��ȡ�û���ӵ�е���������
	 * @param uid  �û�ID
	 * @return
	 */
	public List<Tusers> getTuserAllByUid(String uid);
	
	/**
	 * �޸���������
	 * @param tuser
	 * @return
	 */
	public int modifyTuserPWD(Tusers tuser);
	
	/**
	 * �鿴���ص�������
	 * @return
	 */
	public Object TuserSum();
	/**
	 * ͨ�������ĸ����������ȡһ�����غ�
	 * @param uid �û�id
	 * @param state ����״̬
	 * @param useState ����ʹ��״̬
	 * @param i  Ҫ��ȡ������
	 * @return
	 */
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, int i);
	
	/**
	 * ͨ������IDɾ������
	 * @param t ����ID
	 * @return
	 */
	public int deleteTwtterBy_Tid(String t);
	
	
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState,String type, int i);
	
	
}
