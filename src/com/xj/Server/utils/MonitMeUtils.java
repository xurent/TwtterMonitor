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
	 * ���ü�صĿ�ʼ��ֹͣ
	 * @return
	 */
	public static  boolean isStart() {
		return start;
	}

	/**
	 * ���ü��ֹͣ
	 * @param start0
	 */
	public static void setStart(boolean start0) {
		start = start0;
		if(start==true){//�����������
			try {
				ControlMe(Name,Time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
	}
	
	public static void ControlMe(List<String> name,int time) throws Exception {
		System.out.println("��ʼ����Լ��˺�");
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
							System.out.println("���ڼ��ʱ��");
						}
						Thread.sleep(12*60*60*1000);
						Name=accountService.getTwtter_ALL_ID(); //�ٴ������ݿ��ȡ�˺�ID
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
				}
			}	
			
		}.start();
			
		
		
	}

	private static void Start(List<String> name, int time) throws Exception {
		System.out.println("�������------"+name.size());
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
		System.out.println("���:"+Fz);
		if(Fz) {
			accountService.modifyAccountState(name, "��Ч");
			Name.remove(name);
			System.out.println("�Ƴ���Ч��ض���:"+name);
		}else {
			return;
		}
		
	}
	

}
