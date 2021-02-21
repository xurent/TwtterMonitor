package com.xj.Server.service;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public interface Picture_Service {
	/**
	 * 上传图片
	 * @param video 对象
	 * @return
	 */
	public int uploadPicture(Picture_ picture);
	
	/**
	 * 通过标签类型下载图片
	 * @param tag
	 * @return
	 */
	public List<Picture_> downloadPicture_ByTag(String tag);
	
	/**
	 * 通过ID删除数据库的图片信息
	 * @param Vid
	 * @return
	 * @throws LoginSQLException 
	 */
	public int deletePicture_ByPid(String Pid) throws LoginSQLException;
	
	/**
	 * 通过ID获取对应的图片信息
	 * @param Pid
	 * @return
	 * @throws LoginSQLException 
	 */
	public Picture_ getPicture_By_Pid(String Pid) throws LoginSQLException;
	
	/**
	 * 通过多个图片ID下载图片
	 * @param pid
	 * @return
	 */
	public List<Picture_> downloadPicture_ByID(String pid);
}
