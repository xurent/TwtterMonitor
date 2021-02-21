package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tusers;

public class TusersDaoImpl extends BaseDao<Tusers> implements TusersDao {

	@Override
	public List<Tusers> getAccountByStateRand(String uid, String state, int num) {
	String sql="SELECT * FROM `tusers` WHERE `TuserID` IN( SELECT `AccountNum` FROM `account` WHERE `UserID`=? AND `State`=?) order by rand() limit "+num+";";
		return super.getList(sql, uid,state);
	}

	@Override
	public List<Tusers> getTuserAll() {
		String sql="SELECT * FROM `tusers`;";
		return super.getList(sql);
	}

	@Override
	public List<Tusers> getTuserAllByUid(String uid) {
		String sql="SELECT * FROM `tusers` WHERE `TuserID`=?;";
		return super.getList(sql, uid);
	}

	@Override
	public int modifyTuserPWD(Tusers tuser) {
		String sql="UPDATE `tusers` SET `TuserPWD`=? WHERE `TuserID`=? ;";
		return super.update(sql, tuser.getTuserPWD(),tuser.getTuserID());
	}

	@Override
	public Object TuserSum() {
		String sql="SELECT COUNT(*) FROM `tusers` ;";
		return  super.getValue(sql);
	}

	@Override
	public List<Tusers> getAccountByStateRand(String uid, String state) {
		String sql="SELECT * FROM `tusers` WHERE `TuserID` IN( SELECT `AccountNum` FROM `account` WHERE `UserID`=? AND `State`=?);";
		return super.getList(sql, uid,state);
	}

	@Override
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, int i) {
		String sql="SELECT * FROM `tusers` WHERE `TuserID` IN( SELECT `AccountNum` FROM `account` WHERE `UserID`=? AND `State`=? AND `UseState`=?) order by rand() limit "+i+";";
		return super.getList(sql, uid,state,useState);
	}

	@Override
	public int InsertTuser(Tusers tuser) {
		String sql="INSERT INTO tusers VALUES(?,?,?,?,?);";
		return super.update(sql, tuser.getTuserID(),tuser.getTuserPWD(),tuser.getEmail(),tuser.getEmailPassword(),
				tuser.getType());
	}

	@Override
	public int deleteTwtterBy_Tid(String tid) {
		String sql="DELETE FROM `tusers` WHERE `TuserID`=?;";
		return super.update(sql, tid);
	}

	@Override
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, String type, int i) {
		String sql="SELECT * FROM `tusers` WHERE `TuserID` IN( SELECT `AccountNum` FROM `account` WHERE `UserID`=? AND `State`=? AND `UseState`=? AND `Type`=?) order by rand() limit "+i+";";
		return super.getList(sql, uid,state,useState,type);
	}



}
