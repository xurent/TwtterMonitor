package com.xj.Server.dao;

import java.util.List;

import com.xj.Server.model.Link_Time;

public class LinkDaolmpl extends BaseDao<Link_Time> implements LinkDao {

	@Override
	public int addLink(Link_Time link) {
		String sql="insert into link(username,link,time,code) values(?,?,?,1)";
		return super.update(sql, link.getUsername(),link.getLink(),link.getTime());
	}

	@Override
	public List<Link_Time> getListAll(String name) {
		String sql="select username,link,time,code from link where username=? and code=1";
		return super.getList(sql, name);
	}

	@Override
	public int updateCode(String name) {
		String sql="update link set code=0 where username=?";
		return super.update(sql, name);
	}

	@Override
	public List<Link_Time> getListAll() {
		String sql="select username,link,time,code from link where code=1";
		return super.getList(sql);
	}

	@Override
	public List<Link_Time> getListByUser(String user) {
		String sql="SELECT username,link,time,code FROM monitor m, link l WHERE m.username=l.username and l.code=1 and m.name='"+user+"'";
		return null;
	}
	
	
}
