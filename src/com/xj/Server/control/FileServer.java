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
	private Logger logger=Logger.getLogger(this.getClass()); //��־
	public FileServer(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		String uid = null;
		// ��֤�û��Ƿ��Ѿ���½
		InputStream in = null;
		OutputStream out = null;	
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			// �ȴ��ͻ�����Ϣ
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len,"utf-8");
		
			JSONObject json = JSONObject.fromObject(json_str);// ����
			System.out.println("ftp:"+json.toString());
			uid=json.getString("username");
			
			System.out.println("��ǰ����FTP��uid:"+uid);
			UserInfo u=UserOnlineList.getUserOnlineList().getOnlineUserInfo(uid);
			System.out.println("��uid������:"+u);
			
			 //��ȡ��½�û���Ϣ��IP��ַ
			 Socket userSock=UserOnlineList.getUserOnlineList().getOnlineUserInfo(uid).getSocket();
			 ////////////////////////////////////
			 System.out.println("socketADDR:"+userSock.getInetAddress());
			 System.out.println(userSock.getInetAddress()+"��ȡ����IP");
			 ///////////////////////////////////
			 
			 //�ж��Ѿ���½���û�IP�Ƿ���ڷ��ʵ�IP
			 if(socket.getInetAddress().equals(userSock.getInetAddress())){
				 System.out.println("�������ָ��׶�");		 
				 out.write("{\"state\":500,\"msg\":\"�����ļ�ָ�����״̬!\"}".getBytes("UTF-8"));
				 out.flush();
				 
				 //��ʼҵ��
				 boolean flag=true;
				 long start = System.currentTimeMillis(); //��ʼʱ��
				 while(flag){
					// �ȴ��ͻ�����Ϣ
						byte[] bytes0 = new byte[1024];
						 len = in.read(bytes0);
						 json_str = new String(bytes0, 0, len,"UTF-8");
						 json = JSONObject.fromObject(json_str);// ����
						
					String command= json.getString("command"); //ָ��ͷ
					System.out.println("com:"+command);
					List<Thread> list = new ArrayList<Thread>(); //���߳��б�
								
					try {
						if(command.equals("UPLOAD_TEXT_")){	//�ϴ��ı�
							int nSize=json.getInt("nSize");
							//�����̴߳���
							Thread t=new Thread(new UploadStream_Text(socket,nSize,uid),"UPLOAD_TEXT_");
							t.start();
							list.add(t);
							
						}else if(command.equals("DOWNLOAD_TEXT_")){//�����ı�
							//�����̴߳���
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_Text(socket,tags),"DOWNLOAD_TEXT_");
							t.start();
							list.add(t);
						}else if(command.equals("UPLOAD_PICTURE_")){//�ϴ�ͼƬ
							int nSize0=json.getInt("nSize");
							//�����̴߳���
							Thread t=new Thread(new UploadStream_File(socket,nSize0,uid),"UPLOAD_PICTURE_");
							t.start();
							list.add(t);	
						}else if(command.equals("DOWNLOAD_PICTURE_")){//����ͼƬ
							//�����̴߳���
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_File(socket,tags),"UPLOAD_PICTURE_");
							t.start();
							list.add(t);
						}else if(command.equals("UPLOAD_VlINK")){//�ϴ���Ƶ����
							int nSize0=json.getInt("nSize");
							//�����̴߳���
							Thread t=new Thread(new UploadStream_Vlink(socket,nSize0,uid),"UPLOAD_VlINK");
							t.start();
							list.add(t);	
						}else if(command.equals("DOWNLOAD_VlINK")){//������Ƶ����
							//�����̴߳���
							String tags=json.getString("tag");
							Thread t=new Thread(new DownloadStream_Vlink(socket,tags),"DOWNLOAD_VlINK");
							t.start();
							list.add(t);
							
						}else if(command.equals("DOWNLOAD_PICTURE_BY_PID")){//����ͼƬ��������ͼƬ
							//�����̴߳���
							String pid=json.getString("pid"); //ͼƬ��ID
							Thread t=new Thread(new DownloadStream_Picture_ID(socket,pid),"DOWNLOAD_PICTURE_BY_PID");
							t.start();
							list.add(t);
						}
						
						System.out.println("FTP��һ��ָ��");
					}catch(JSONException e){
						try {//json��ʽ����ֱ��kill�����socket
							out.write("{\"state\":930,\"msg\":\"JSONObject['����'] not found!\"}".getBytes("UTF-8"));
							out.flush();
							System.out.println("FileServer���յ�json��ʽ����");
							logger.info("FileServer���յ�json��ʽ���� :"+e.getMessage());
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
							System.out.println("�ļ��������߳�ID:"+thread.getId()+"join");
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}					
					//flag=false;
				 }
				 long end = System.currentTimeMillis();//����ʱ��
				 System.out.println("�ļ��������߳�ִ����ʱ����" + (end-start));
			 }else{
				 out.write("{\"state\":530,\"msg\":\"����IP��ַ����ȷ��û�е�½,���ܲ���!\"}".getBytes("UTF-8"));
				 out.flush();
			 }
			
		} catch(JSONException e){
			try {//json��ʽ����ֱ��kill�����socket
				out.write("{\"state\":4,\"msg\":\"JSON���ݸ�ʽ������Ҳ�����Ӧ��KEYֵ!\"}".getBytes("UTF-8"));
				out.flush();
				System.out.println("FileServer���յ�json��ʽ����");
				logger.info(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}catch (NullPointerException e) {
			try {
				out.write("{\"state\":531,\"msg\":\"��û�е�½,���ܲ���!\"}".getBytes("UTF-8"));
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
			System.out.println(socket.getInetAddress()+"�Ѿ������ļ�������");
		}
	}
}
