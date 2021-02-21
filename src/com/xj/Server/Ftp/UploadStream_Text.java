package com.xj.Server.Ftp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import com.xj.Server.model.Text_;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Text_Service;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UploadStream_Text implements Runnable{
	@SuppressWarnings({ "static-access" })
	private Text_Service text_service=new FactoryService().getText_Service();//操作文本业务
	private Socket socket=null;
	private int nSize=0;
	private String uid=null;
	public UploadStream_Text(Socket socket,int nSize,String uid){
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
				//List<String> dataArr=new ArrayList<String>();		
				//StringBuffer datas=new StringBuffer();		
				//InputStreamReader br=new InputStreamReader(socket.getInputStream(), "utf-8"); 
				int s;
				//循环接收数据
				while((new InputStreamReader(socket.getInputStream(), "utf-8").read(buff)!=-1)){			
					//String json_str= new String(buff,0,buff.length);							
					if((s=socket.getInputStream().available())==0){
						System.out.println("if s:"+s);
						break;
					}
					
				}			
				String strbuff=new String(buff,0,buff.length);			
				System.out.println("strbuff"+strbuff);
					//JSONObject json = JSONObject.fromObject(json_str);// 解析	
					//dataArr.add(json_str); //数据追加存入data数组
				
					//String text_=json.getString("text_");//获取内容
					//String Upload_N=json.getString("upload_name");//上传者
					//String Tag=json.getString("tag");//标签
							
					//构建一条文本数据
				//	int flag=text_service.uploadText_(new Text_(System.currentTimeMillis(),text_,Tag,uid,new Date()));
//					if(flag>0){
//						String send="{\"nsize\":\""+i+"\",\"msg\":\"上传成功\",\"state\":\"400\"}";//完成第i条数据上传
//						out.write(send.getBytes("UTF-8"));
//					}else{
//						String send="{\"nsize\":\""+i+"\",\"msg\":\"上传失败\",\"state\":\"430\"}";//第i条数据上传失败
//						out.write(send.getBytes("UTF-8"));
//					}							
					
				//int nums=dataArr.size(); //data数据数量
				
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
						//构建一条文本数据
						int flag=text_service.uploadText_(new Text_(System.currentTimeMillis()+1,dataArrList[j],Tag,uid,new Date()));
						
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
				
				System.out.println("ftp传输文本结束");
				
		}catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['text_'] or JSONObject['tag']  not found!\"}".getBytes("UTF-8"));
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
//			try {
//				if(in!=null)
//					in.close();
//				if(out!=null)
//					out.close();
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
			long end = System.currentTimeMillis();//结束时间
			System.out.println(Thread.currentThread().getName()+" 子线程执行时长：" + (end-start)+"ms");
		}
		System.out.println("上传文件结束");
	}

}
