package com.xj.Server.dao;

import java.util.Date;
import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Build_record;

public class Build_recordDaoImpl extends BaseDao<Build_record>implements Build_recordDao {

	@Override
	public int addBuild_record(Build_record build_rd) {
		
		String sql="INSERT INTO build_record(`Account_Num`,`User`,`Plaform`,`Type`,`Floor`,`Next_floor`,`Interlinking`,"
		 		+ "`Content`,`Screenshot`,`Send_time`) VALUES(?,?,?,?,?,?,?,?,?,?);";
		 	return super.update(sql, build_rd.getAccount_Num(),build_rd.getUserID(),build_rd.getPlaform(),build_rd.getType(),
		 			build_rd.getFloor(),build_rd.getNext_floor(),build_rd.getInterlinking(),build_rd.getContent(),
		 			build_rd.getScreenshot(),build_rd.getSend_time());
		
//		int flag=super.update(sql, build_rd.getAccount_Num());
//		if(flag<=0)
//			return flag;
//		
//		sql="UPDATE build_record SET Floor=?,Next_floor=?,Interlinking=?,Content=?,"+
//		"Screenshot=? ,Send_time=? WHERE Account_Num=?;";
//		
//		return super.update(sql,build_rd.getFloor(),build_rd.getNext_floor(),
//				build_rd.getInterlinking(),build_rd.getContent(),build_rd.getScreenshot(),
//				build_rd.getSend_time(),
//				build_rd.getAccount_Num());
	}

	@Override
	public List<Build_record> FindBuild_recordBy_Tid(String Tid, Date send_time_s,Date send_time_e) {
		String sql="SELECT * FROM `build_record` WHERE `Account_Num`=? AND "
				+ "`Send_time` BETWEEN  (?) AND (?);";
		return super.getList(sql, send_time_s,send_time_e);
	}

}
