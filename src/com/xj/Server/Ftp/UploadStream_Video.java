package com.xj.Server.Ftp;

import java.net.Socket;

import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Text_Service;
import com.xj.Server.service.Video_Service;

public class UploadStream_Video implements Runnable{
	
	@SuppressWarnings({ "static-access" })
	private Video_Service video_service=new FactoryService().getVideo_Service();//操作视频链接业务
	private Socket socket=null;
	private int nSize=0;
	private String uid=null;
	public UploadStream_Video(Socket socket,int nSize,String uid){
		this.socket=socket;
		this.nSize=nSize;
		this.uid=uid;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
