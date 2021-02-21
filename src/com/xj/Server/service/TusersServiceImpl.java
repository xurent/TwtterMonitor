package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.TusersDao;
import com.xj.Server.model.Tusers;

public class TusersServiceImpl implements TusersService {

	@SuppressWarnings("static-access")
	TusersDao tuserDao= new FactoryDao().getTusersDao();
	@Override
	
	public List<Tusers> getAccountByStateRand(String uid, String state, int num) {
		
		return tuserDao.getAccountByStateRand(uid, state, num);
	}

	@Override
	public List<Tusers> getTuserAll() {
		
		return tuserDao.getTuserAll();
	}

	@Override
	public List<Tusers> getTuserAllByUid(String uid) {
		
		return tuserDao.getTuserAllByUid(uid);
	}

	@Override
	public int modifyTuserPWD(Tusers tuser) {
		
		return tuserDao.modifyTuserPWD(tuser);
	}

	@Override
	public Object TuserSum() {
		
		return tuserDao.TuserSum();
	}

	@Override
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, int i) {
		
		return tuserDao.getAccountByState_UseState_Rand(uid,state,useState,i);
	}

	@Override
	public int deleteTwtterBy_Tid(String t) {
		
		return tuserDao.deleteTwtterBy_Tid(t);
	}

	@Override
	public List<Tusers> getAccountByState_UseState_Rand(String uid, String state, String useState, String type, int i) {
		
		return tuserDao.getAccountByState_UseState_Rand(uid,state,useState,type,i);
	}

	
	
}
