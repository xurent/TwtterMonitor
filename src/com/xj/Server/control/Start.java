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
					System.out.println("��¼�����������ɹ�!");
					LoginServer.openServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		
		//17773�˿ڣ�ר���ļ����������
		new Thread() {
		
			public void run() {
				try {
					System.out.println("�ļ���������������ɹ�!");
					FileServer.openServer();				
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	

	//17770�˿ڣ����ڼ��ҵ��
	new Thread(){

		@SuppressWarnings("static-access")
		public void run() {
			try {
				
				StartMonitor.getStartMonitor().setStartMonitor(null, 2);
				StartMonitor.getStartMonitor().setStart(true);
				MonitoringServer.openServer();				
			} catch (Exception e) {
				Logger logger=Logger.getLogger(this.getClass()); //��־
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		};
	}.start();
	
		//��ʼ�������״̬
		System.out.println("����˺ſ���");	
		try {
		 AccountService accountService=new FactoryService().getAccountService();//����������Ϣ��
			MonitMeUtils.ControlMe( accountService.getTwtter_ALL_ID(), 2);
			//MonitMeUtils.setStart(true);
		} catch(NullPointerException e){
			System.out.println("û�м�ض���");
		}catch (SQLException e) {
			System.out.println("���ݿ��쳣");
		}catch (Exception e) {
			
			// TODO: handle exception
		}
		
	}	
	
	
}
