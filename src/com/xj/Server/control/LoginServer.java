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

/*登陆服务器*/

public class LoginServer implements Runnable {

	private Socket socket = null;
	private Logger logger=Logger.getLogger(this.getClass()); //日志
	public LoginServer(Socket socket) {
		this.socket = socket;
		//打印线程ID的名字
		
	}
	// 线程方法
	@SuppressWarnings("static-access")
	public void run() {
		String uid = null;
		// 登录操作
		InputStream in = null;
		OutputStream out = null;	
		
		try {												
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			// 等待客户端信息
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len);
			//System.out.println(json_str);
			//////////////////////////////////////////// {“username”:”13800012461”,”password”:”123456”}
			JSONObject json = JSONObject.fromObject(json_str);// 解析
			
			String username = json.getString("username");
			String password = json.getString("password");
			//String type=json.getString("type");
			System.out.println(username+" "+password);
					
			try {
				//登陆
				uid = new FactoryService().getUserService().login(new User(username,password,null));
				// 登记登录信息
				UserOnlineList.getUserOnlineList().regOnLine(uid, socket);
				String Type=new FactoryService().getUserService().getType(uid);	//获取登陆用户的类型
				String send="{\"state\":0,\"type\":\""+Type+"\",\"msg\":\"登录成功!\"}";
				out.write(send.getBytes("UTF-8"));
				out.flush();
			
				// 陆陆续续的接收客户端发送的指令要求
				while (true) {
					//修改socket超时时间
					socket.setSoTimeout(30*60*1000);
					
					char []buff=new char[2048];
					 len=new InputStreamReader(socket.getInputStream(), "utf-8").read(buff);				
					String command= new String(buff,0,buff.length);					
					//System.out.println(command.length());		
					JSONObject jsoncmd = JSONObject.fromObject(command);// 解析					
					//进入协议解析层
					{Protocol.getProtocol().Protocolanalysis(jsoncmd, socket,uid);	}	
					
				}			
			
			} catch(NullPointerException e){
				out.write("{\"state\":1,\"msg\":\"账户名或者密码错误!\"}".getBytes("UTF-8"));
				out.flush();
				return;
				
			} catch(SocketTimeoutException e){
				
				System.out.println("超时异常");
				out.write("{\"state\":2,\"msg\":\"超时异常!\"}".getBytes("UTF-8"));
				out.flush();
				if(!socket.getKeepAlive()){
					socket.setKeepAlive(true);
					System.out.println("调用socket.setKeepAlive(true)");
				}
				
			}catch(StringIndexOutOfBoundsException e){			
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
				if(!socket.isClosed()){//客户端退出，read异常，关闭socket
					socket.close();
				}
			}catch(SocketException e){
				
				System.out.println("指令读取被关闭");			
				//e.printStackTrace();			
					if(!socket.isClosed()){				
					//socket.shutdownInput();
					//socket.shutdownOutput();
					//socket.setTcpNoDelay(on);
						System.out.println(e.getMessage());
					}
					
			}catch(LoginSQLException e){
				System.out.println("发生数据库异常!");
				out.write("{\"state\":4,\"msg\":\"数据库异常!\"}".getBytes("UTF-8"));
				out.flush();
			}catch (Exception e) {									
			
				System.out.println("未知异常:"+e.getMessage());		
				out.write("{\"state\":3,\"msg\":\"未知异常!\"}".getBytes("UTF-8"));
				out.flush();		
				
			}

		}catch(SocketException e){			
		
			System.out.println("客户端关闭");		
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
			try {//json格式出错，直接kill掉这个socket
				out.write("{\"state\":4,\"msg\":\"JSON数据格式错误或找不到对应的KEY值!\"}".getBytes("UTF-8"));
				out.flush();
				System.out.println(e.getMessage());
				System.out.println("接收的json格式错误");
				logger.info("JSONException:"+e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}catch(SocketTimeoutException e){
				
			System.out.println("用户登陆超时");
			logger.info("SocketTimeoutException:"+e.getMessage());
//			try {
//				socket.setKeepAlive(true);
//			} catch (SocketException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}		
			try {
				out.write("{\"state\":403,\"msg\":\"登陆操作超时!\"}".getBytes("UTF-8"));
				out.flush();
			}  catch (Exception e1) {
				e1.printStackTrace();
			}
			
			return;
		}catch (Exception e) {
		
				e.printStackTrace();
				System.out.println("接收异常");
		} finally {
			// 结束后把连接关闭
			try {
				// 如果遇到突然关闭或者是关闭的话 我们需要在列表里去除此账户
				UserOnlineList.getUserOnlineList().logout(uid);
				in.close();
				out.close();
				InetAddress ip=socket.getInetAddress();
				if(socket.isInputShutdown()){
					System.out.println("调用socket.shutdownInput");
					socket.shutdownInput();
				}
//				if(socket.getSoLinger()==-1){
//					socket.setSoLinger(true, 0);
//				}
				socket.close();
				System.out.println(ip+"的登陆线程结束关闭");
				logger.info(ip+"的登陆线程结束关闭");
			} catch (Exception e2) {
			}
		}
			
		
}


	public static void openServer() throws Exception {
		// 线程池
		ExecutorService execute = Executors.newFixedThreadPool(1000);
		// 开启了 TCP 4001 端口 用于登录业务
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(9443);
		//server.bind(new InetSocketAddress("192.168.31.66",4000));
		// 死循环的目的是可以无限服务
		while (true) {
			Socket socket = server.accept();
			socket.setSoTimeout(15*1000);  //15秒超时时间
			//socket.setKeepAlive(true);//如果我们设置了SO_KEEPALIVE，TCP在距离上一次TCP包交互2个小时（取决于操作系统和TCP实现，规范建议不低于2小时）后，会发送一个探测包给另一端，如果接收不到响应，则在75秒后重新发送，连续10次仍然没有响应，则认为对方已经关闭，系统会将该连接关闭
			execute.execute(new LoginServer(socket));
			//socket.setReuseAddress(true);
			
			System.out.println(socket.getInetAddress()+"--"+socket.getPort()+"登陆");
			
		}

	}


}


