package com.xj.Server.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.xj.Server.Ftp.DownloadStream_File;
import com.xj.Server.Ftp.DownloadStream_Picture_ID;
import com.xj.Server.Ftp.DownloadStream_Text;
import com.xj.Server.Ftp.DownloadStream_Vlink;
import com.xj.Server.Ftp.UploadStream_Text;
import com.xj.Server.Ftp.UploadStream_Vlink;
import com.xj.Server.Ftp.UploadStream_File;
import com.xj.Server.model.UserInfo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class FileServer implements Runnable {

	private Socket socket = null;
	private Logger logger=Logger.getLogger(this.getClass()); //日志
	public FileServer(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		String uid = null;
		// 验证用户是否已经登陆
		InputStream in = null;
		OutputStream out = null;	
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			// 等待客户端信息
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len,"utf-8");
		
			JSONObject json = JSONObject.fromObject(json_str);// 解析
			System.out.println("ftp:"+json.toString());
			uid=json.getString("username");
			
			System.out.println("当前访问FTP的uid:"+uid);
			UserInfo u=UserOnlineList.getUserOnlineList().getOnlineUserInfo(uid);
			System.out.println("该uid的数据:"+u);
			
			 //获取登陆用户信息的IP地址
			 Socket userSock=UserOnlineList.getUserOnlineList().getOnlineUserInfo(uid).getSocket();
			 ////////////////////////////////////
			 System.out.println("socketADDR:"+userSock.getInetAddress());
			 System.out.println(userSock.getInetAddress()+"获取到了IP");
			 ///////////////////////////////////
			 
			 //判断已经登陆的用户IP是否等于访问的IP
			 if(socket.getInetAddress().equals(userSock.getInetAddress())){
				 System.out.println("进入接收指令阶段");		 
				 out.write("{\"state\":500,\"msg\":\"进入文件指令操作状态!\"}".getBytes("UTF-8"));
				 out.flush();
				 
				 //开始业务
				 boolean flag=true;
				 long start = System.currentTimeMillis(); //开始时间
				 while(flag){
					// 等待客户端信息
						byte[] bytes0 = new byte[1024];
						 len = in.read(bytes0);
						 json_str = new String(bytes0, 0, len,"UTF-8");
						 json = JSONObject.fromObject(json_str);// 解析
						
					String command= json.getString("command"); //指令头
					System.out.println("com:"+command);
					List<Thread> list = new ArrayList<Thread>(); //多线程列表
								
					try {
						if(command.equals("UPLOAD_TEXT_")){	//上传文本
							int nSize=json.getInt("nSize");
							//开启线程传输
							Thread t=new Thread(new UploadStream_Text(socket,nSize,uid),"UPLOAD_TEXT_");
							t.start();
							list.add(t);
							
						}else if(command.equals("DOWNLOAD_TEXT_")){//下载文本
							//开启线程传输
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_Text(socket,tags),"DOWNLOAD_TEXT_");
							t.start();
							list.add(t);
						}else if(command.equals("UPLOAD_PICTURE_")){//上传图片
							int nSize0=json.getInt("nSize");
							//开启线程传输
							Thread t=new Thread(new UploadStream_File(socket,nSize0,uid),"UPLOAD_PICTURE_");
							t.start();
							list.add(t);	
						}else if(command.equals("DOWNLOAD_PICTURE_")){//下载图片
							//开启线程传输
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_File(socket,tags),"UPLOAD_PICTURE_");
							t.start();
							list.add(t);
						}else if(command.equals("UPLOAD_VlINK")){//上传视频链接
							int nSize0=json.getInt("nSize");
							//开启线程传输
							Thread t=new Thread(new UploadStream_Vlink(socket,nSize0,uid),"UPLOAD_VlINK");
							t.start();
							list.add(t);	
						}else if(command.equals("DOWNLOAD_VlINK")){//下载视频链接
							//开启线程传输
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_Vlink(socket,tags),"DOWNLOAD_VlINK");
							t.start();
							list.add(t);
							
						}else if(command.equals("DOWNLOAD_PICTURE_BY_PID")){//按照图片名称下载图片
							//开启线程传输
							String pid=json.getString("pid"); //图片的ID
							Thread t=new Thread(new DownloadStream_Picture_ID(socket,pid),"DOWNLOAD_PICTURE_BY_PID");
							t.start();
							list.add(t);
						}
						
						System.out.println("FTP下一次指令");
					}catch(JSONException e){
						try {//json格式出错，直接kill掉这个socket
							out.write("{\"state\":930,\"msg\":\"JSONObject['数据'] not found!\"}".getBytes("UTF-8"));
							out.flush();
							System.out.println("FileServer接收的json格式错误");
							logger.info("FileServer接收的json格式错误 :"+e.getMessage());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} catch (Exception e) {		
						logger.info(e.getMessage());
					}						 			 
					try
					{
						for(Thread thread : list)
						{
							thread.join();
							System.out.println("文件服务器线程ID:"+thread.getId()+"join");
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}					
					//flag=false;
				 }
				 long end = System.currentTimeMillis();//结束时间
				 System.out.println("文件服务器线程执行总时长：" + (end-start));
			 }else{
				 out.write("{\"state\":530,\"msg\":\"您的IP地址不正确或没有登陆,不能操作!\"}".getBytes("UTF-8"));
				 out.flush();
			 }
			
		} catch(JSONException e){
			try {//json格式出错，直接kill掉这个socket
				out.write("{\"state\":4,\"msg\":\"JSON数据格式错误或找不到对应的KEY值!\"}".getBytes("UTF-8"));
				out.flush();
				System.out.println("FileServer接收的json格式错误");
				logger.info(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}catch (NullPointerException e) {
			try {
				out.write("{\"state\":531,\"msg\":\"您没有登陆,不能操作!\"}".getBytes("UTF-8"));
				out.flush();
				logger.info(e.getMessage());
			}  catch (IOException e1) {		
				e1.printStackTrace();
			}
			 
		}catch (Exception e) {
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
				
			System.out.println("ftp-socket is close?:"+socket.isClosed());
		}
		
	}

	public static void openServer() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(1000);
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(17773);
		while (true) {
			Socket socket = server.accept();
			service.execute(new FileServer(socket));
			System.out.println(socket.getInetAddress()+"已经连接文件服务器");
		}
	}
}
