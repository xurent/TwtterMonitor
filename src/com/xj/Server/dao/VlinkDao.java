package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Vlink;

public interface VlinkDao {
	
	/**
	 * 上传链接内容
	 * @return
	 */
	public int uploadVlink(Vlink Vlink);
	
	/**
	 * 下载所有链接内容
	 * @return
	 */
	public List<Vlink> downloadVlink();
	
	/**
	 * 通过ID删除链接
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadVlink_(String Vlink_ID);
	
	/**
	 * 按标签类型下载链接
	 * @param tag 标签列表
	 * @return
	 */
	public List<Vlink> downloadVlink_ByTag(String[] tag);
	
	/**
	 * 通过文本ID修改内容
	 * @param Vlink  对象
	 * @return
	 */
	public int modifyVlink_ByID(Vlink link);
}
