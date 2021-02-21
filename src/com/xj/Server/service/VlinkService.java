package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Vlink;

public interface VlinkService {
	/**
	 * 上传链接内容
	 * @return
	 */
	public int uploadVlink(Vlink link);
	
	/**
	 * 下载链接内容
	 * @return
	 */
	public List<Vlink> downloadText_();
	
	/**
	 * 通过ID批量删除链接
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadVlink(String Vlink_ID);
	
	/**
	 * 按标签类型下载链接
	 * @param tag 标签字符串
	 * @return
	 */
	public List<String> downloadVlink_ByTag(String tag);
	
	/**
	 * 更新一条链接数据
	 * @param text_
	 * @return 
	 */
	public int modifyVlink_ByID(Vlink link);
}
