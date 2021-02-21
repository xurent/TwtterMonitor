package com.xj.Server.Ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import com.xj.Server.model.Picture_;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Picture_Service;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UploadStream_File implements Runnable{
	
//	@SuppressWarnings("static-access")
//	Video_Service video_Service=new FactoryService().getVideo_Service();
	@SuppressWarnings("static-access")
	Picture_Service picture_Service=new FactoryService().getPicture_Service();
	//保存文件路径
	private final String savePath=System.getProperty("user.dir")+"\\picture"; //得到工程的路径
	private Socket socket;
	private int nSize;
	private String uid=null;
	public UploadStream_File(Socket socket,int nSize,String uid){
		this.socket=socket;
		this.nSize=nSize;
		this.uid=uid;
	}
	@Override
	public void run() {
		
		InputStream in =null;
		OutputStream out=null;
		try {
				System.out.println("保持路径:"+savePath);
				in=socket.getInputStream();
				out=socket.getOutputStream();
				System.out.println("上传的视频数量："+nSize);
			{
				out.write("{\"state\":\"601\",\"msg\":\"已经进入接收数据阶段，请发送文件大小和名称！\"}".getBytes("UTF-8"));
				out.flush();
			}
			int i=0; 
			char []buff=new char[8*1024];	//缓存空间
			try{
				 
				//循环接受文件的名称和大小
				while(((new InputStreamReader(in, "utf-8").read(buff)))!=-1){	
					
					if((socket.getInputStream().available())==0){
						System.out.println("读取完所有size和filename数据");
						break;
					}						
				}
				String dataList=new String(buff,0,buff.length);
				JSONObject json = JSONObject.fromObject(dataList);// 解析
				String fileName=json.getString("filename");//获取文件名称
				String fileTag=json.getString("tag"); //获取标签
				String fileSize=json.getString("filesize");	//文件大小
				
				String[]fileNamedata=fileName.split(",");
				String []fileSizedata=fileSize.split(",");			
				
				//存文件名称和大小数组			
				//提示
				{
					out.write("{\"state\":\"602\",\"msg\":\"可以接收文件数据了！\"}".getBytes("UTF-8"));
					out.flush();
				}
				String saveFileName="";
				String realSavePath="";
				///////////////////////
				byte []dataBuff=new byte[(int)Long.parseLong(fileSizedata[i])];//缓存空间  2*(int)Long.parseLong(fileSizedata[i])
			while(i<nSize){		
				int nLen=0,readlen=0;
				
				OutputStream outfile=null;
				if(fileNamedata[i].length()>0&&fileSizedata[i].length()>0){
					//创建保存路径
					saveFileName=makeFileName(fileNamedata[i]);
					realSavePath=makePath(saveFileName,savePath);		
					System.out.println("saveFileName:"+fileNamedata[i]);
					System.out.println("saveFileName:"+saveFileName);
					System.out.println("saveFile Size:"+Long.parseLong(fileSizedata[i]));
					//先创建输出流
					outfile= new FileOutputStream(realSavePath+"\\"+saveFileName);
					//建立缓冲区
				}
				//循环接收数据	
				String data="";//数据			
				while(nLen!=(new Integer(fileSizedata[i]))){					
					readlen=in.read(dataBuff);
					System.out.println("读取的readlen:"+readlen+"读取的nLen:"+nLen);
					System.out.println("Integer(fileSizedata[i]):"+new Integer(fileSizedata[i]));
					System.out.println("第"+i+"个文件上传进度:"+((double)nLen/(double)new Integer(fileSizedata[i]))*100+"%");					

					outfile.write(dataBuff,0,(int)readlen);
					nLen+=readlen;
					
				}			
				System.out.println("读取完有一条数据");
				//关闭
				outfile.close();			
				//写入数据库
				int flag=picture_Service.uploadPicture(new Picture_(saveFileName,fileNamedata[i],fileSizedata[i],realSavePath,fileTag,uid,new Date()));	
				if(flag>0){																													
						System.out.println("文件信息保持到数据库成功");											
					//发送成功
					String send="{\"state\":\"600\",\"msg\":\"成功上传该条数据\",\"nsize\":\""+i+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
							
				}else{
					System.out.println("文件信息保持到数据库失败");
					String send="{\"state\":\"630\",\"msg\":\"失败上传该条数据\",\"nsize\":\""+i+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}				
				i++;
				System.out.println("i:"+i);
			}
			System.out.println("结束");
			
			}catch(JSONException e){
				try {
					out.write("{\"state\":931,\"msg\":\"JSONObject['filename'] or JSONObject['context'] or JSONObject['size']  not found!\"}".getBytes("UTF-8"));
					out.flush();	
					
				} catch (Exception e1) {	
					e1.printStackTrace();
				}		
			}
			System.out.println("上传视频操作结束!");
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		
	}
	private String makePath(String saveFileName, String savePath) {
		//拿到文件名字，在内存地址中，hashcode值
		int hashCode=saveFileName.hashCode();
		int dir1=hashCode&0xf; //dir的值，这个与运算结果范围0-15
		int dir2=(hashCode>>4)&0xf; //得到的结果还是在0-15
		//用dir1，dir2构造新的存储文件目录
		String dir=savePath+"\\"+dir1+"\\"+dir2;
		File file=new File(dir);
		if(!file.exists()){
			boolean flag=file.mkdirs();
			System.out.println("创建"+flag);
		}
		System.out.println(dir);
		return dir;
	}

	private String makeFileName(String filename) {
		// uuid	
		return UUID.randomUUID().toString()+"_"+filename;
	}
}
