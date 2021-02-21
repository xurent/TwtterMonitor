package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Video_;

public class Video_DaoImpl extends BaseDao<Video_>implements Video_Dao {

	@Override
	public int uploadVideo(Video_ video) {
		String sql="INSERT INTO `video_`(`Vid_ID`,`Vold_name`,`Vsize`,`Vid_Path`,`Tag`,`Upload_N`,`Time`)VALUES(?,?,?,?,?,?,?);";
		return super.update(sql, video.getVid_ID(),video.getVold_name(),video.getVsize(),video.getVid_Path(),video.getTag(),video.getUpload_N(),video.getTime());
	}

	@Override
	public List<Video_> downloadVideo_ByTag(String[] tag) {
		String sql="SELECT * FROM `video_` WHERE WHERE 1=1";
		int nLen=tag.length;
		int i=0;
		while(i<nLen){
			sql+=" AND FIND_IN_SET('"+tag[i]+"',Tag)";
			i++;
		}
		
		return super.getList(sql);
		
	}

	@Override
	public int deleteVideo_ByVid(String Vid) {
		String sql="DELETE FROM `video_` WHERE `Vid_ID`=?;";
		return super.update(sql, Vid);
	}

}
