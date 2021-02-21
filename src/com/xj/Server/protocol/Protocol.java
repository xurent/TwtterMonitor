package com.xj.Server.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.xj.Server.Ftp.DownloadStream_Text;
import com.xj.Server.Ftp.DownloadStream_Vlink;
import com.xj.Server.Ftp.UploadStream_Text;
import com.xj.Server.control.StartMonitor;
import com.xj.Server.control.UserOnlineList;
import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.model.Build_record;
import com.xj.Server.model.Text_;
import com.xj.Server.model.Tuser_Account;
import com.xj.Server.model.Tusers;
import com.xj.Server.model.User;
import com.xj.Server.model.UserInfo;
import com.xj.Server.model.UserInfoList;
import com.xj.Server.model.UserMoniter;
import com.xj.Server.service.AccountService;
import com.xj.Server.service.Build_recordService;
import com.xj.Server.service.FactoryService;
import com.xj.Server.service.Picture_Service;
import com.xj.Server.service.TagService;
import com.xj.Server.service.Text_Service;
import com.xj.Server.service.Tuser_AccountService;
import com.xj.Server.service.TusersService;
import com.xj.Server.service.UserInfoService;
import com.xj.Server.service.UserService;
import com.xj.Server.service.VlinkService;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Protocol {
	
	private InputStream in=null;
	private OutputStream out=null;
	private static String Type=null; //�û�Ȩ�� //����Ա&��ͨ�û�
	
	private static Protocol protocal=new Protocol();
	@SuppressWarnings("static-access")
	private UserService userService= new FactoryService().getUserService();//�����û���
	@SuppressWarnings("static-access")
	private UserInfoService userinfoService=new FactoryService().getUserInfoService();//�����û��������ϱ�
	@SuppressWarnings({ "static-access" })
	private AccountService accountService=new FactoryService().getAccountService();//����������Ϣ��
	@SuppressWarnings("static-access")
	private TusersService tusersService=new FactoryService().getTusersService();//�������ر�
	@SuppressWarnings("static-access")
	private TagService tagService=new FactoryService().getTagService();//������ǩ��
	@SuppressWarnings("static-access")
	private Text_Service text_service=new FactoryService().getText_Service();//�����ı�ҵ��
	@SuppressWarnings("static-access")
	private Picture_Service picture_Service=new FactoryService().getPicture_Service();//����ͼƬ��
	@SuppressWarnings("static-access")
	private Tuser_AccountService tuser_accountService=new FactoryService().getTuser_AccountService();//��������+��Ϣ��
	@SuppressWarnings("static-access")
	private VlinkService vlinkservice= new FactoryService().getVlinkService();//������Ƶ����
	@SuppressWarnings("static-access")
	private Build_recordService build_recordDao=new FactoryService().getBuild_recordServiceImpl();
	//private 
	private Protocol(){}
	
	public static Protocol getProtocol(){
		return protocal;	
	}
	
	public int Protocolanalysis(JSONObject jsoncmd,Socket socket,String uid){
			
		try {
			Type=userService.get(uid).getType();
		} catch (LoginSQLException e2) {
			e2.printStackTrace();
		}//�û�Ȩ��
		//System.out.println(jsoncmd);
		try{
					
			in=socket.getInputStream();
			out=socket.getOutputStream();
			
			//����commandָ��
			String command=jsoncmd.getString("command");
			System.out.println("command:"+command);
			
			Method method=this.getClass().getDeclaredMethod(command,Socket.class,String.class,JSONObject.class);
			method.invoke(this, socket,uid,jsoncmd);
			
			return 1;	
		
	}catch(NoSuchMethodException e){
		
		try {
			out.write("{\"state\":930,\"msg\":\"notFoundProtocal!\"}".getBytes("UTF-8"));
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("û�и�Э��ָ��");
		return 0;
	}catch(JSONException e){
		try {
			out.write("{\"state\":931,\"msg\":\"JSONObject['Protocolanalysis-command'] not found!\"}".getBytes("UTF-8"));
			out.flush();	
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}catch(NullPointerException e){//commandΪ��
		System.out.println("commandΪ�ջ�û��ָ��");
		if(!socket.isClosed()){//�ر�����
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}catch(Exception e){
		
		e.printStackTrace();
		return 0;
	}
		return 1;
		
	
}
	
	/**ָ�SEEONLINE_USERS
	 * ����Ա�鿴�����û�
	 * @param socket
	 * @param uid
	 */
	private void SEEONLINE_USERS(Socket socket,String uid,JSONObject jsoncmd){
		if(!Type.equals("����Ա"))
			return ; //������ǹ���Ա�Ͳ�����
		try{
			out.write(1);
			out.flush();
					
		StringBuffer stringBuffer = new StringBuffer();
		Collection<UserInfoList> userList=UserOnlineList.getUserOnlineList().getUserInfoList();
		//System.out.println(userList);
		System.out.println(userList.size());
		if(!userList.isEmpty())
		{
			Iterator<UserInfoList> iter = userList.iterator() ;
			while(iter.hasNext()){
				UserInfoList str = iter.next() ;
				stringBuffer.append(str);
				stringBuffer.append(",");
			}
			
		}	
		if (stringBuffer.length() == 0) {
			// û��������
			out.write("{\"state\":920,\"msg\":\"notFound!\"}".getBytes("UTF-8"));
			out.flush();
		} else {
			// ��ִ�����б�
			out.write(stringBuffer.toString().getBytes("UTF-8"));
			out.flush();
		}

	}catch(Exception e){
		e.printStackTrace();
	}

}	
	/**ָ�EXIT_USER
	 * �˳���½
	 * @param socket
	 * @param uid
	 */
	private void EXIT_USER(Socket socket,String uid,JSONObject jsoncmd){
		UserOnlineList.getUserOnlineList().logout(uid);
		System.out.println("�û������˳�");
		if(socket!=null){
			try {		
				socket.close();
			} catch (IOException e) {
				System.out.println("�ͻ��������˳�ʱ�ر�socket�쳣��"+e.getMessage());
			}
		}
	}
	
	/**ָ�MODIFYPWD_USER
	 * �����޸�����
	 * @param socket
	 * @param uid
	 */
	private void MODIFYPWD_USER(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			String oldPasswod =jsoncmd.getString("oldPasswod");
			String newPassword =jsoncmd.getString("newPassword");
			//////////////////////////////////////////// 		 

			//�ͻ�������oldPasswod��newPassword��Ϊ�յĴ���
			if(userService.get(uid).getPasswd().equals(oldPasswod)){//��֤�������Ƿ���ȷ
				
				int flag=userService.modifypasswd(new User(uid,newPassword,"1"));
				if(flag>0){
					out.write("{\"state\":100,\"msg\":\"modify sucessful!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":130,\"msg\":\"modify failed!\"}".getBytes("UTF-8"));
					out.flush();
				}
			}else{
				out.write("���������".getBytes("UTF-8"));
				out.flush();
			}
						
		}catch(JSONException e){
			//json��ʽ����ֱ��kill�����socket
				try {
					out.write("{\"state\":931,\"msg\":\"JSONObject['oldPasswod'] orJSONObject['newPassword'] not found!\"}".getBytes("UTF-8"));
					out.flush();
					System.out.println(e.getMessage());
					System.out.println("���յ��޸�����json��ʽ����");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}			
			 
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("δ֪�쳣:"+e.toString());
		}
		
}

	/**ָ�MODIFY_USERINFO
	 * �޸��˺ŵĸ�������(Sex,Age,Region)
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void MODIFY_USERINFO(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				out=socket.getOutputStream();
				String Sex=jsoncmd.getString("sex");
				int Age=jsoncmd.getInt("age");
				String Region=jsoncmd.getString("region");
				
			int flag=userinfoService.updateUserById(new UserInfo(uid,Sex,Age,Region));
			if(flag<1){
				out.write("{\"state\":233,\"msg\":\"�޸ĸ�������ʧ��!\"}".getBytes("UTF-8"));
				out.flush();
			}else{
				out.write("{\"state\":230,\"msg\":\"�޸ĸ������ϳɹ�!\"}".getBytes("UTF-8"));
				out.flush();
			}
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['command'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**ָ�LOOKINFO_USERINFO
	 * �鿴��������
	 * @param socket
	 * @param uid
	 */
	private void LOOKINFO_USERINFO(Socket socket,String uid,JSONObject jsoncmd){
		UserInfo userinfo=null;
		try {
			userinfo=userinfoService.get(uid);
			out = socket.getOutputStream();
			if(userinfo!=null){
				out.write(userinfo.toString().getBytes("UTF-8"));
				out.flush();
			}else{
				out.write("{\"state\":230,\"msg\":\"��ȡ�������ϳ���!\"}".getBytes("UTF-8"));
				out.flush();
			}
		} catch(LoginSQLException e){
			System.out.println(e.getMessage());
		}	catch (Exception e) {
		
			// TODO: handle exception
		}
	}
	
	/**ָ�GETTWITTER_ACCOUNT
	 * ��ȡָ���û�ӵ�е������˺ź�����(emailflagΪtrue�򷵻ض�email�����룬false��ֻ���������˺ź�����)
	 * @param socket
	 * @param uid
	 */
	private void GETTWITTER_USER(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				out=socket.getOutputStream();		
				/*//��ͨ�˺Ų��ܲ���
				if(!Type.equals("��ͨ")){
					out.write("{\"state\":030,\"msg\":\"��Ȩ����!\"}".getBytes("UTF-8"));
					out.flush();
					return ;
				}*/
				String Type=jsoncmd.getString("type");
				//��������ݿ��л�ȡһ��δ��ʹ�ú������������˺�				
				List<Tusers> tusers=tusersService.getAccountByState_UseState_Rand(uid,"��Ч","δ����",Type,1);
				if(tusers.size()>0){
					Tusers tuser=tusers.get(0);//ȡ�����غ�
					String emailflag=jsoncmd.getString("emailflag"); //��ȡ�Ƿ�Ż�email��emailpasword
					System.out.println(emailflag);
					//����
					if(emailflag.equals("false")){
					out.write(tuser.getTuser().getBytes("UTF-8"));
					}		
					else if(emailflag.equals("true")){
					out.write(tuser.toString().getBytes("UTF-8"));
					}
					//�޸ĸ�������Ϣ���״̬Ϊ"������"
					int flag=accountService.modifyAccountUseState(tuser.getTuserID(), "������");
					if(flag<1){
						System.out.println("�޸�����״̬ʧ�ܣ�");
					}
				
				}else{
					try {
						out.write("{\"state\":330,\"msg\":\"û�ܻ�ȡ����ʹ�õ������˺�!\"}".getBytes("UTF-8"));
						out.flush();
					} catch (Exception e1) {				
						e1.printStackTrace();
					}
				}
			
		} catch (NullPointerException e){
			
			System.out.println("��ȡ�����˺�ʱ���Ϳ�ָ���쳣");
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['emailflag'] or JSONObject['type'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	/**ָ�RELEASETWTTER_STATE
	 * �˺�ʹ�������غ�����޸������˺�Ϊ�������С�
	 * @param socket
	 * @param uid
	 * 
	 */
	private  void RELEASETWTTER_STATE(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			String AccountNum=jsoncmd.getString("accountNum");
			if(AccountNum!=null){
			int flag =accountService.modifyAccountUseState(AccountNum, "δ����");
			if(flag<1)
			{
				out.write("{\"state\":301,\"msg\":\"�޸�״̬�����쳣ʧ�ܣ�\"}".getBytes("UTF-8"));
				out.flush();
			}else{
				out.write("{\"state\":300,\"msg\":\"�޸�״̬�ɹ���\"}".getBytes("UTF-8"));
				out.flush();
			}
			}else{
				out.write("{\"state\":330,\"msg\":\"�޸�״̬ʧ�ܣ�\"}".getBytes("UTF-8"));
				out.flush();
			}
		} catch (JSONException e) {		
			try {
				out.write("\"state\":931,\"msg\":\"JSONObject['accountNum'] not found!\"}".getBytes("UTF-8"));
				out.flush();
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**ָ�GETNUMSBYS_U_TWTTER
	 * ��ȡָ���û���ָ��״̬����������(type��Ϊ��ʱ���ɰ���Ʒ�˺ź���ͨ�˺Ų�ѯ)
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 * "{\"command\"\:\"GETNUMSBYS_U_TWTTER\",\"state\":\"������\",\"type\":\"��ͨ�˺�\"}"(����KEY�������)
	 */
	private  void GETNUMSBYS_U_TWTTER(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			 out=socket.getOutputStream();
			 String State=jsoncmd.getString("state");
			 String Type=jsoncmd.getString("type");
			 if(State!=null&&Type!=null){
				 Object nums=accountService.getAccountNumsBy_S_U_T(State, uid, Type);
				 if(nums!=null){
					 String sdata="{\"state\":\"330\",\"data\":\""+nums.toString()+"\"}";
					 out.write(sdata.getBytes());
					 out.flush();			
					 }else{
						 out.write("{\"state\":334,\"msg\":\"û���������������ݣ�\"}".getBytes("UTF-8"));
						 out.flush();
					 }		
			 }else{
				 out.write("{\"state\":335,\"msg\":\"���͵��������\"}".getBytes("UTF-8"));
				 out.flush();
			 }
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['state'] or JSONObject['type'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Ϊĳ���û���ӻ��޸�һ�����غ�
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void ADDUSERID_ACCOUNT(Socket socket,String uid,JSONObject jsoncmd){
		if(!Type.equals("��ͨ")){//���˺Ų��ǹ���Ա
			return ;
		}
		try {
				out=socket.getOutputStream();
				String UserId=jsoncmd.getString("userid");
				String AccountNum=jsoncmd.getString("accountnum");
				int flag=accountService.addUserIDforTwtter(UserId,AccountNum);
				if(flag<1){
					out.write("{\"state\":330,\"msg\":\"ִ��ʧ��!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":300,\"msg\":\"ִ�гɹ�!\"}".getBytes("UTF-8"));
					out.flush();
				}
		}catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['userid'] or JSONObject['accountnum'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ���߳������ϴ��ı�
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	@SuppressWarnings("finally")
	private void UPLOAD_TEXT_(Socket socket,String uid,JSONObject jsoncmd){
			
		try {
				out=socket.getOutputStream();
				//��ȡҪ�ϴ��ĸ���
				int nSize=jsoncmd.getInt("nSize");							
				//�����̴߳���
				new Thread(new UploadStream_Text(socket,nSize,uid),"UPLOAD_TEXT_").start();
										
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['nSize'] not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			System.out.println("UPLOAD_TEXT_ָ�����");
			return;
		}
		
	}
	
	/**
	 * ���߳������ı��ļ�
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DOWNLOAD_TEXT_(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			in=socket.getInputStream();
			//��ȡҪ���ص��ļ��ı�ǩ����
								
			String tags=jsoncmd.getString("tag");
			System.out.println(tags);									
//	
			//�����̴߳���
			new Thread(new DownloadStream_Text(socket,tags),"DOWNLOAD_TEXT_").start();
									
	} catch(JSONException e){
		try {
			out.write("{\"state\":931,\"msg\":\"JSONObject['command'] not found!\"}".getBytes("UTF-8"));
			out.flush();	
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}		
	}catch (Exception e) {
		// TODO: handle exception
		System.out.println("�ļ������׳����쳣��Ϣ"+e.getMessage());
	}
		
	}
	
	/**
	 * ���߳�������Ƶ����
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DOWNLOAD_VlINK(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			in=socket.getInputStream();
										
			String tags=jsoncmd.getString("tag");												
			//�����̴߳���
			new Thread(new DownloadStream_Vlink(socket,tags),"DOWNLOAD_VlINK").start();
									
	} catch(JSONException e){
		try {
			out.write("{\"state\":931,\"msg\":\"JSONObject['command'] not found!\"}".getBytes("UTF-8"));
			out.flush();	
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}		
	}catch (Exception e) {
		// TODO: handle exception
		System.out.println("��Ƶ���������׳����쳣��Ϣ"+e.getMessage());
	}
	}
	
	/**
	 * ͨ��VlINK_IDɾ��һ������
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETEBYID_VlINK(Socket socket,String uid,JSONObject jsoncmd){
		try {
			out=socket.getOutputStream();
		 	String VlINK_ID=jsoncmd.getString("vlink_id");
		 	System.out.println(VlINK_ID);
		 	int i=vlinkservice.deleteDownloadVlink(VlINK_ID);
		 	if(i>0){
		 		String send="{\"state\":\"500\",\"msg\":\"�ɹ�ɾ��"+i+"����¼��\"}";
		 		out.write(send.getBytes("UTF-8"));
		 		out.flush(); 		
		 	}else{
		 		out.write("{\"state\":\"530\",\"msg\":\"ɾ��ʧ�ܣ�\"}".getBytes("UTF-8"));
		 		out.flush();
		 	}
	} catch(JSONException e){
		try {
			out.write("{\"state\":931,\"msg\":\"JSONObject['vlink_id']  not found!\"}".getBytes("UTF-8"));
			out.flush();	
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}		
	}catch (Exception e) {
		// TODO: handle exception
	}
	
	}
	/**
	 * ͨ��Text_IDɾ��һ���ı�
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETEBYID_TEXT_(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				out=socket.getOutputStream();
			 	String Text_ID=jsoncmd.getString("text_id");
			 	System.out.println(Text_ID);
			 	int i=text_service.deleteDownloadText_(Text_ID);
			 	if(i>0){
			 		String send="{\"state\":\"500\",\"msg\":\"�ɹ�ɾ��"+i+"����¼��\"}";
			 		out.write(send.getBytes("UTF-8"));
			 		out.flush();
			 		
			 	}else{
			 		out.write("{\"state\":\"530\",\"msg\":\"ɾ��ʧ�ܣ�\"}".getBytes("UTF-8"));
			 		out.flush();
			 	}
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['text_id']  not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * ͨ��Text_ID����һ���ı�����
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void MODIFYTEXT_BYID(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			   String Text_Id=jsoncmd.getString("text_id");
			   String Tag=jsoncmd.getString("tag");
			   String Text_=jsoncmd.getString("text_");
			   
			   long id= new Long(Text_Id);//�ַ���ת��
			   //����һ���ı�����
			   int flag=text_service.modifyText_ByID(new Text_(id,Text_,Tag,uid,new Date()));
			   if(flag>0){
				   out.write("{\"state\":500,\"msg\":\"�޸ĳɹ�!\"}".getBytes("UTF-8"));
					out.flush();	
			   }else{
				   out.write("{\"state\":530,\"msg\":\"�޸�ʧ��!\"}".getBytes("UTF-8"));
					out.flush();	
			   }
			   
		}catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['text_id'] or JSONObject['tag'] or JSONObject['text_']  not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * ��ȡ����Tag��ǩ
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void GETALL_TAGS_TAG(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				out= socket.getOutputStream();
			  StringBuffer strBuff=new StringBuffer();
			  strBuff=tagService.getAlltag();
			  System.out.println(strBuff);
			  if(strBuff.length()==0){
				  out.write("{\"state\":920,\"msg\":\"Tag notFound!\"}".getBytes("UTF-8"));
				  out.flush();
			  }else{
				  out.write(strBuff.toString().getBytes("UTF-8"));
				  out.flush();
			  }
		} catch (Exception e) {
			// TODO: handle exception
		}finally{	
//			if(out!=null)
//				try {
//					//out.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	}

	}
	
	/**
	 * ͨ������state ��ȡָ���û����������ر��������Ϣ
	 * @param socket
	 * @param uid  ��½�û�
	 * @param jsoncmd
	 */
	private void GETALL_TWTTERANDINFO_BY_S(Socket socket,String uid,JSONObject jsoncmd){	
		try {
			  	String State=jsoncmd.getString("state");  //��ȡ����״̬
			  	List<Tuser_Account>t_list=tuser_accountService.FindAll_BY_UID_S(uid, State);
			  	if(t_list.size()>0){
			  		for(Tuser_Account t:t_list){
			  			out.write(t.toString().getBytes("UTF-8"));
						out.flush();
			  		}
			  	}else{
			  		out.write("{\"state\":730,\"msg\":\"û���ҵ����ϵ�����!\"}".getBytes("UTF-8"));
					out.flush();
			  	}
			  	
		} catch(JSONException e){
			
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['state']  not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
		
			// TODO: handle exception
		}
	}
	
	/**
	 * ͨ������ID�������޸����ر�+������Ϣ��
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void MODIFY_TWTTERANDINFO_BY_TID(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			{//Ӧ��
				out.write("{\"state\":\"701\",\"msg\":\"���Խ���������!\"}".getBytes("utf-8"));
				out.flush();
			}
			byte []bytes=new byte[4*1024];
			String Datas="";//��������
			int nLen=0;
			while(((nLen=in.read(bytes))!=-1)){				
				Datas+=new String(bytes,0,nLen,"utf-8");
				if(in.available()==0){
					break;
				}
			}
			JSONObject json = JSONObject.fromObject(Datas);// ����	
			
				String datas=json.getString("datas"); //������ȡҪ�޸ĵ�����
				String[] dataList=datas.split("&&&&");    //�и�����						
				int nSize=dataList.length;			
				//jsonѭ��ȡ��ÿ�����ݵ�ֵ	
				if(nSize>0){
				int i=0;int successnum=0,errornums=0;//�ɹ��޸ĵ�����
				while(i<nSize){
					 json = JSONObject.fromObject(dataList[i]);// ����		
					//����Ҫ�޸ĵĶ�������
					if(tuser_accountService.modify_BY_UID(new Tuser_Account(json.getString("AccUser"),json.getString("AccPass"),
							json.getString("Mail"),json.getString("MailPass"),"type","uid",
							json.getString("platform"),json.getString("language"),
							json.getString("state"),"",json.getString("phone"),json.getString("Main_page")))>0){
						successnum++;
					}else{
						errornums++;
					}
					i++;
				}
				String send="{\"state\":700,\"msg\":\"�����ɹ�! �ɹ��޸�"+successnum+"������,ʧ��"+errornums+"������\"}";
				out.write(send.getBytes("UTF-8"));
				out.flush();
				}else{
					out.write("{\"state\":031,\"msg\":\"����ʧ�ܣ�û���޸ĵ�����!\"}".getBytes("UTF-8"));
					out.flush();	
				}			
		} catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['datas']  not found!\"}".getBytes("UTF-8"));
				out.flush();	
				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ��½�û�����������������
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void ADDTWTTERDATAS_BY_UID(Socket socket,String uid,JSONObject jsoncmd){			
		try {			
			//in=socket.getInputStream();
			out=socket.getOutputStream();		
			String datas=jsoncmd.getString("datas");
			if(datas!=null&&datas.length()>0){
				JSONObject	json = JSONObject.fromObject(datas);// ����	
				//����ȡ��ÿ�����ؾ�������		
 
				if((tuser_accountService.insertTwtterData(new Tuser_Account(json.getString("AccUser"),json.getString("AccPass"),
						json.getString("Mail"),json.getString("MailPass"),
						json.getString("AccType"),uid,
						json.getString("platform"),json.getString("language"),
						json.getString("state"),"δ����",json.getString("phone"),
						json.getString("Main_page"))))==2){
					
					out.write("{\"state\":700,\"msg\":\"�ɹ���������!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":701,\"msg\":\"��������ʧ��!\"}".getBytes("UTF-8"));
					out.flush();
				}
			}else{
				out.write("{\"state\":730,\"msg\":\"�Ҳ���Ҫ���������!\"}".getBytes("UTF-8"));
				out.flush();
			}
			
		} catch(JSONException e){
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['datas']  not found!\"}".getBytes("UTF-8"));
				out.flush();
			}  catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}	catch (Exception e) {
		
			// TODO: handle exception
		}
		
	}
//	private void ADDTWTTERDATAS_BY_UID(Socket socket,String uid,JSONObject jsoncmd){		
//		try {	
//				in=socket.getInputStream();
//				out=socket.getOutputStream();
//				{//Ӧ��
//					out.write("{\"state\":\"701\",\"msg\":\"���Խ���������!\"}".getBytes("utf-8"));
//					out.flush();
//				}
//				byte []bytes=new byte[4*1024];
//				String Datas="";//��������
//				int nLen=0;
//				while(((nLen=in.read(bytes))!=-1)){				
//					Datas+=new String(bytes,0,nLen,"utf-8");
//					if(in.available()==0){
//						break;
//					}
//				}
//				JSONObject json = JSONObject.fromObject(Datas);// ����			
//				String datas=json.getString("datas");
//				String datas=jsoncmd.getString("datas");
//				if(datas!=null&&datas.length()>0){			
//					String []dataArr=datas.split("&&&&");
//					System.out.println("�ϴ����ص����ݣ�"+dataArr);
//					int nSize=dataArr.length;
//					if(nSize>0){
//						int successnum=0,errornums=0;//�ɹ����������
//						int i=0;
//						while(i<nSize){
//							json = JSONObject.fromObject(dataArr[i]);// ����	
							//����ȡ��ÿ�����ؾ�������						
//							if((tuser_accountService.insertTwtterData(new Tuser_Account(jsoncmd.getString("AccUser"),jsoncmd.getString("AccPass"),
//									jsoncmd.getString("Mail"),jsoncmd.getString("MailPass"),
//									jsoncmd.getString("AccType"),uid,
//									jsoncmd.getString("platform"),jsoncmd.getString("language"),
//									jsoncmd.getString("state"),"δʹ��")))==2){
////								successnum++;
//							}else{
////								errornums++;
//							}
////							i++;
//						}
//						String send="{\"state\":700,\"msg\":\"��������ɹ�! �ɹ�����"+successnum+"������,ʧ��"+errornums+"������\"}";
//						out.write(send.getBytes("UTF-8"));
//						out.flush();
//					}else{
//						out.write("{\"state\":730,\"msg\":\"�Ҳ���Ҫ���������!\"}".getBytes("UTF-8"));
//						out.flush();
//					}
//				}else{
//					out.write("{\"state\":730,\"msg\":\"�Ҳ���Ҫ���������!\"}".getBytes("UTF-8"));
//					out.flush();
////				}
//			
//		} catch(JSONException e){		
//			try {
//				out.write("{\"state\":931,\"msg\":\"JSONObject['datas']  not found!\"}".getBytes("UTF-8"));
//				out.flush();				
//			} catch (Exception e1) {	
//				e1.printStackTrace();
//			}		
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	/**
	 * ����ɾ�������˺ŵ�ӵ����
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETETWTTERUID_INFO_BY_TID(Socket socket,String uid,JSONObject jsoncmd){
		try {
				String TidList=jsoncmd.getString("tid"); //��ȡ�����˺�
				String []TidArr=TidList.split(",");
				if(TidArr.length>0){
					int successnum=0,errornum=0;
					for(String t:TidArr){
						if(accountService.deleteTwtterUIDBy_Tid(t)>0){
							successnum++;
						}else{
							errornum++;
						}
					}
					String send="{\"state\":700,\"msg\":\"�ɹ�ִ��ɾ��!,�ɹ�ɾ��"+successnum+"������,ʧ��"+errornum+"������\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":731,\"msg\":\"�Ҳ�����Ҫɾ���������˺�!\"}".getBytes("UTF-8"));
					out.flush();
				}
				//
		}catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['pid']  not found!\"}".getBytes("UTF-8"));
				out.flush();				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * ͨ��ͼƬid����ɾ��ͼƬ
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETE_PICTURE_BYPID(Socket socket,String uid,JSONObject jsoncmd){		
		try {
				String Pid=jsoncmd.getString("pid");
				int flag=picture_Service.deletePicture_ByPid(Pid);//ɾ��ͼƬ			
				if(flag>0){
					String send="{\"state\":\"700\",\"msg\":\"�ɹ�ɾ��"+flag+"��ͼƬ!\"}";
					out.write(send.getBytes("utf-8"));
					out.flush();
				}else{				
					out.write("{\"state\":\"730\",\"msg\":\"ɾ��ͼƬʧ��!\"}".getBytes("utf-8"));
					out.flush();
				}
				
		} catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['pid']  not found!\"}".getBytes("UTF-8"));
				out.flush();				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * �򿪼��
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void OPENMONITORING(Socket socket,String uid,JSONObject jsoncmd){	
		
		try {
				out=socket.getOutputStream();
			if(true==StartMonitor.getStartMonitor().isStart()){
				out.write("{\"state\":\"802\",\"msg\":\"��ش��ڴ�״̬!\"}".getBytes("utf-8"));
				out.flush();
			}else{
				StartMonitor.getStartMonitor().setStart(true);//�򿪼��
				out.write("{\"state\":\"800\",\"msg\":\"�ɹ���״̬!\"}".getBytes("utf-8"));
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * �رռ��
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void CLOSEMONITORING(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			if(true==StartMonitor.getStartMonitor().isStart()){
				StartMonitor.getStartMonitor().setStart(false);//�ر�
				out.write("{\"state\":\"800\",\"msg\":\"�ɹ��رռ��!\"}".getBytes("utf-8"));
				out.flush();
			}else{
				
				out.write("{\"state\":\"802\",\"msg\":\"����Ѿ��ǹر�״̬!\"}".getBytes("utf-8"));
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ��Ӽ��Ŀ���б�
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void ADDMONITORING(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				in=socket.getInputStream();
				out=socket.getOutputStream();		  
			  {
				out.write("{\"state\":\"850\",\"msg\":\"���Խ��ܼ���б���!\"}".getBytes("utf-8"));
				out.flush();  
			  }
			  // �ȴ��ͻ�����Ϣ
			  List<UserMoniter> infoList=new ArrayList<>();
				byte[] bytes = new byte[4*1024];
				int len = in.read(bytes);
				String json_str = new String(bytes, 0, len,"utf-8");
				
				System.out.println("���յ�������:"+json_str);
				JSONObject json=JSONObject.fromObject(json_str);
				JSONArray Date=json.getJSONArray("datas");
				
				System.out.println("datas:"+Date);
				
				if(Date!=null&&Date.size()>0){
					for(int i=0;i<Date.size();i++) {
						UserMoniter s= (UserMoniter) JSONObject.toBean(Date.getJSONObject(i), UserMoniter.class);
						infoList.add(s);
					}		
					
					
					System.out.println("Dateѭ������");
				StartMonitor.getStartMonitor().setList_Time(infoList, 2);
				out.write("{\"state\":\"800\",\"msg\":\"�ɹ���Ӽ������!\"}".getBytes("utf-8"));
				out.flush(); 
				System.out.println("out.write �ɹ���Ӽ������!\"}");
				
				}else{		
					out.write("{\"state\":\"830\",\"msg\":\"��Ӽ������ʧ��!\"}".getBytes("utf-8"));
					out.flush(); 
				}			
				
		} catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['datas']  not found!\"}".getBytes("UTF-8"));
				out.flush();				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	/**
	 * �Ƴ����Ŀ��
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void REMOVEMONITORING(Socket socket,String uid,JSONArray jsoncmd){
		
		try {
				out=socket.getOutputStream();
				/*String names=jsoncmd.getString("names");
				String[] str=names.split(",");*/		
				if(jsoncmd.size()>0){
					for(int i=0;i<jsoncmd.size();i++) {
						JSONObject str=jsoncmd.getJSONObject(i);
						UserMoniter name=(UserMoniter) JSONObject.toBean(str, UserMoniter.class);
						StartMonitor.getStartMonitor().removeMonitor(name);//ɾ����ض���
					}

					/*for(UserMoniter name:str){
						StartMonitor.getStartMonitor().removeMonitor(name);//ɾ����ض���
					}*/
					out.write("{\"state\":800,\"msg\":\"�Ƴ�����ɹ�!\"}".getBytes("UTF-8"));
					out.flush();	
				}else{
					out.write("{\"state\":830,\"msg\":\"�Ƴ�����ʧ��!\"}".getBytes("UTF-8"));
					out.flush();
				}
			
		} catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['names']  not found!\"}".getBytes("UTF-8"));
				out.flush();				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * �ϴ�һ����¥��¼
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	public void ADD_BUILD_RECORD(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			  in=socket.getInputStream();
			  out=socket.getOutputStream();
		//	  String data_str=jsoncmd.getString("data"); //��������
			  
			  if(jsoncmd!=null&&!jsoncmd.equals("")){
				 
				  Build_record bc=new Build_record();  //����Build_record��������
					bc.setAccount_Num(jsoncmd.getString("account_num"));
					bc.setFloor(jsoncmd.getInt("floor"));
					bc.setNext_floor(jsoncmd.getInt("next_floor"));
					bc.setInterlinking(jsoncmd.getString("interlinking"));
					bc.setContent(jsoncmd.getString("content"));
					String screenshot=jsoncmd.getString("screenshot"); //��ͼ��Ϊ��
					//if(screenshot!=null&&screenshot.length()>0)
					bc.setScreenshot(screenshot);
					
					bc.setUserID(uid);
					bc.setSend_time(new Date());
					if(build_recordDao.addBuild_record(bc)>0){ //�ɹ�
						out.write("{\"state\":800,\"msg\":\"��¼�ɹ�\"}".getBytes("UTF-8"));
						out.flush();		
					}else{
						out.write("{\"state\":830,\"msg\":\"��¼ʧ��!\"}".getBytes("UTF-8"));
						out.flush();		
					}
			  }else{
				  out.write("{\"state\":820,\"msg\":\"������!\"}".getBytes("UTF-8"));
					out.flush();
			  }
			  			  			  
		} catch(JSONException e){		
			try {
				out.write("{\"state\":931,\"msg\":\"JSONObject['names']  not found!\"}".getBytes("UTF-8"));
				out.flush();				
			} catch (Exception e1) {	
				e1.printStackTrace();
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
