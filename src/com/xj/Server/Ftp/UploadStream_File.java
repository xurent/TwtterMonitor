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
	//�����ļ�·��
	private final String savePath=System.getProperty("user.dir")+"\\picture"; //�õ����̵�·��
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
				System.out.println("����·��:"+savePath);
				in=socket.getInputStream();
				out=socket.getOutputStream();
				System.out.println("�ϴ�����Ƶ������"+nSize);
			{
				out.write("{\"state\":\"601\",\"msg\":\"�Ѿ�����������ݽ׶Σ��뷢���ļ���С�����ƣ�\"}".getBytes("UTF-8"));
				out.flush();
			}
			int i=0; 
			char []buff=new char[8*1024];	//����ռ�
			try{
				 
				//ѭ�������ļ������ƺʹ�С
				while(((new InputStreamReader(in, "utf-8").read(buff)))!=-1){	
					
					if((socket.getInputStream().available())==0){
						System.out.println("��ȡ������size��filename����");
						break;
					}						
				}
				String dataList=new String(buff,0,buff.length);
				JSONObject json = JSONObject.fromObject(dataList);// ����
				String fileName=json.getString("filename");//��ȡ�ļ�����
				String fileTag=json.getString("tag"); //��ȡ��ǩ
				String fileSize=json.getString("filesize");	//�ļ���С
				
				String[]fileNamedata=fileName.split(",");
				String []fileSizedata=fileSize.split(",");			
				
				//���ļ����ƺʹ�С����			
				//��ʾ
				{
					out.write("{\"state\":\"602\",\"msg\":\"���Խ����ļ������ˣ�\"}".getBytes("UTF-8"));
					out.flush();
				}
				String saveFileName="";
				String realSavePath="";
				///////////////////////
				byte []dataBuff=new byte[(int)Long.parseLong(fileSizedata[i])];//����ռ�  2*(int)Long.parseLong(fileSizedata[i])
			while(i<nSize){		
				int nLen=0,readlen=0;
				
				OutputStream outfile=null;
				if(fileNamedata[i].length()>0&&fileSizedata[i].length()>0){
					//��������·��
					saveFileName=makeFileName(fileNamedata[i]);
					realSavePath=makePath(saveFileName,savePath);		
					System.out.println("saveFileName:"+fileNamedata[i]);
					System.out.println("saveFileName:"+saveFileName);
					System.out.println("saveFile Size:"+Long.parseLong(fileSizedata[i]));
					//�ȴ��������
					outfile= new FileOutputStream(realSavePath+"\\"+saveFileName);
					//����������
				}
				//ѭ����������	
				String data="";//����			
				while(nLen!=(new Integer(fileSizedata[i]))){					
					readlen=in.read(dataBuff);
					System.out.println("��ȡ��readlen:"+readlen+"��ȡ��nLen:"+nLen);
					System.out.println("Integer(fileSizedata[i]):"+new Integer(fileSizedata[i]));
					System.out.println("��"+i+"���ļ��ϴ�����:"+((double)nLen/(double)new Integer(fileSizedata[i]))*100+"%");					

					outfile.write(dataBuff,0,(int)readlen);
					nLen+=readlen;
					
				}			
				System.out.println("��ȡ����һ������");
				//�ر�
				outfile.close();			
				//д�����ݿ�
				int flag=picture_Service.uploadPicture(new Picture_(saveFileName,fileNamedata[i],fileSizedata[i],realSavePath,fileTag,uid,new Date()));	
				if(flag>0){																													
						System.out.println("�ļ���Ϣ���ֵ����ݿ�ɹ�");											
					//���ͳɹ�
					String send="{\"state\":\"600\",\"msg\":\"�ɹ��ϴ���������\",\"nsize\":\""+i+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
							
				}else{
					System.out.println("�ļ���Ϣ���ֵ����ݿ�ʧ��");
					String send="{\"state\":\"630\",\"msg\":\"ʧ���ϴ���������\",\"nsize\":\""+i+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}				
				i++;
				System.out.println("i:"+i);
			}
			System.out.println("����");
			
			}catch(JSONException e){
				try {
					out.write("{\"state\":931,\"msg\":\"JSONObject['filename'] or JSONObject['context'] or JSONObject['size']  not found!\"}".getBytes("UTF-8"));
					out.flush();	
					
				} catch (Exception e1) {	
					e1.printStackTrace();
				}		
			}
			System.out.println("�ϴ���Ƶ��������!");
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		
	}
	private String makePath(String saveFileName, String savePath) {
		//�õ��ļ����֣����ڴ��ַ�У�hashcodeֵ
		int hashCode=saveFileName.hashCode();
		int dir1=hashCode&0xf; //dir��ֵ���������������Χ0-15
		int dir2=(hashCode>>4)&0xf; //�õ��Ľ��������0-15
		//��dir1��dir2�����µĴ洢�ļ�Ŀ¼
		String dir=savePath+"\\"+dir1+"\\"+dir2;
		File file=new File(dir);
		if(!file.exists()){
			boolean flag=file.mkdirs();
			System.out.println("����"+flag);
		}
		System.out.println(dir);
		return dir;
	}

	private String makeFileName(String filename) {
		// uuid	
		return UUID.randomUUID().toString()+"_"+filename;
	}
}
