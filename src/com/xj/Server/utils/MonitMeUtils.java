package com.xj.Server.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.xj.Server.service.AccountService;
import com.xj.Server.service.FactoryService;

public class MonitMeUtils {
	
	
	private static boolean start=true;
	private static List<String> Name;
	private static int Time;
	private static AccountService accountService=FactoryService.getAccountService();
	
	/**
	 * 设置监控的开始与停止
	 * @return
	 */
	public static  boolean isStart() {
		return start;
	}

	/**
	 * 设置监控停止
	 * @param start0
	 */
	public static void setStart(boolean start0) {
		start = start0;
		if(start==true){//重新启动监控
			try {
				ControlMe(Name,Time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
	}
	
	public static void ControlMe(List<String> name,int time) throws Exception {
		System.out.println("开始监控自己账号");
		Name=name;Time=time;
		new Thread(){
			@Override
			public void run() {
				while(start) {
					
					try {
						Date date=new Date();
						if(date.getHours()>=13||date.getHours()<=7) {
							Start(Name,time);
							
						}else{
							System.out.println("不在监控时间");
						}
						Thread.sleep(12*60*60*1000);
						Name=accountService.getTwtter_ALL_ID(); //再次向数据库读取账号ID
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
				}
			}	
			
		}.start();
			
		
		
	}

	private static void Start(List<String> name, int time) throws Exception {
		System.out.println("监控数量------"+name.size());
		if(name==null)
			return ;
		for(int i=0;i<name.size();i++) {	
		Monitor(name.get(i));
		Thread.sleep(time*1000);	
		}
		
	}

	private static void Monitor(String name) throws IOException {
		// TODO Auto-generated method stub
		TwritterUtils twritter=new TwritterUtils("https://twitter.com/"+name);
		boolean Fz=twritter.isFrozen();
		System.out.println("监控:"+Fz);
		if(Fz) {
			accountService.modifyAccountState(name, "无效");
			Name.remove(name);
			System.out.println("移除无效监控对象:"+name);
		}else {
			return;
		}
		
	}
	

}
