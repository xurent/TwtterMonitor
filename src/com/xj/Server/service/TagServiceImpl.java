package com.xj.Server.service;

import java.util.Iterator;
import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.TagDao;
import com.xj.Server.model.Tag;

public class TagServiceImpl implements TagService {
	
	@SuppressWarnings("static-access")
	TagDao tagdao =new FactoryDao().getTagDao();
	@Override
	public StringBuffer getAlltag() {

		List<Tag>list= tagdao.getAlltag();
		StringBuffer strBuff= new StringBuffer();
		if(!list.isEmpty())
		{
			Iterator<Tag> iter = list.iterator() ;
			while(iter.hasNext()){
				Tag str = iter.next() ;
				strBuff.append(str.getTags());
				strBuff.append(",");
			}
			
		}	
		return strBuff;
	}

	@Override
	public int addTag(String addName) {
		
		return tagdao.addTag(addName);
	}

	@Override
	public int deleteTagByName(String Name) {
		
		return tagdao.deleteTagByName(Name);
	}

}
