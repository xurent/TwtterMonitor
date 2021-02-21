package com.xj.Server.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.User;
import com.xj.Server.protocol.Protocol;
import com.xj.Server.service.FactoryService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/*��½������*/

public class LoginServer implements Runnable {

	private Socket socket = null;
	private Logger logger=Logger.getLogger(this.getClass()); //��־
	public LoginServer(Socket socket) {
		this.socket = socket;
		//��ӡ�߳�ID������
		
	}
	// �̷߳���
	@SuppressWarnings("static-access")
	public void run() {
		String uid = null;
		// ��¼����
		InputStream in = null;
		OutputStream out = null;	
		
		try {												
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			// �ȴ��ͻ�����Ϣ
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len);
			//System.out.println(json_str);
			//////////////////////////////////////////// {��username��:��13800012461��,��password��:��123456��}
			JSONObject json = JSONObject.fromObject(json_str);// ����
			
			String username = json.getString("username");
			String password = json.getString("password");
			//String type=json.getString("type");
			System.out.println(username+" "+password);
					
			try {
				//��½
				uid = new FactoryService().getUserService().login(new User(username,password,null));
				// �Ǽǵ�¼��Ϣ
				UserOnlineList.getUserOnlineList().regOnLine(uid, socket);
				String Type=new FactoryService().getUserService().getType(uid);	//��ȡ��½�û�������
				String send="{\"state\":0,\"type\":\""+Type+"\",\"msg\":\"��¼�ɹ�!\"}";
				out.write(send.getBytes("UTF-8"));
				out.flush();
			
				// ½½�����Ľ��տͻ��˷��͵�ָ��Ҫ��
				while (true) {
					//�޸�socket��ʱʱ��
					socket.setSoTimeout(30*60*1000);
					
					char []buff=new char[2048];
					 len=new InputStreamReader(socket.getInputStream(), "utf-8").read(buff);				
					String command= new String(buff,0,buff.length);					
					//System.out.println(command.length());		
					JSONObject jsoncmd = JSONObject.fromObject(command);// ����					
					//����Э�������
					{Protocol.getProtocol().Protocolanalysis(jsoncmd, socket,uid);	}	
					
				}			
			
			} catch(NullPointerException e){
				out.write("{\"state\":1,\"msg\":\"�˻��������������!\"}".getBytes("UTF-8"));
				out.flush();
				return;
				
			} catch(SocketTimeoutException e){
				
				System.out.println("��ʱ�쳣");
				out.write("{\"state\":2,\"msg\":\"��ʱ�쳣!\"}".getBytes("UTF-8"));
				out.flush();
				if(!socket.getKeepAlive()){
					socket.setKeepAlive(true);
					System.out.println("����socket.setKeepAlive(true)");
				}
				
			}catch(StringIndexOutOfBoundsException e){			
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
				if(!socket.isClosed()){//�ͻ����˳���read�쳣���ر�socket
					socket.close();
				}
			}catch(SocketException e){
				
				System.out.println("ָ���ȡ���ر�");			
				//e.printStackTrace();			
					if(!socket.isClosed()){				
					//socket.shutdownInput();
					//socket.shutdownOutput();
					//socket.setTcpNoDelay(on);
						System.out.println(e.getMessage());
					}
					
			}catch(LoginSQLException e){
				System.out.println("�������ݿ��쳣!");
				out.write("{\"state\":4,\"msg\":\"���ݿ��쳣!\"}".getBytes("UTF-8"));
				out.flush();
			}catch (Exception e) {									
			
				System.out.println("δ֪�쳣:"+e.getMessage());		
				out.write("{\"state\":3,\"msg\":\"δ֪�쳣!\"}".getBytes("UTF-8"));
				out.flush();		
				
			}

		}catch(SocketException e){			
		
			System.out.println("�ͻ��˹ر�");		
			logger.info("SocketException:"+e.getMessage());
			//e.printStackTrace();
			try {				
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
				//System.out.println(socket.isClosed());
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}catch(JSONException e){
			try {//json��ʽ����ֱ��kill�����socket
				out.write("{\"state\":4,\"msg\":\"JSON���ݸ�ʽ������Ҳ�����Ӧ��KEYֵ!\"}".getBytes("UTF-8"));
				out.flush();
				System.out.println(e.getMessage());
				System.out.println("���յ�json��ʽ����");
				logger.info("JSONException:"+e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}catch(SocketTimeoutException e){
				
			System.out.println("�û���½��ʱ");
			logger.info("SocketTimeoutException:"+e.getMessage());
//			try {
//				socket.setKeepAlive(true);
//			} catch (SocketException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}		
			try {
				out.write("{\"state\":403,\"msg\":\"��½������ʱ!\"}".getBytes("UTF-8"));
				out.flush();
			}  catch (Exception e1) {
				e1.printStackTrace();
			}
			
			return;
		}catch (Exception e) {
		
				e.printStackTrace();
				System.out.println("�����쳣");
		} finally {
			// ����������ӹر�
			try {
				// �������ͻȻ�رջ����ǹرյĻ� ������Ҫ���б���ȥ�����˻�
				UserOnlineList.getUserOnlineList().logout(uid);
				in.close();
				out.close();
				InetAddress ip=socket.getInetAddress();
				if(socket.isInputShutdown()){
					System.out.println("����socket.shutdownInput");
					socket.shutdownInput();
				}
//				if(socket.getSoLinger()==-1){
//					socket.setSoLinger(true, 0);
//				}
				socket.close();
				System.out.println(ip+"�ĵ�½�߳̽����ر�");
				logger.info(ip+"�ĵ�½�߳̽����ر�");
			} catch (Exception e2) {
			}
		}
			
		
}


	public static void openServer() throws Exception {
		// �̳߳�
		ExecutorService execute = Executors.newFixedThreadPool(1000);
		// ������ TCP 4001 �˿� ���ڵ�¼ҵ��
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(9443);
		//server.bind(new InetSocketAddress("192.168.31.66",4000));
		// ��ѭ����Ŀ���ǿ������޷���
		while (true) {
			Socket socket = server.accept();
			socket.setSoTimeout(15*1000);  //15�볬ʱʱ��
			//socket.setKeepAlive(true);//�������������SO_KEEPALIVE��TCP�ھ�����һ��TCP������2��Сʱ��ȡ���ڲ���ϵͳ��TCPʵ�֣��淶���鲻����2Сʱ���󣬻ᷢ��һ��̽�������һ�ˣ�������ղ�����Ӧ������75������·��ͣ�����10����Ȼû����Ӧ������Ϊ�Է��Ѿ��رգ�ϵͳ�Ὣ�����ӹر�
			execute.execute(new LoginServer(socket));
			//socket.setReuseAddress(true);
			
			System.out.println(socket.getInetAddress()+"--"+socket.getPort()+"��½");
			
		}

	}


}


