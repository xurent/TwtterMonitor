package com.xj.Server.service;

import java.util.List;

import com.xj.Server.model.Text_;

public interface Text_Service {
	
	/**
	 * 上传文本内容
	 * @return
	 */
	public int uploadText_(Text_ text_);
	
	/**
	 * 下载文本内容
	 * @return
	 */
	public Text_ downloadText_();
	
	/**
	 * 通过ID批量删除文本
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadText_(String Text_ID);
	
	/**
	 * 按标签类型下载文本
	 * @param tag 标签字符串
	 * @return
	 */
	public List<String> downloadText_ByTag(String tag);
	
	/**
	 * 更新一条文本数据
	 * @param text_
	 * @return 
	 */
	public int modifyText_ByID(Text_ text_);

	
}
