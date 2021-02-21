package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Text_;

public class Text_DaoImpl extends BaseDao<Text_>implements Text_Dao {

	@Override
	public int uploadText_(Text_ text_) {
		String sql="INSERT INTO `text_`(`Text_ID`,`Text_`,`Tag`,`Upload_N`,`Time`)VALUES(?,?,?,?,?);";
		return super.update(sql, text_.getText_ID(),text_.getText_(),text_.getTag(),text_.getUpload_N(),text_.getTime());
		
	}

	@Override
	public Text_ downloadText_() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteDownloadText_(long Text_ID) {
		String sql="DELETE FROM `TEXT_` WHERE `Text_ID`=?;";
		return super.update(sql, Text_ID);
	}

	@Override
	public List<Text_> downloadText_ByTag(String[] tag) {

		String sql="SELECT `Text_ID`,`Tag`,`text_` FROM `text_` WHERE 1=1";
		int nLen=tag.length;
		int i=0;
		while(i<nLen){
			sql+=" AND FIND_IN_SET('"+tag[i]+"',Tag)";
			i++;
		}
		System.out.println(sql);
		return super.getList(sql);
	}

	@Override
	public int modifyText_ByID(Text_ text_) {
		String sql="UPDATE `text_` SET `Tag`=?,`text_`=?,`Upload_N`=?,`Time`=? WHERE `Text_ID`=?;";
		return super.update(sql, text_.getTag(),text_.getText_(),text_.getUpload_N(),text_.getTime(),text_.getText_ID());
	}

}
