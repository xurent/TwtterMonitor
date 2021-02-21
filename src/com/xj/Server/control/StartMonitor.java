package com.xj.Server.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.xj.Server.model.Link_Time;
import com.xj.Server.model.MonitorUser;
import com.xj.Server.model.UserMoniter;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.LinkService;
import com.xj.Server.service.MonitorUserService;
import com.xj.Server.utils.TwritterUtils;

public class StartMonitor {
	
	private StartMonitor(){}
	private static StartMonitor startMonitor=new StartMonitor(); //全局
	private  Logger logger=Logger.getLogger(this.getClass()); //日志
	
	public static StartMonitor getStartMonitor(){//获取全局对象
		
		return startMonitor;
	}
	private static List<Link_Time> list_data=new ArrayList<>();
	private  static boolean start=false;//控制监控开始和停止
	private static List<UserMoniter> Name=new ArrayList<>();
	private static int Time;
	static LinkService linkService=FactoryService.getLinkService();
	static MonitorUserService monitorUserService=FactoryService.getMonitorUserService();
	
	/**
	 * 添加监控对象和设置时间
	 * @param name
	 * @param time
	 */
	public void setList_Time(List<UserMoniter> namelist,int time) {
		if(namelist.isEmpty()||namelist.size()<=0){
			System.out.println("return ");
			return;
		}		
		Name.addAll(Name.size(), namelist);
		
		Time=time;
		System.out.println("设置监控完成");
	}
	
	public void setStartMonitor(List<UserMoniter> name,int time) {
		if(name==null)return;
		Name.addAll(Name.size(), name);
		Time=time;
		Time_Go(time);
	}
	
	/**
	 * 移除监控对象
	 * @param name 监控对象
	 */
	public boolean removeMonitor(UserMoniter name){
		
		return Name.remove(name);
	}
	/**
	 * 传入监控对象
	 * @param name
	 * @param time
	 * @return
	 * @throws InterruptedException
	 */
	private static  boolean Start(int time) throws InterruptedException {
		if(Name==null)return false;
		int count=Name.size();
		System.out.println("对象数量:"+count);
		for(int i=0;i<count;i++) {		
			Monitor(Name.get(i).getName(),Name.get(i).getUser());
			Thread.sleep(2000);
		}
		Thread.sleep(time*1000*60);
		return false;
	}
	
	
	/**
	 * 开始监控记录
	 * @param string
	 * @throws IOException 
	 */
	private static void Monitor(String name,String user) {	
		TwritterUtils twritter=new TwritterUtils("https://twitter.com/"+name);
/*	System.out.println("关注数量:"+twritter.getFoucuser());
		System.out.println("正在关注:"+twritter.getFoucusing());
		System.out.println("推文数量:"+twritter.getTuiwen());
		for(int i=0;i<twritter.getLinks().size();i++) {
			Link_Time object=twritter.getLinks().get(i);
			System.out.println("链接:"+object.getLink()+",时间:"+object.getTime());
		}*/
		if(twritter.isFrozen()) {
			System.out.println(name+"已经被冻结");
			return;
		}
		int num=twritter.getTuiwen();
		MonitorUser mon=new MonitorUser(name,num,user);
		int datanum=monitorUserService.getNum(name,user);
		if(datanum==-1) {//数据库不存在此对象
			System.out.println("数据库不存在此对象正在添加");
			monitorUserService.add(mon);
		}
		System.out.println("*******"+datanum+","+num);
		if(datanum-num>0) {//有更新
			for(int i=0;i<datanum-num;i++) {
				Link_Time object=twritter.getLinks().get(i);
				if(true==UserOnlineList.getUserOnlineList().getUserInfos().isEmpty()){
					//无人在线
					linkService.addLink(object);//数据存入数据库
					DataClear();//清空数据
				}
				
				list_data.add(object);
			}
		}else if(datanum==num) {//无更新
			System.out.println("无更新");
			return;
		}
		
		monitorUserService.updateUser(mon);
		
		
	}

	//定时器
	 public static void Time_Go(int time) {
		new Thread() {
			@Override
			public void run() {
				boolean f=true;
				if(f)
				System.out.println("开启监控-----");
				f=false;
			while(start) {
				Date date=new Date();
				try {
					//System.out.println("---"+date.getHours());
					if(date.getHours()>=17||date.getHours()<=7) {
			//			System.out.println("第"+(i++)+"次监控....");
						Start(time);			
					}
					sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
		}
			
		}.start();
	}

	public static boolean isStart() {
		return start;
	}
	//设置监控开始或停止
	public  static void setStart(boolean start0) {
		start = start0;
		if(start==true){//重新启动监控
			Time_Go(Time);
		}
	}
	/**
	 * 获取所有更新的数据
	 * @return
	 */
	public List<Link_Time> getList_data() {
		return list_data;
	}
	
	/**
	 * 清空存更新数据的临时容器
	 */
	public static void DataClear() {
		list_data.clear();
	}

	/**
	 * 取出传入的数据
	 * @return
	 */
	public static List<UserMoniter> getName() {
		return Name;
	}
	
		
	
}
