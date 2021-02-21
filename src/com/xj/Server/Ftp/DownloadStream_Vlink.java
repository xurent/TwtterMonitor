package com.xj.Server.Ftp;

import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.VlinkService;

import net.sf.json.JSONException;

public class DownloadStream_Vlink implements Runnable{
	
	@SuppressWarnings("static-access")
	private VlinkService vlink_service=new FactoryService().getVlinkService();//��������ҵ��
	private Socket socket;
	private String tags;
	
	public DownloadStream_Vlink(Socket socket, String tags) {
		this.socket=socket;
		this.tags=tags;
	}

	@Override
	public void run() {
		OutputStream out = null;
		
		long start = System.currentTimeMillis(); //��ʼʱ��
		try {							
				out=socket.getOutputStream();
				//��ȡҪ���ص��ı�
				List<String>list_link=vlink_service.downloadVlink_ByTag(tags);
				if(list_link.size()>0){
								
//					//���͸��ͻ����ж���������
//					String send="{\"nSize\":\""+Integer.toString(list_text.size())+"\",\"msg\":\"Ҫ���ܵ��ı���Ϣ������\"}";
//					out.write(send.getBytes("UTF-8"));
//					out.flush();			
					//ѭ����������
					for(String s:list_link){
						out.write(s.getBytes("UTF-8"));
						out.flush();
					}
					
				}else{
					out.write("{\"state\":\"430\",\"msg\":\"�Ҳ�����������������\"}".getBytes("UTF-8"));
					out.flush();
				}			
				System.out.println("ftp�������ӽ���");
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
			
			long end = System.currentTimeMillis();//����ʱ��
			System.out.println(Thread.currentThread().getName()+" ���߳�ִ��ʱ����" + (end-start)+"ms");
		}
		System.out.println("�������ӽ���");

	}
}
