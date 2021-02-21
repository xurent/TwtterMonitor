package com.xj.Server.control;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.xj.Server.service.AccountService;
import com.xj.Server.service.FactoryService;
import com.xj.Server.utils.MonitMeUtils;

public class Start {

	public static void main(String[] args)throws Exception {
		

		new Thread(){	
			public void run() {
				try {			
					System.out.println("登录服务器启动成功!");
					LoginServer.openServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		
		//17773端口，专用文件传输服务器
		new Thread() {
		
			public void run() {
				try {
					System.out.println("文件传输服务器启动成功!");
					FileServer.openServer();				
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	

	//17770端口，用于监控业务
	new Thread(){

		@SuppressWarnings("static-access")
		public void run() {
			try {
				
				StartMonitor.getStartMonitor().setStartMonitor(null, 2);
				StartMonitor.getStartMonitor().setStart(true);
				MonitoringServer.openServer();				
			} catch (Exception e) {
				Logger logger=Logger.getLogger(this.getClass()); //日志
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		};
	}.start();
	
		//开始监控推特状态
		System.out.println("监控账号开启");	
		try {
		 AccountService accountService=new FactoryService().getAccountService();//操作推特信息表
			MonitMeUtils.ControlMe( accountService.getTwtter_ALL_ID(), 2);
			//MonitMeUtils.setStart(true);
		} catch(NullPointerException e){
			System.out.println("没有监控对象");
		}catch (SQLException e) {
			System.out.println("数据库异常");
		}catch (Exception e) {
			
			// TODO: handle exception
		}
		
	}	
	
	
}
