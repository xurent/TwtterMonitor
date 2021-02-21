package com.xj.Server.dao;

import java.util.Date;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Build_record;

public interface Build_recordDao {
	
	/**
	 * 添加盖楼记录
	 * @param build_rd
	 * @return
	 */
	public int addBuild_record(Build_record build_rd); 
	
	/**
	 * 根据盖楼推特ID或（ID和盖楼时间段）查找盖楼记录
	 * @param Tid 推特ID
	 * @param send_time  盖楼时间
	 * @return
	 */
	public List<Build_record> FindBuild_recordBy_Tid(String Tid,Date send_time_s,Date send_time_e);
}
