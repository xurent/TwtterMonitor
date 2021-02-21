package com.xj.Server.service;

import java.util.List;

import com.xj.Server.dao.FactoryDao;
import com.xj.Server.dao.Video_Dao;
import com.xj.Server.model.Video_;

public class Video_ServiceImp implements Video_Service{

	@SuppressWarnings("static-access")
	Video_Dao  video_Dao=new FactoryDao().getVideo_Dao();
	@Override
	public int uploadVideo(Video_ video) {
		
		return video_Dao.uploadVideo(video);
	}

	@Override
	public List<Video_> downloadVideo_ByTag(String tags) {
		String strArr[]=tags.split(",");	
	//	List<Text_>list= video_Dao.downloadVideo_ByTag(tag);
		return null;
	}

	@Override
	public int deleteVideo_ByVid(String Vid) {
		
		return video_Dao.deleteVideo_ByVid(Vid);
	}

}
