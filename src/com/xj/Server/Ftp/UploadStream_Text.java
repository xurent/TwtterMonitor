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
	private Text_Service text_service=new FactoryService().getText_Service();//�����ı�ҵ��
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
				//List<String> dataArr=new ArrayList<String>();		
				//StringBuffer datas=new StringBuffer();		
				//InputStreamReader br=new InputStreamReader(socket.getInputStream(), "utf-8"); 
				int s;
				//ѭ����������
				while((new InputStreamReader(socket.getInputStream(), "utf-8").read(buff)!=-1)){			
					//String json_str= new String(buff,0,buff.length);							
					if((s=socket.getInputStream().available())==0){
						System.out.println("if s:"+s);
						break;
					}
					
				}			
				String strbuff=new String(buff,0,buff.length);			
				System.out.println("strbuff"+strbuff);
					//JSONObject json = JSONObject.fromObject(json_str);// ����	
					//dataArr.add(json_str); //����׷�Ӵ���data����
				
					//String text_=json.getString("text_");//��ȡ����
					//String Upload_N=json.getString("upload_name");//�ϴ���
					//String Tag=json.getString("tag");//��ǩ
							
					//����һ���ı�����
				//	int flag=text_service.uploadText_(new Text_(System.currentTimeMillis(),text_,Tag,uid,new Date()));
//					if(flag>0){
//						String send="{\"nsize\":\""+i+"\",\"msg\":\"�ϴ��ɹ�\",\"state\":\"400\"}";//��ɵ�i�������ϴ�
//						out.write(send.getBytes("UTF-8"));
//					}else{
//						String send="{\"nsize\":\""+i+"\",\"msg\":\"�ϴ�ʧ��\",\"state\":\"430\"}";//��i�������ϴ�ʧ��
//						out.write(send.getBytes("UTF-8"));
//					}							
					
				//int nums=dataArr.size(); //data��������
				
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
						//����һ���ı�����
						int flag=text_service.uploadText_(new Text_(System.currentTimeMillis()+1,dataArrList[j],Tag,uid,new Date()));
						
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
				
				System.out.println("ftp�����ı�����");
				
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
			long end = System.currentTimeMillis();//����ʱ��
			System.out.println(Thread.currentThread().getName()+" ���߳�ִ��ʱ����" + (end-start)+"ms");
		}
		System.out.println("�ϴ��ļ�����");
	}

}
