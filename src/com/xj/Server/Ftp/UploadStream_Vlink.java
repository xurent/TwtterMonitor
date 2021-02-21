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
	private VlinkService vlink_service=new FactoryService().getVlinkService();//��������ҵ��
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
		long start = System.currentTimeMillis(); //��ʼʱ��
		
		try {	
				System.out.println("�ϴ���nSize:"+nSize);
				in=socket.getInputStream();
				out=socket.getOutputStream();
			
				{//��ʾ�ͻ��˿��Է���������
					out.write("{\"state\":\"501\",\"msg\":\"���Ѿ�׼���ý���������\"}".getBytes("UTF-8"));
					out.flush();
				}
				
				char []buff=new char[nSize];	//����ռ�				
				//ѭ����������
				while((new InputStreamReader(in, "utf-8").read(buff)!=-1)){			
					//String json_str= new String(buff,0,buff.length);							
					if((socket.getInputStream().available())==0){
						//System.out.println("if s:"+s);
						break;
					}					
				}			
				String strbuff=new String(buff,0,buff.length);			
				System.out.println("strbuff"+strbuff);					
				
				if(strbuff.length()>0){ //������									
					JSONObject json = JSONObject.fromObject(strbuff);// ����
					String text_=json.getString("text_");//��ȡ����
					String Tag=json.getString("tag");//��ȡ��ǩ
					//�и�
					String []dataArrList=text_.split("\r@\n");
					
					int nLen=dataArrList.length,j=0;  //�и����������
					System.out.println("nLen:"+nLen);
					String errorNum="";   //����ڼ��������ϴ��ɹ��ģ��ڼ���ʧ��
					int i=0;//��¼�ɹ�������
					while(j<nLen){		
						System.out.println(dataArrList[j]);
						//����һ���ı�����
						int flag=vlink_service.uploadVlink(new Vlink(new Long(System.currentTimeMillis()).toString(),dataArrList[j],Tag,uid,new Date()));
						
						if(flag>0){//�ɹ��ϴ��ľ���numֵ
							i++;
						}else{//�ϴ�ʧ�ܵ�numֵ
							errorNum+=j+1+",";
						}
						j++;
						
					}				
					String send="{\"state\":\"400\",\"sucessNum\":\""+i+"\",\"errorNum\":\""+errorNum+"\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}else{
					
					out.write("{\"msg\":\"�ϴ�ʧ��\",\"state\":\"430\"}".getBytes("UTF-8"));
					out.flush();
				}				
				System.out.println("ftp�������ӽ���");
				
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
			long end = System.currentTimeMillis();//����ʱ��
			System.out.println(Thread.currentThread().getName()+" ���߳�ִ��ʱ����" + (end-start)+"ms");
			
		}		
	}
		
	
}

