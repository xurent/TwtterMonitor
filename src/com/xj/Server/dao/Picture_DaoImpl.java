package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Picture_;

public class Picture_DaoImpl extends BaseDao<Picture_>implements Picture_Dao {

	@Override
	public int uploadPicture(Picture_ picture) {
		String sql="INSERT INTO `picture_`(`Pic_ID`,`Pic_old_name`,`Pic_size`,`Pic_path`,`Tag`,`Upload_N`,`Time`)VALUES(?,?,?,?,?,?,?);";
		return super.update(sql, picture.getPic_ID(),picture.getPic_old_name(),picture.getPic_size(),picture.getPic_path(),picture.getTag(),picture.getUpload_N(),picture.getTime());
	}

	@Override
	public List<Picture_> downloadPicture_ByTag(String[] tag) {
		String sql="SELECT * FROM `picture_` WHERE 1=1";
		int nLen=tag.length;
		int i=0;
		while(i<nLen){
			sql+=" AND FIND_IN_SET('"+tag[i]+"',Tag)";
			i++;
		}	
		return super.getList(sql);
	}

	@Override
	public int deletePicture_ByPid(String Pid) {
		String sql="DELETE FROM `picture_` WHERE `Pic_ID`=?;";
		return super.update(sql, Pid);
	}

	@Override
	public Picture_ getPicture_By_Pid(String pid) throws LoginSQLException {
		String sql="SELECT * FROM `picture_` WHERE `Pic_ID`=?;";
		return super.get(sql, pid);
	}

}
