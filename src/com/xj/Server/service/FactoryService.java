package com.xj.Server.service;

public class FactoryService {
	public static UserService getUserService(){
		return new UserServiceImpl();			
		}
	
	public static UserInfoService getUserInfoService(){
		return new UserInfoServiceImpl();
	}
	
	public static AccountService getAccountService(){
		return new AccountServiceImpl();
	}
	
	public static TusersService getTusersService(){
		return new TusersServiceImpl();
	}
	
	public static Text_Service getText_Service(){
		return new Text_ServiceImpl();
	}
	
	public static TagService getTagService(){
		return new TagServiceImpl();
	}
	
	
	public static Picture_Service getPicture_Service(){
		return new Picture_ServiceImpl();
	}
	public static Tuser_AccountService getTuser_AccountService(){
		return new Tuser_AccountServiceImpl();
	}
	
	public static Video_Service getVideo_Service(){
		return new Video_ServiceImp();
	}
	
	public static VlinkService getVlinkService(){
		return new VlinkServiceImpl();
	}
	
	public static MonitorUserService getMonitorUserService() {
		return new MonitorUserServiceImpl();
	}
	
	public static LinkService getLinkService() {
		return new LinkServicelmpl();
	}
	
	public static Build_recordService getBuild_recordServiceImpl(){
		return new Build_recordServiceImpl();
	}
}
