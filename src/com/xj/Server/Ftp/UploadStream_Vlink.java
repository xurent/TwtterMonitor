package com.xj.Server.Ftp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import com.xj.Server.model.Vlink;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.VlinkService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UploadStream_Vlink implements Runnable {

	@SuppressWarnings({ "static-access" })
	private VlinkService vlink_service=new FactoryService().getVlinkService();//操作链接业务
	private Socket socket=null;
	private int nSize=0;
	private String uid=null;
	public UploadStream_Vlink(Socket socket,int nSize,String uid){
		this.socket=socket;
		this.nSize=nSize;
		this.uid=uid;
	}
	
	@Override
	public void run() {
		
		InputStream in = null;
		OutputStream out = null;
		long start = System.currentTimeMillis(); //开始时间
		
		try {	
				System.out.println("上传的nSize:"+nSize);
				in=socket.getInputStream();
				out=socket.getOutputStream();
			
				{//提示客户端可以发送数据了
					out.write("{\"state\":\"501\",\"msg\":\"我已经准备好接收数据了\"}".getBytes("UTF-8"));
					out.flush();
				}
				
				char []buff=new char[nSize];	//缓存空间				
				//循环接收数据
				while((new InputStreamReader(in, "utf-8").read(buff)!=-1)){			
					//String json_str= new String(buff,0,buff.length);							
					if((socket.getInputStream().available())==0){
						//System.out.println("if s:"+s);
						break;
					}					
				}			
				String strbuff=new String(buff,0,buff.length);			
				System.out.println("strbuff"+strbuff);					
				
				if(strbuff.length()>0){ //有数据									
					JSONObject json = JSONObject.fromObject(strbuff);// 解析
					String text_=json.getString("text_");//获取内容
					String Tag=json.getString("tag");//获取标签
					//切割
					String []dataArrList=text_.split("\r@\n");
					
					int nLen=dataArrList.length,j=0;  //切割出来的条数
					System.out.println("nLen:"+nLen);
					String errorNum="";   //存入第几条数据上传成功的，第几条失败
					int i=0;//记录成功的条数
					while(j<nLen){		
						System.out.println(dataArrList[j]);
						//构建一条文本数据
						int flag=vlink_service.uploadVlink(new Vlink(new Long(System.currentTimeMillis()).toString(),dataArrList[j],Tag,uid,new Date()));
						
						if(flag>0){//成功上传的具体num值
							i++;
						}else{//上传失败的num值
							errorNum+=j+1+",";
						}
						j++;
						
					}				
					String send="{\"state\":\"400\",\"sucessNum\":\""+i+"\",\"errorNum\":\""+errorNum+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}else{
					
					out.write("{\"msg\":\"上传失败\",\"state\":\"430\"}".getBytes("UTF-8"));
					out.flush();
				}				
				System.out.println("ftp传输链接结束");
				
		}catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['vlink_'] or JSONObject['tag']  not found!\"}".getBytes("UTF-8"));
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
	}
		
	
}

