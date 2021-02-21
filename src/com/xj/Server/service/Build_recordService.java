package com.xj.Server.service;

import java.util.Date;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Build_record;

public interface Build_recordService {
	
	/**
	 * ������Ӹ�¥��¼
	 * @param build_rd
	 * @return
	 * @throws LoginSQLException 
	 */
	public int addBuild_record(Build_record build_rd) throws LoginSQLException;
	
	/**
	 * ���ݸ�¥����ID��ID�͸�¥ʱ��Σ����Ҹ�¥��¼
	 * @param Tid ����ID
	 * @param send_time  ��¥ʱ��
	 * @return
	 */
	public List<Build_record> FindBuild_recordBy_Tid(String Tid,Date send_time_s,Date send_time_e);
}
