package com.xj.Server.Ftp;

import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.VlinkService;

import net.sf.json.JSONException;

public class DownloadStream_Vlink implements Runnable{
	
	@SuppressWarnings("static-access")
	private VlinkService vlink_service=new FactoryService().getVlinkService();//操作链接业务
	private Socket socket;
	private String tags;
	
	public DownloadStream_Vlink(Socket socket, String tags) {
		this.socket=socket;
		this.tags=tags;
	}

	@Override
	public void run() {
		OutputStream out = null;
		
		long start = System.currentTimeMillis(); //开始时间
		try {							
				out=socket.getOutputStream();
				//获取要下载的文本
				List<String>list_link=vlink_service.downloadVlink_ByTag(tags);
				if(list_link.size()>0){
								
//					//发送给客户端有多少条数据
//					String send="{\"nSize\":\""+Integer.toString(list_text.size())+"\",\"msg\":\"要接受的文本信息的数量\"}";
//					out.write(send.getBytes("UTF-8"));
//					out.flush();			
					//循环发送数据
					for(String s:list_link){
						out.write(s.getBytes("UTF-8"));
						out.flush();
					}
					
				}else{
					out.write("{\"state\":\"430\",\"msg\":\"找不到满足条件的链接\"}".getBytes("UTF-8"));
					out.flush();
				}			
				System.out.println("ftp传输链接结束");
		}catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['command'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch(SocketException e){
			
			System.out.println("SocketException");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally{
			
			long end = System.currentTimeMillis();//结束时间
			System.out.println(Thread.currentThread().getName()+" 子线程执行时长：" + (end-start)+"ms");
		}
		System.out.println("下载链接结束");

	}
}
