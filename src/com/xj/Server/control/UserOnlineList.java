package com.xj.Server.control;

import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.xj.Server.model.UserInfo;
import com.xj.Server.model.UserInfoList;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.UserInfoService;

public class UserOnlineList {
	
	private UserOnlineList(){}
	private static UserOnlineList userOnlineList=new UserOnlineList();
	//把所有在线用户全部登记在集合
	private HashMap<String,UserInfoList>hashMap=new HashMap<String,UserInfoList>();
	//服务层
	UserInfoService userInfoService=FactoryService.getUserInfoService();
	/**
	 * 获取在线用户
	 * @return
	 */
	public static UserOnlineList getUserOnlineList(){
		
		return userOnlineList;
	}
	public void regOnLine(String uid,Socket socket){
		//判断其他客户端是否登陆一样的账号，是的话强迫下线
		UserInfoList userInfolist=hashMap.get(uid);
		if(userInfolist!=null){
			try {
				try {
					userInfolist.getSocket().getOutputStream().write(4);
				} catch (Exception e) {
				}
				if(userInfolist.getSocket().getSoLinger()==-1)
				userInfolist.getSocket().setSoLinger(true, 1);//发送RST前等待客户端1秒钟
				userInfolist.getSocket().close();
			} catch (Exception e) {
			}
			
		}
		
		//UserInfo userInfo=
		userInfolist =new UserInfoList();
		
		try{
		UserInfo userinfo= userInfoService.get(uid);
		
		userInfolist.setUserID(uid);
		if(userinfo!=null){
		userInfolist.setAge(userinfo.getAge());
		userInfolist.setSex(userinfo.getSex());
		userInfolist.setRegion(userinfo.getRegion());
		userInfolist.setType(userinfo.getType());
		userInfolist.setRegistTime(userinfo.getRegistTime());
		}
		userInfolist.setSocket(socket);
		
		hashMap.put(uid, userInfolist);//登记在线

		}catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("个人资料为空");
		}catch(Exception e){
		
			e.printStackTrace();
			System.out.println("获取个人信息未知错误");
		}
	}
	
	//判断用户是否在线
		public boolean isUserOnline(String uid){		
			return hashMap.containsKey(uid);
		}
		/**
		 * 获得在线用户信息
		 * 
		 * @param uid
		 * @return
		 */
		public UserInfoList getOnlineUserInfo(String uid){
			return hashMap.get(uid);
		}
		/**
		 * 下线
		 * @param uid
		 */
		public void logout(String uid){
			hashMap.remove(uid);
		}
		/**
		 * 获得在线的所有ID信息
		 * 
		 * @return
		 */
		public Set<String> getUserInfos(){
			return hashMap.keySet();
			
		}
		
		/**
		 * 获取所有value
		 * @return
		 */
		public Collection<UserInfoList> getUserInfoList(){
			return hashMap.values();
		}
		
}
