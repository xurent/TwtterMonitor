package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Video_;

public interface Video_Dao {
	
	/**
	 * 上传视频
	 * @param video 对象
	 * @return
	 */
	public int uploadVideo(Video_ video);
	
	/**
	 * 通过标签类型下载视频
	 * @param tag
	 * @return
	 */
	public List<Video_> downloadVideo_ByTag(String[] tag);
	
	/**
	 * 通过ID删除视频
	 * @param Vid
	 * @return
	 */
	public int deleteVideo_ByVid(String Vid);
	
}
