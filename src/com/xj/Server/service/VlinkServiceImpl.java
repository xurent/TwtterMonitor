package com.xj.Server.service;

import java.util.ArrayList;
import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.VlinkDao;
import com.xj.Server.model.Text_;
import com.xj.Server.model.Vlink;

public class VlinkServiceImpl implements VlinkService {
	
	@SuppressWarnings("static-access")
	VlinkDao vlinkdao=new FactoryDao().getVlinkDao();
	@Override
	public int uploadVlink(Vlink link) {
		
		return vlinkdao.uploadVlink(link);
	}

	@Override
	public List<Vlink> downloadText_() {
		
		return vlinkdao.downloadVlink();
	}

	@Override
	public int deleteDownloadVlink(String Vlink_ID) {
		String strArr[]=Vlink_ID.split(",");	 //切割要删除的文本ID
		int nLen=strArr.length;//数值的长度
		int i=0;
		int deleteNum=0;//删除的条数
		while(i<nLen){
			if(vlinkdao.deleteDownloadVlink_((strArr[i]))>0){
				deleteNum++;
			}
			i++;
		}		 
		return deleteNum;
	}

	@Override
	public List<String> downloadVlink_ByTag(String tag) {

		String strArr[]=tag.split(",");	
		List<Vlink>list= vlinkdao.downloadVlink_ByTag(strArr);
		
		@SuppressWarnings("unchecked")
		List<String> send_json= new ArrayList();
		if(list.size()>0){			
		//构建存文本标签和文本内容的json数据 链表;	
		for(Vlink t:list ){	
			String s="{\"Vlink_ID\":\""+t.getVLink_ID()+"\",\"Tag\":\""+t.getTag()+"\",\"VLink\":\""+t.getVLink()+"\"}\r\n";
			send_json.add(s);	
		}		
		}
		
		return send_json;
	}

	@Override
	public int modifyVlink_ByID(Vlink link) {
		
		return vlinkdao.modifyVlink_ByID(link);
	}

}
