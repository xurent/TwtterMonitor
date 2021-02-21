package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public interface Picture_Dao {
	
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
	public List<Picture_> downloadPicture_ByTag(String[] tag);
	
	/**
	 * 通过ID删除图片
	 * @param Vid
	 * @return
	 */
	public int deletePicture_ByPid(String Pid);
	
	/**
	 * 通过Pid获取图片信息
	 * @param pid
	 * @return
	 * @throws LoginSQLException 
	 */
	public Picture_ getPicture_By_Pid(String pid) throws LoginSQLException;

	
}
