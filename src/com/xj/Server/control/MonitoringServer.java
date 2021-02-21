package com.xj.Server.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xj.Server.model.Link_Time;
import com.xj.Server.model.UserInfoList;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

public class MonitoringServer implements Runnable {

	private Socket socket = null;

	public MonitoringServer(Socket socket) {	
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		InputStream in = null;
		OutputStream out = null;	
		boolean flag=false;
		try {
			
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			//��ȡ��½�û���Ϣ��IP��ַ
			Collection<UserInfoList>onlines=UserOnlineList.getUserOnlineList().getUserInfoList();
			Iterator<UserInfoList> it=onlines.iterator();
			while(it.hasNext()){
				if(it.next().getSocket().getInetAddress()==socket.getInetAddress()){
					flag=true;
					break;
				}
			}
			
			if(flag==false){		
				out.write("{\"state\":830,\"msg\":\"��½��ط�����ʧ��!\"}".getBytes("utf-8"));
				out.flush();
			}else{//��½�ɹ�,���շ���			
				out.write("{\"state\":800,\"msg\":\"��½��ط������ɹ�!\"}".getBytes("utf-8"));
				out.flush();
				
				try {
					while(true){
						// �ȴ��ͻ�����Ϣ					
						byte[] bytes = new byte[1024];
						int len = in.read(bytes);
						String json_str = new String(bytes, 0, len,"utf-8");
					
						JSONObject json = JSONObject.fromObject(json_str);// ����
						String command= json.getString("command"); //ָ��ͷ
						System.out.println("command:"+command);
						
						if(command.equals("MONITORING_INFO")){
							
							if(StartMonitor.getStartMonitor().isStart()==true){//�жϼ����û�п���
								List<Link_Time>links=StartMonitor.getStartMonitor().getList_data(); //��ȡ����
								//monitorUserService
								List<Link_Time> sql_list=StartMonitor.linkService.getListAll(); //���ݿ��ȡ����
								if(!links.isEmpty()||!sql_list.isEmpty()){
									links.addAll(links.size(),sql_list);
									JSONArray array=JSONArray.fromObject(links);
									String datas="{\"state\":800,\"msg\":\"�õ����¼������!\"}"+array.toString();
									out.write(datas.getBytes("utf-8"));
									out.flush();
									//��ջ�������
									StartMonitor.getStartMonitor().DataClear();
								}else{
									out.write("{\"state\":820,\"msg\":\"���޼�ض�����Ϣ!\"}".getBytes("utf-8"));
									out.flush();
								}
								
							}else{
								
								out.write("{\"state\":825,\"msg\":\"���û�п���!\"}".getBytes("utf-8"));
								out.flush();
							}
						}
						
					}
					
				}catch(JSONException e){
					try {//json��ʽ����
						out.write("{\"state\":930,\"msg\":\"JSONObject['����'] ����!\"}".getBytes("UTF-8"));
						out.flush();
						System.out.println("���յ���json��ʽ����");
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}				
				} catch (Exception e) {
					// TODO: handle exception
				}							
				
			}
			 
		} catch (Exception e) {
			// TODO: handle exception
		}finally{		
			try {
				if(in!=null)
					in.close();
					if(out!=null)
						out.close();
					if(!socket.isClosed())
						socket.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		 

	}
	
	public static void openServer() throws Exception {
		// �̳߳�
		ExecutorService execute = Executors.newFixedThreadPool(1000);
		// ������ TCP 17770 �˿� ���ڼ��ҵ��
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(17770);
		// ��ѭ����Ŀ���ǿ������޷���
		while (true) {
			Socket socket = server.accept();
//			socket.setSoTimeout(15*1000);  //15�볬ʱʱ��
			//socket.setKeepAlive(true);//�������������SO_KEEPALIVE��TCP�ھ�����һ��TCP������2��Сʱ��ȡ���ڲ���ϵͳ��TCPʵ�֣��淶���鲻����2Сʱ���󣬻ᷢ��һ��̽�������һ�ˣ�������ղ�����Ӧ������75������·��ͣ�����10����Ȼû����Ӧ������Ϊ�Է��Ѿ��رգ�ϵͳ�Ὣ�����ӹر�
			execute.execute(new MonitoringServer(socket));		
			System.out.println(socket.getInetAddress()+"--"+socket.getPort()+"���Ӽ�ط����");
		}

	}
	
}
