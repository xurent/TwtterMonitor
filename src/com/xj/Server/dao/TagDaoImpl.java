package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Tag;

public class TagDaoImpl extends BaseDao<Tag> implements  TagDao {

	@Override
	public List<Tag> getAlltag() {
		String sql="SELECT * FROM `tag`;";
		return super.getList(sql);
	}

	@Override
	public int addTag(String addName) {
		String sql="INSERT INTO `tag`(`Tag_Name`) VALUES(?);";
		return super.update(sql, addName);
	}

	@Override
	public int deleteTagByName(String Name) {
		String sql="DELETE FROM `tag` WHERE `Tag_Name` IN ?";
		return super.update(sql, Name);
	}

}
