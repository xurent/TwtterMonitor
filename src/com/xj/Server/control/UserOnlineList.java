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
	//�����������û�ȫ���Ǽ��ڼ���
	private HashMap<String,UserInfoList>hashMap=new HashMap<String,UserInfoList>();
	//�����
	UserInfoService userInfoService=FactoryService.getUserInfoService();
	/**
	 * ��ȡ�����û�
	 * @return
	 */
	public static UserOnlineList getUserOnlineList(){
		
		return userOnlineList;
	}
	public void regOnLine(String uid,Socket socket){
		//�ж������ͻ����Ƿ��½һ�����˺ţ��ǵĻ�ǿ������
		UserInfoList userInfolist=hashMap.get(uid);
		if(userInfolist!=null){
			try {
				try {
					userInfolist.getSocket().getOutputStream().write(4);
				} catch (Exception e) {
				}
				if(userInfolist.getSocket().getSoLinger()==-1)
				userInfolist.getSocket().setSoLinger(true, 1);//����RSTǰ�ȴ��ͻ���1����
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
		
		hashMap.put(uid, userInfolist);//�Ǽ�����

		}catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("��������Ϊ��");
		}catch(Exception e){
		
			e.printStackTrace();
			System.out.println("��ȡ������Ϣδ֪����");
		}
	}
	
	//�ж��û��Ƿ�����
		public boolean isUserOnline(String uid){		
			return hashMap.containsKey(uid);
		}
		/**
		 * ��������û���Ϣ
		 * 
		 * @param uid
		 * @return
		 */
		public UserInfoList getOnlineUserInfo(String uid){
			return hashMap.get(uid);
		}
		/**
		 * ����
		 * @param uid
		 */
		public void logout(String uid){
			hashMap.remove(uid);
		}
		/**
		 * ������ߵ�����ID��Ϣ
		 * 
		 * @return
		 */
		public Set<String> getUserInfos(){
			return hashMap.keySet();
			
		}
		
		/**
		 * ��ȡ����value
		 * @return
		 */
		public Collection<UserInfoList> getUserInfoList(){
			return hashMap.values();
		}
		
}
