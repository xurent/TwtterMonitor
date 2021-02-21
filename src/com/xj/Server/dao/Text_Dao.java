package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Text_;

public interface Text_Dao {
	
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
	 * 通过ID删除文本
	 * @param Text_ID
	 * @return
	 */
	public int deleteDownloadText_(long Text_ID);
	
	/**
	 * 按标签类型下载文本
	 * @param tag 标签列表
	 * @return
	 */
	public List<Text_> downloadText_ByTag(String[] tag);
	
	/**
	 * 通过文本ID修改内容
	 * @param text_  对象
	 * @return
	 */
	public int modifyText_ByID(Text_ text_);
}
