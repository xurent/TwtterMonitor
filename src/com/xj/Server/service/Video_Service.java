package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Video_;

public interface Video_Service {
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
	public List<Video_> downloadVideo_ByTag(String tags);
	
	/**
	 * ͨ��IDɾ����Ƶ
	 * @param Vid
	 * @return
	 */
	public int deleteVideo_ByVid(String Vid);
	
}
