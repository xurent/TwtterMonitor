package com.xj.Server.Ftp;

import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Text_Service;

import net.sf.json.JSONException;

public class DownloadStream_Text implements Runnable {
	
	@SuppressWarnings("static-access")
	private Text_Service text_service=new FactoryService().getText_Service();//�����ı�ҵ��
	private Socket socket;
	private String tags;
	
	public DownloadStream_Text(Socket socket, String tags) {
		this.socket=socket;
		this.tags=tags;
	}

	@Override
	public void run() {
		OutputStream out = null;
		//Text_ test=null; //�ı�����
		long start = System.currentTimeMillis(); //��ʼʱ��
		try {							
				out=socket.getOutputStream();
				//��ȡҪ���ص��ı�
				List<String>list_text=text_service.downloadText_ByTag(tags);
				if(list_text.size()>0){
								
//					//���͸��ͻ����ж���������
//					String send="{\"nSize\":\""+Integer.toString(list_text.size())+"\",\"msg\":\"Ҫ���ܵ��ı���Ϣ������\"}";
//					out.write(send.getBytes("UTF-8"));
//					out.flush();			
					//ѭ����������
					for(String s:list_text){
						out.write(s.getBytes("UTF-8"));
						out.flush();
					}
					
				}else{
					out.write("{\"state\":\"430\",\"msg\":\"�Ҳ��������������ı��ļ�\"}".getBytes("UTF-8"));
					out.flush();
				}
				
				System.out.println("ftp�����ı�����");
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
//			try {			
//			if(out!=null)
//					out.close();		
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
			
			long end = System.currentTimeMillis();//����ʱ��
			System.out.println(Thread.currentThread().getName()+" ���߳�ִ��ʱ����" + (end-start)+"ms");
		}
		System.out.println("�����ļ�����");

	}
	
	
}
