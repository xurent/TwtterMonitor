package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Video_;

public interface Video_Dao {
	
	/**
	 * �ϴ���Ƶ
	 * @param video ����
	 * @return
	 */
	public int uploadVideo(Video_ video);
	
	/**
	 * ͨ����ǩ����������Ƶ
	 * @param tag
	 * @return
	 */
	public List<Video_> downloadVideo_ByTag(String[] tag);
	
	/**
	 * ͨ��IDɾ����Ƶ
	 * @param Vid
	 * @return
	 */
	public int deleteVideo_ByVid(String Vid);
	
}
