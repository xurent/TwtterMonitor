package com.xj.Server.Ftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.xj.Server.model.Picture_;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Picture_Service;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class DownloadStream_Picture_ID implements Runnable {
	
	@SuppressWarnings("static-access")
	Picture_Service picture_Service=new FactoryService().getPicture_Service();
	private Socket socket;
	private String pid;
	
	public DownloadStream_Picture_ID(Socket socket, String Pid) {
		this.socket=socket;
		this.pid=Pid;
	}
	@Override
	public void run() {
		InputStream in=null;
		OutputStream  out=null;
		 
		 try {
			 in=socket.getInputStream();
			  out=socket.getOutputStream();
			 
			 List<Picture_>pictures= picture_Service.downloadPicture_ByID(pid);
			 int nSize=pictures.size();
			 if(nSize>0){
				 //向客户端发送查询到的条数
				 {
					 String send="{\"state\":\"701\",\"nSize\":\""+nSize+"\"}";
					 out.write(send.getBytes("utf-8"));
					 out.flush();
				 }
				// 等待客户端下一步信息
					byte[] bytes = new byte[256];
					int len = in.read(bytes);
					String json_str = new String(bytes, 0, len,"utf-8");
					JSONObject json = JSONObject.fromObject(json_str);// 解析
					if(json.getInt("state")==600){				
						 int i=0;
						 for(Picture_ p:pictures){
							 String sendfa="{\"Pic_id\":\""+p.getPic_ID()+"\",\"Pic_size\":\""+p.getPic_size()+"\"}\r\n";
							 out.write(sendfa.getBytes("utf-8"));
							 out.flush();
						 }
						 
						// 等待客户端下一步信息					 
							 InputStream filein=null;
							 int j=0;
							 while(j<nSize){//循环发送文件
								 
								 byte[] bytes0 = new byte[256];
								 len = in.read(bytes0);
								 json_str = new String(bytes0, 0, len,"utf-8");
								 json = JSONObject.fromObject(json_str);// 解析
								 if(json.getInt("state")==601){//等待
									 
								 //从本地读取文件流
								 filein=new FileInputStream(pictures.get(j).getPic_path()+"\\"+pictures.get(j).getPic_ID());
								 //缓冲区
								 byte []filebytes=new byte[4*1024];
								 int length=0; //读取长度总大小
								 int readlength=0;//每次读取的长度
								 
								 while(length!=new Integer(pictures.get(j).getPic_size())){
									// System.out.println("进入发文件");
									 readlength=filein.read(filebytes, 0, filebytes.length);//读取文件				
									/// System.out.println("读文件长度:"+readlength);
									 //发送数据
									 out.write(filebytes,0,readlength);
									 length+=readlength;
								 }
								 out.flush();
								 System.out.println("发送完第"+j+"个文件");
								 j++;
								 }
								 
								
							 }
							 System.out.println("循环发送完文件");
//						 
					}else{
						System.out.println("收不到客户端第一次应答");
					}
				 
			 }else{//找不到满足条件的文件			 
				 out.write("{\"state\":\"730\",\"msg\":\"找不到满足条件的文件\"}".getBytes("utf-8"));
				 out.flush();
			 }
			 
			System.out.println("文件下载结束");
		} catch(JSONException e){
			try {//json格式出错，直接kill掉这个socket
				out.write("{\"state\":901,\"msg\":\"JSON数据格式错误\"}".getBytes("UTF-8"));
				out.flush();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}

	}

}
