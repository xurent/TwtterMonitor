package com.xj.Server.service;

import java.util.ArrayList;
import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.Text_Dao;
import com.xj.Server.model.Text_;

public class Text_ServiceImpl implements Text_Service {
	
	@SuppressWarnings("static-access")
	Text_Dao text_dao=new FactoryDao().getText_Dao();
	@Override
	public int uploadText_(Text_ text_) {
		
		return text_dao.uploadText_(text_);
	}

	@Override
	public Text_ downloadText_() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteDownloadText_(String Text_ID) {
		String strArr[]=Text_ID.split(",");	 //切割要删除的文本ID
		int nLen=strArr.length;//数值的长度
		int i=0;
		int deleteNum=0;//删除的条数
		while(i<nLen){
			if(text_dao.deleteDownloadText_(Long.parseLong(strArr[i]))>0){
				deleteNum++;
			}
			i++;
		}		 
		return deleteNum;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> downloadText_ByTag(String tag) {
		
		
		String strArr[]=tag.split(",");	
		List<Text_>list= text_dao.downloadText_ByTag(strArr);
		
		@SuppressWarnings("unchecked")
		List<String> send_json= new ArrayList();
		if(list.size()>0){			
		//构建存文本标签和文本内容的json数据 链表;	
		for(Text_ t:list ){	
			String s="{\"Text_ID\":\""+t.getText_ID()+"\",\"Tag\":\""+t.getTag()+"\",\"text_\":\""+t.getText_()+"\"}\r\n";
			send_json.add(s);	
		}
		
		}
		
		return send_json;
		
	}

	@Override
	public int modifyText_ByID(Text_ text_) {
		
		return text_dao.modifyText_ByID(text_);
	}

}
