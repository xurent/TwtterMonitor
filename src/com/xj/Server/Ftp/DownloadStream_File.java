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

public class DownloadStream_File implements Runnable {
	
	@SuppressWarnings("static-access")
	Picture_Service picture_Service=new FactoryService().getPicture_Service();
	private Socket socket;
	private String tags;
	public DownloadStream_File(Socket socket, String tags) {
		this.socket=socket;
		this.tags=tags;
	}
	@Override
	public void run() {
		InputStream in=null;
		 OutputStream  out=null;
		try {
			   in=socket.getInputStream();
			   out=socket.getOutputStream();
			  
			 List<Picture_>pictures= picture_Service.downloadPicture_ByTag(tags);
			 int nSize=pictures.size();
			 if(nSize>0){
				 //��ͻ��˷��Ͳ�ѯ��������
				 {
					 String send="{\"state\":\"701\",\"nSize\":\""+nSize+"\"}";
					 out.write(send.getBytes("utf-8"));
					 out.flush();
				 }
				 //���տͻ���Ӧ��
				 
					// �ȴ��ͻ�����һ����Ϣ
					byte[] bytes = new byte[256];
					int len = in.read(bytes);
					String json_str = new String(bytes, 0, len,"utf-8");
					JSONObject json = JSONObject.fromObject(json_str);// ����
					if(json.getInt("state")==600){				
						 StringBuffer stringBuffer = new StringBuffer();
						 for(Picture_ p:pictures){
							 String sendfa="{\"Pic_id\":\""+p.getPic_ID()+"\",\"Pic_size\":\""+p.getPic_size()+"\"}\r\n";
							 stringBuffer.append(sendfa);
						 }
						 out.write(stringBuffer.toString().getBytes("utf-8"));
						 out.flush();
						// �ȴ��ͻ�����һ����Ϣ
						 
//						 if(json.getInt("state")==601){
							 
							 InputStream filein=null;
							 int j=0;
							 while(j<nSize){//ѭ�������ļ�
								 
								 byte[] bytes0 = new byte[256];
								 len = in.read(bytes0);
								 json_str = new String(bytes0, 0, len,"utf-8");
								 json = JSONObject.fromObject(json_str);// ����
								 if(json.getInt("state")==601){//�ȴ�
									 
								 //�ӱ��ض�ȡ�ļ���
								 filein=new FileInputStream(pictures.get(j).getPic_path()+"\\"+pictures.get(j).getPic_ID());
								 //������
								 byte []filebytes=new byte[4*1024];
								 int length=0; //��ȡ�����ܴ�С
								 int readlength=0;//ÿ�ζ�ȡ�ĳ���
								 
								 while(length!=new Integer(pictures.get(j).getPic_size())){
									// System.out.println("���뷢�ļ�");
									 readlength=filein.read(filebytes, 0, filebytes.length);//��ȡ�ļ�				
									// System.out.println("���ļ�����:"+readlength);
									 //��������
									 out.write(filebytes,0,readlength);
									 length+=readlength;
								 }
								 out.flush();
								 System.out.println("�������"+j+"���ļ�");
								 j++;
								 }
								 
								 System.out.println("j:"+j);
//								 //�ȴ��ͻ��˽�����һ���ļ�
//								 byte[] bytes1 = new byte[256];
//								 len = in.read(bytes1);
//								 json_str = new String(bytes1, 0, len,"utf-8");
//								 json = JSONObject.fromObject(json_str);// ����
//								 if(json.getInt("state")==601){
//									 System.out.println("������һ��ѭ��");
//									 continue;
//								 }else{
//									 System.out.println("���ղ����ͻ���601״̬���߳̽�������״̬");
//									 in.wait();//�̵߳ȴ�������
//								 }
							 }
							 System.out.println("ѭ���������ļ�");
						 	filein.close(); //�ر��ļ���
					}else{
						System.out.println("�ղ����ͻ��˵�һ��Ӧ��");
					}
				 
			 }else{//�Ҳ��������������ļ�			 
				 out.write("{\"state\":\"730\",\"msg\":\"�Ҳ��������������ļ�\"}".getBytes("utf-8"));
				 out.flush();
			 }
			 
			System.out.println("�ļ����ؽ���");
		} catch(JSONException e){
			try {//json��ʽ����ֱ��kill�����socket
				out.write("{\"state\":901,\"msg\":\"JSON���ݸ�ʽ����\"}".getBytes("UTF-8"));
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
