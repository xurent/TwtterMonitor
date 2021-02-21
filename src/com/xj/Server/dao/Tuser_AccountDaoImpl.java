package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tuser_Account;

public class Tuser_AccountDaoImpl extends BaseDao<Tuser_Account>implements Tuser_AccountDao{

	@Override
	public List<Tuser_Account> FindAll_BY_UID_S(String uid, String state) {
		String append=null;
		if(state!=null&&state.length()>0){
			append=" AND account.State=?;";
		}else{
			append=";";
		}
		String sql="SELECT tusers.*,`UserID`,`Plaform`,`Language`,`State`,`Main_page`,`phone` FROM tusers JOIN account WHERE "
				+ "tusers.TuserID=account.AccountNum AND account.UserID=?"+append;
		System.out.println("``````"+sql);
		if(state!=null&&state.length()>0)
			return super.getList(sql, uid,state);
		else
			return super.getList(sql, uid);	
	}

	@Override
	public int modify_BY_UID(Tuser_Account tuser_account) {
		String sql="UPDATE tusers,account SET tusers.TuserID=?,tusers.TuserPWD=?,"
				+ "tusers.Email=?,tusers.EmailPassword=?,account.`Language`=?,"
				+ "account.Plaform=?,account.State=?, account.Main_page=?, account.phone=? WHERE tusers.TuserID=account.AccountNum "
				+ "AND tusers.TuserID=?;";
		return super.update(sql, tuser_account.getTuserID(),tuser_account.getTuserPWD(),tuser_account.getEmail(),
				tuser_account.getEmailPassword(),tuser_account.getLanguage(),tuser_account.getPlaform(),tuser_account.getState(),
				tuser_account.getMain_page(),tuser_account.getPhone(),tuser_account.getTuserID());
		
	}
	

}
