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
			
			//获取登陆用户信息的IP地址
			Collection<UserInfoList>onlines=UserOnlineList.getUserOnlineList().getUserInfoList();
			Iterator<UserInfoList> it=onlines.iterator();
			while(it.hasNext()){
				if(it.next().getSocket().getInetAddress()==socket.getInetAddress()){
					flag=true;
					break;
				}
			}
			
			if(flag==false){		
				out.write("{\"state\":830,\"msg\":\"登陆监控服务器失败!\"}".getBytes("utf-8"));
				out.flush();
			}else{//登陆成功,接收服务			
				out.write("{\"state\":800,\"msg\":\"登陆监控服务器成功!\"}".getBytes("utf-8"));
				out.flush();
				
				try {
					while(true){
						// 等待客户端信息					
						byte[] bytes = new byte[1024];
						int len = in.read(bytes);
						String json_str = new String(bytes, 0, len,"utf-8");
					
						JSONObject json = JSONObject.fromObject(json_str);// 解析
						String command= json.getString("command"); //指令头
						System.out.println("command:"+command);
						
						if(command.equals("MONITORING_INFO")){
							
							if(StartMonitor.getStartMonitor().isStart()==true){//判断监控有没有开启
								List<Link_Time>links=StartMonitor.getStartMonitor().getList_data(); //获取数据
								//monitorUserService
								List<Link_Time> sql_list=StartMonitor.linkService.getListAll(); //数据库获取数据
								if(!links.isEmpty()||!sql_list.isEmpty()){
									links.addAll(links.size(),sql_list);
									JSONArray array=JSONArray.fromObject(links);
									String datas="{\"state\":800,\"msg\":\"拿到最新监控数据!\"}"+array.toString();
									out.write(datas.getBytes("utf-8"));
									out.flush();
									//清空缓存数据
									StartMonitor.getStartMonitor().DataClear();
								}else{
									out.write("{\"state\":820,\"msg\":\"暂无监控对象信息!\"}".getBytes("utf-8"));
									out.flush();
								}
								
							}else{
								
								out.write("{\"state\":825,\"msg\":\"监控没有开启!\"}".getBytes("utf-8"));
								out.flush();
							}
						}
						
					}
					
				}catch(JSONException e){
					try {//json格式出错
						out.write("{\"state\":930,\"msg\":\"JSONObject['数据'] 出错!\"}".getBytes("UTF-8"));
						out.flush();
						System.out.println("接收到的json格式错误");
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
		// 线程池
		ExecutorService execute = Executors.newFixedThreadPool(1000);
		// 开启了 TCP 17770 端口 用于监控业务
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(17770);
		// 死循环的目的是可以无限服务
		while (true) {
			Socket socket = server.accept();
//			socket.setSoTimeout(15*1000);  //15秒超时时间
			//socket.setKeepAlive(true);//如果我们设置了SO_KEEPALIVE，TCP在距离上一次TCP包交互2个小时（取决于操作系统和TCP实现，规范建议不低于2小时）后，会发送一个探测包给另一端，如果接收不到响应，则在75秒后重新发送，连续10次仍然没有响应，则认为对方已经关闭，系统会将该连接关闭
			execute.execute(new MonitoringServer(socket));		
			System.out.println(socket.getInetAddress()+"--"+socket.getPort()+"连接监控服务端");
		}

	}
	
}
