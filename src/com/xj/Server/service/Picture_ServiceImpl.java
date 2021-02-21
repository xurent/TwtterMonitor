package com.xj.Server.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.Picture_Dao;
import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public class Picture_ServiceImpl implements Picture_Service {
	
	@SuppressWarnings("static-access")
	Picture_Dao picture_dao=new FactoryDao().getPicture_Dao();
	@Override
	public int uploadPicture(Picture_ picture) {
		
		return picture_dao.uploadPicture(picture);
	}

	@Override
	public List<Picture_> downloadPicture_ByTag(String tag) {
		String strArr[]=tag.split(",");	
		
		return picture_dao.downloadPicture_ByTag(strArr);			
		//List<String> send_json= new ArrayList();
		//if(list.size()>0){			
		//构建存文本标签和文本内容的json数据 链表;	
//		for(Picture_ p:list ){	
//			String s="{\"Pic_ID\":\""+p.getPic_ID()+"\",\"Tag\":\""+t.getTag()+"\",\"text_\":\""+t.getText_()+"\"}\r\n";
//			//send_json.add(s);	
//		}
//		
//		}
		//return list;
		
		//return send_json;
	}

	@Override
	public int deletePicture_ByPid(String Pid) throws LoginSQLException {
		String[] PidArr=Pid.split(",");
		int nSize=PidArr.length;
		int i=0;
		int deleteNum=0;//删除的条数	
		while(i<nSize){
			//先获取要删除的图片信息
			Picture_ deletePic=picture_dao.getPicture_By_Pid(PidArr[i]);
			if(picture_dao.deletePicture_ByPid(PidArr[i])>0){//删除数据库中图片的信息
				//删除本地图片
				boolean flag=deleteFile(deletePic.getPic_path().replace("\\", "\\\\")+"\\\\"+deletePic.getPic_ID());
				System.out.println(deletePic.getPic_path().replace("\\", "\\\\")+"\\\\"+deletePic.getPic_ID());
				System.out.println("deleteFile:"+flag);
				if(flag&& new File(deletePic.getPic_path().replace("\\", "\\\\")).listFiles().length<=0){
					//成功删除文件后，判断该文件夹是否为空文件夹，是则连文件夹一起删除
					deleteFile(deletePic.getPic_path().replace("\\", "\\\\"));
				}
				deleteNum++;
			}
			i++;
		}		
		return deleteNum; 
	}

	@Override
	public Picture_ getPicture_By_Pid(String Pid) throws LoginSQLException {
		
		return picture_dao.getPicture_By_Pid(Pid);
	}
	
	//删除文件或文件夹
	public boolean deleteFile(String sPath) {  
		
		System.out.println("sPath:"+sPath);
	      boolean flag = false;  
	      File file = new File(sPath);  
	      // 路径为文件且不为空则进行删除  
	     if (file.isFile() && file.exists()) {  
	    	 System.out.println("删除文件");
	         file.delete();  
	         flag = true;  
	    }  
	     return flag;  
	 }

	@Override
	public List<Picture_> downloadPicture_ByID(String pid) {
		
		List<Picture_> PicList=new ArrayList<Picture_>();
		String[] pidArr=pid.split(",");
		if(pidArr.length>0){
			//循环查找图片
			for(String p:pidArr){
				try {
					PicList.add(picture_dao.getPicture_By_Pid(p));
				} catch (LoginSQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		return PicList;
	}  

}
