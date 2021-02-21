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
	private static StartMonitor startMonitor=new StartMonitor(); //ȫ��
	private  Logger logger=Logger.getLogger(this.getClass()); //��־
	
	public static StartMonitor getStartMonitor(){//��ȡȫ�ֶ���
		
		return startMonitor;
	}
	private static List<Link_Time> list_data=new ArrayList<>();
	private  static boolean start=false;//���Ƽ�ؿ�ʼ��ֹͣ
	private static List<UserMoniter> Name=new ArrayList<>();
	private static int Time;
	static LinkService linkService=FactoryService.getLinkService();
	static MonitorUserService monitorUserService=FactoryService.getMonitorUserService();
	
	/**
	 * ��Ӽ�ض��������ʱ��
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
		System.out.println("���ü�����");
	}
	
	public void setStartMonitor(List<UserMoniter> name,int time) {
		if(name==null)return;
		Name.addAll(Name.size(), name);
		Time=time;
		Time_Go(time);
	}
	
	/**
	 * �Ƴ���ض���
	 * @param name ��ض���
	 */
	public boolean removeMonitor(UserMoniter name){
		
		return Name.remove(name);
	}
	/**
	 * �����ض���
	 * @param name
	 * @param time
	 * @return
	 * @throws InterruptedException
	 */
	private static  boolean Start(int time) throws InterruptedException {
		if(Name==null)return false;
		int count=Name.size();
		System.out.println("��������:"+count);
		for(int i=0;i<count;i++) {		
			Monitor(Name.get(i).getName(),Name.get(i).getUser());
			Thread.sleep(2000);
		}
		Thread.sleep(time*1000*60);
		return false;
	}
	
	
	/**
	 * ��ʼ��ؼ�¼
	 * @param string
	 * @throws IOException 
	 */
	private static void Monitor(String name,String user) {	
		TwritterUtils twritter=new TwritterUtils("https://twitter.com/"+name);
/*	System.out.println("��ע����:"+twritter.getFoucuser());
		System.out.println("���ڹ�ע:"+twritter.getFoucusing());
		System.out.println("��������:"+twritter.getTuiwen());
		for(int i=0;i<twritter.getLinks().size();i++) {
			Link_Time object=twritter.getLinks().get(i);
			System.out.println("����:"+object.getLink()+",ʱ��:"+object.getTime());
		}*/
		if(twritter.isFrozen()) {
			System.out.println(name+"�Ѿ�������");
			return;
		}
		int num=twritter.getTuiwen();
		MonitorUser mon=new MonitorUser(name,num,user);
		int datanum=monitorUserService.getNum(name,user);
		if(datanum==-1) {//���ݿⲻ���ڴ˶���
			System.out.println("���ݿⲻ���ڴ˶����������");
			monitorUserService.add(mon);
		}
		System.out.println("*******"+datanum+","+num);
		if(datanum-num>0) {//�и���
			for(int i=0;i<datanum-num;i++) {
				Link_Time object=twritter.getLinks().get(i);
				if(true==UserOnlineList.getUserOnlineList().getUserInfos().isEmpty()){
					//��������
					linkService.addLink(object);//���ݴ������ݿ�
					DataClear();//�������
				}
				
				list_data.add(object);
			}
		}else if(datanum==num) {//�޸���
			System.out.println("�޸���");
			return;
		}
		
		monitorUserService.updateUser(mon);
		
		
	}

	//��ʱ��
	 public static void Time_Go(int time) {
		new Thread() {
			@Override
			public void run() {
				boolean f=true;
				if(f)
				System.out.println("�������-----");
				f=false;
			while(start) {
				Date date=new Date();
				try {
					//System.out.println("---"+date.getHours());
					if(date.getHours()>=17||date.getHours()<=7) {
			//			System.out.println("��"+(i++)+"�μ��....");
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
	//���ü�ؿ�ʼ��ֹͣ
	public  static void setStart(boolean start0) {
		start = start0;
		if(start==true){//�����������
			Time_Go(Time);
		}
	}
	/**
	 * ��ȡ���и��µ�����
	 * @return
	 */
	public List<Link_Time> getList_data() {
		return list_data;
	}
	
	/**
	 * ��մ�������ݵ���ʱ����
	 */
	public static void DataClear() {
		list_data.clear();
	}

	/**
	 * ȡ�����������
	 * @return
	 */
	public static List<UserMoniter> getName() {
		return Name;
	}
	
		
	
}
