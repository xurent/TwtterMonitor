package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Vlink;

public class VlinkDaoImpl extends BaseDao<Vlink>implements VlinkDao {

	@Override
	public int uploadVlink(Vlink Vlink) {
		String sql="INSERT INTO `vlink`(`VLink_ID`,`VLink`,`Tag`,`Upload_N`,`Time`)VALUES(?,?,?,?,?);";
		return super.update(sql, Vlink.getVLink_ID(),Vlink.getVLink(),Vlink.getTag(),Vlink.getUpload_N(),Vlink.getTime());
	}

	@Override
	public List<Vlink> downloadVlink() {
		String sql="SELECT * FROM `vlink`;";
		return super.getList(sql);
	}

	@Override
	public int deleteDownloadVlink_(String Vlink_ID) {
		String sql="DELETE FROM `vlink` WHERE `VLink_ID`=?;";
		return super.update(sql, Vlink_ID);
	}

	@Override
	public List<Vlink> downloadVlink_ByTag(String[] tag) {
		String sql="SELECT `VLink_ID`,`Tag`,`VLink` FROM `vlink` WHERE 1=1";
		int nLen=tag.length;
		int i=0;
		while(i<nLen){
			sql+=" AND FIND_IN_SET('"+tag[i]+"',Tag)";
			i++;
		}
		//System.out.println(sql);
		return super.getList(sql);
	}

	@Override
	public int modifyVlink_ByID(Vlink link) {
		String sql="UPDATE `vlink` SET `Tag`=?,`VLink`=? `Upload_N`=? `Time`=? WHERE `VLink_ID`=?;";
		return super.update(sql, link.getTag(),link.getVLink(),link.getUpload_N(),link.getTime(),link.getVLink_ID());
	}

}
