package com.xj.Server.dao;
/**
 * 工厂类
 * @author 何旭杰
 *
 */
public class FactoryDao {
	public static UserDao getUserDao(){
		return new UserDaoImpl();
	}
	
	public static UserInfoDao getUserInfoDao(){
		return new UserInfoDaoImpl();
	}
	
	public static AccountDao getAccountDao(){
		return new AccountDaoImpl();
	}
	
	public static TusersDao getTusersDao(){
		return new TusersDaoImpl();
	}
	public static Text_Dao getText_Dao(){
		return new Text_DaoImpl();
	}
	
	public static TagDao getTagDao(){
		return new TagDaoImpl();
	}
	
	public static  Video_Dao getVideo_Dao(){
		return  new Video_DaoImpl();
	}
	
	public static Picture_Dao getPicture_Dao(){
		return new Picture_DaoImpl();
	}
	
	public static Tuser_AccountDao getTuser_AccountDao(){
		return new Tuser_AccountDaoImpl();
	}
	
	public static VlinkDao getVlinkDao(){
		return new VlinkDaoImpl();
	}
	
	public static Build_recordDao getBuild_recordDao(){
		return new Build_recordDaoImpl();
	}
	
	public static MonitorUserDao getMonitorUserDao() {
		return new MonitorUserDaolmpl();
	}
	
	public static LinkDao getLinkDao() {
		return new LinkDaolmpl();
	}
}
