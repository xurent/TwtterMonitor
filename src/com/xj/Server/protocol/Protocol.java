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
	private static String Type=null; //用户权限 //管理员&普通用户
	
	private static Protocol protocal=new Protocol();
	@SuppressWarnings("static-access")
	private UserService userService= new FactoryService().getUserService();//操作用户表
	@SuppressWarnings("static-access")
	private UserInfoService userinfoService=new FactoryService().getUserInfoService();//操作用户个人资料表
	@SuppressWarnings({ "static-access" })
	private AccountService accountService=new FactoryService().getAccountService();//操作推特信息表
	@SuppressWarnings("static-access")
	private TusersService tusersService=new FactoryService().getTusersService();//操作推特表
	@SuppressWarnings("static-access")
	private TagService tagService=new FactoryService().getTagService();//操作标签表
	@SuppressWarnings("static-access")
	private Text_Service text_service=new FactoryService().getText_Service();//操作文本业务
	@SuppressWarnings("static-access")
	private Picture_Service picture_Service=new FactoryService().getPicture_Service();//操作图片表
	@SuppressWarnings("static-access")
	private Tuser_AccountService tuser_accountService=new FactoryService().getTuser_AccountService();//操作推特+信息表
	@SuppressWarnings("static-access")
	private VlinkService vlinkservice= new FactoryService().getVlinkService();//操作视频链接
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
		}//用户权限
		//System.out.println(jsoncmd);
		try{
					
			in=socket.getInputStream();
			out=socket.getOutputStream();
			
			//解析command指令
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
		System.out.println("没有该协议指令");
		return 0;
	}catch(JSONException e){
		try {
			out.write("{\"state\":931,\"msg\":\"JSONObject['Protocolanalysis-command'] not found!\"}".getBytes("UTF-8"));
			out.flush();	
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}catch(NullPointerException e){//command为空
		System.out.println("command为空或没有指令");
		if(!socket.isClosed()){//关闭连接
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
	
	/**指令：SEEONLINE_USERS
	 * 管理员查看在线用户
	 * @param socket
	 * @param uid
	 */
	private void SEEONLINE_USERS(Socket socket,String uid,JSONObject jsoncmd){
		if(!Type.equals("管理员"))
			return ; //如果不是管理员就不处理
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
			// 没有人在线
			out.write("{\"state\":920,\"msg\":\"notFound!\"}".getBytes("UTF-8"));
			out.flush();
		} else {
			// 回执在线列表
			out.write(stringBuffer.toString().getBytes("UTF-8"));
			out.flush();
		}

	}catch(Exception e){
		e.printStackTrace();
	}

}	
	/**指令：EXIT_USER
	 * 退出登陆
	 * @param socket
	 * @param uid
	 */
	private void EXIT_USER(Socket socket,String uid,JSONObject jsoncmd){
		UserOnlineList.getUserOnlineList().logout(uid);
		System.out.println("用户主动退出");
		if(socket!=null){
			try {		
				socket.close();
			} catch (IOException e) {
				System.out.println("客户端主动退出时关闭socket异常："+e.getMessage());
			}
		}
	}
	
	/**指令：MODIFYPWD_USER
	 * 个人修改密码
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

			//客户端来做oldPasswod和newPassword不为空的处理
			if(userService.get(uid).getPasswd().equals(oldPasswod)){//验证旧密码是否正确
				
				int flag=userService.modifypasswd(new User(uid,newPassword,"1"));
				if(flag>0){
					out.write("{\"state\":100,\"msg\":\"modify sucessful!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":130,\"msg\":\"modify failed!\"}".getBytes("UTF-8"));
					out.flush();
				}
			}else{
				out.write("旧密码错误！".getBytes("UTF-8"));
				out.flush();
			}
						
		}catch(JSONException e){
			//json格式出错，直接kill掉这个socket
				try {
					out.write("{\"state\":931,\"msg\":\"JSONObject['oldPasswod'] orJSONObject['newPassword'] not found!\"}".getBytes("UTF-8"));
					out.flush();
					System.out.println(e.getMessage());
					System.out.println("接收的修改密码json格式错误");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}			
			 
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("未知异常:"+e.toString());
		}
		
}

	/**指令：MODIFY_USERINFO
	 * 修改账号的个人资料(Sex,Age,Region)
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
				out.write("{\"state\":233,\"msg\":\"修改个人资料失败!\"}".getBytes("UTF-8"));
				out.flush();
			}else{
				out.write("{\"state\":230,\"msg\":\"修改个人资料成功!\"}".getBytes("UTF-8"));
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
	
	/**指令：LOOKINFO_USERINFO
	 * 查看个人资料
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
				out.write("{\"state\":230,\"msg\":\"获取个人资料出错!\"}".getBytes("UTF-8"));
				out.flush();
			}
		} catch(LoginSQLException e){
			System.out.println(e.getMessage());
		}	catch (Exception e) {
		
			// TODO: handle exception
		}
	}
	
	/**指令：GETTWITTER_ACCOUNT
	 * 获取指定用户拥有的推特账号和密码(emailflag为true则返回多email和密码，false则只返回推特账号和密码)
	 * @param socket
	 * @param uid
	 */
	private void GETTWITTER_USER(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				out=socket.getOutputStream();		
				/*//普通账号才能操作
				if(!Type.equals("普通")){
					out.write("{\"state\":030,\"msg\":\"无权操作!\"}".getBytes("UTF-8"));
					out.flush();
					return ;
				}*/
				String Type=jsoncmd.getString("type");
				//随机从数据库中获取一条未被使用和正常的推特账号				
				List<Tusers> tusers=tusersService.getAccountByState_UseState_Rand(uid,"有效","未养护",Type,1);
				if(tusers.size()>0){
					Tusers tuser=tusers.get(0);//取出推特号
					String emailflag=jsoncmd.getString("emailflag"); //获取是否放回email和emailpasword
					System.out.println(emailflag);
					//发送
					if(emailflag.equals("false")){
					out.write(tuser.getTuser().getBytes("UTF-8"));
					}		
					else if(emailflag.equals("true")){
					out.write(tuser.toString().getBytes("UTF-8"));
					}
					//修改改推特信息表的状态为"已养护"
					int flag=accountService.modifyAccountUseState(tuser.getTuserID(), "已养护");
					if(flag<1){
						System.out.println("修改推特状态失败！");
					}
				
				}else{
					try {
						out.write("{\"state\":330,\"msg\":\"没能获取到能使用的推特账号!\"}".getBytes("UTF-8"));
						out.flush();
					} catch (Exception e1) {				
						e1.printStackTrace();
					}
				}
			
		} catch (NullPointerException e){
			
			System.out.println("获取推特账号时发送空指针异常");
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
	
	/**指令：RELEASETWTTER_STATE
	 * 账号使用完推特后必须修改推特账号为‘休眠中’
	 * @param socket
	 * @param uid
	 * 
	 */
	private  void RELEASETWTTER_STATE(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			String AccountNum=jsoncmd.getString("accountNum");
			if(AccountNum!=null){
			int flag =accountService.modifyAccountUseState(AccountNum, "未养护");
			if(flag<1)
			{
				out.write("{\"state\":301,\"msg\":\"修改状态出现异常失败！\"}".getBytes("UTF-8"));
				out.flush();
			}else{
				out.write("{\"state\":300,\"msg\":\"修改状态成功！\"}".getBytes("UTF-8"));
				out.flush();
			}
			}else{
				out.write("{\"state\":330,\"msg\":\"修改状态失败！\"}".getBytes("UTF-8"));
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
	
	/**指令：GETNUMSBYS_U_TWTTER
	 * 获取指定用户，指定状态的推特数量(type不为空时，可按精品账号和普通账号查询)
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 * "{\"command\"\:\"GETNUMSBYS_U_TWTTER\",\"state\":\"休眠中\",\"type\":\"普通账号\"}"(三个KEY必须带上)
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
						 out.write("{\"state\":334,\"msg\":\"没有满足条件的数据！\"}".getBytes("UTF-8"));
						 out.flush();
					 }		
			 }else{
				 out.write("{\"state\":335,\"msg\":\"发送的请求错误！\"}".getBytes("UTF-8"));
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
	 * 为某个用户添加或修改一个推特号
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void ADDUSERID_ACCOUNT(Socket socket,String uid,JSONObject jsoncmd){
		if(!Type.equals("普通")){//该账号不是管理员
			return ;
		}
		try {
				out=socket.getOutputStream();
				String UserId=jsoncmd.getString("userid");
				String AccountNum=jsoncmd.getString("accountnum");
				int flag=accountService.addUserIDforTwtter(UserId,AccountNum);
				if(flag<1){
					out.write("{\"state\":330,\"msg\":\"执行失败!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":300,\"msg\":\"执行成功!\"}".getBytes("UTF-8"));
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
	 * 多线程批量上传文本
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	@SuppressWarnings("finally")
	private void UPLOAD_TEXT_(Socket socket,String uid,JSONObject jsoncmd){
			
		try {
				out=socket.getOutputStream();
				//获取要上传的个数
				int nSize=jsoncmd.getInt("nSize");							
				//开启线程传输
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
			System.out.println("UPLOAD_TEXT_指令完成");
			return;
		}
		
	}
	
	/**
	 * 多线程下载文本文件
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DOWNLOAD_TEXT_(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			in=socket.getInputStream();
			//获取要下载的文件的标签类型
								
			String tags=jsoncmd.getString("tag");
			System.out.println(tags);									
//	
			//开启线程传输
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
		System.out.println("文件下载抛出的异常信息"+e.getMessage());
	}
		
	}
	
	/**
	 * 多线程下载视频链接
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DOWNLOAD_VlINK(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			in=socket.getInputStream();
										
			String tags=jsoncmd.getString("tag");												
			//开启线程传输
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
		System.out.println("视频链接下载抛出的异常信息"+e.getMessage());
	}
	}
	
	/**
	 * 通过VlINK_ID删除一条链接
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
		 		String send="{\"state\":\"500\",\"msg\":\"成功删除"+i+"条记录！\"}";
		 		out.write(send.getBytes("UTF-8"));
		 		out.flush(); 		
		 	}else{
		 		out.write("{\"state\":\"530\",\"msg\":\"删除失败！\"}".getBytes("UTF-8"));
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
	 * 通过Text_ID删除一条文本
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
			 		String send="{\"state\":\"500\",\"msg\":\"成功删除"+i+"条记录！\"}";
			 		out.write(send.getBytes("UTF-8"));
			 		out.flush();
			 		
			 	}else{
			 		out.write("{\"state\":\"530\",\"msg\":\"删除失败！\"}".getBytes("UTF-8"));
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
	 * 通过Text_ID更新一条文本数据
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void MODIFYTEXT_BYID(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			   String Text_Id=jsoncmd.getString("text_id");
			   String Tag=jsoncmd.getString("tag");
			   String Text_=jsoncmd.getString("text_");
			   
			   long id= new Long(Text_Id);//字符串转换
			   //更新一条文本数据
			   int flag=text_service.modifyText_ByID(new Text_(id,Text_,Tag,uid,new Date()));
			   if(flag>0){
				   out.write("{\"state\":500,\"msg\":\"修改成功!\"}".getBytes("UTF-8"));
					out.flush();	
			   }else{
				   out.write("{\"state\":530,\"msg\":\"修改失败!\"}".getBytes("UTF-8"));
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
	 * 获取所有Tag标签
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
	 * 通过推特state 获取指定用户的所有推特表和推特信息
	 * @param socket
	 * @param uid  登陆用户
	 * @param jsoncmd
	 */
	private void GETALL_TWTTERANDINFO_BY_S(Socket socket,String uid,JSONObject jsoncmd){	
		try {
			  	String State=jsoncmd.getString("state");  //获取推特状态
			  	List<Tuser_Account>t_list=tuser_accountService.FindAll_BY_UID_S(uid, State);
			  	if(t_list.size()>0){
			  		for(Tuser_Account t:t_list){
			  			out.write(t.toString().getBytes("UTF-8"));
						out.flush();
			  		}
			  	}else{
			  		out.write("{\"state\":730,\"msg\":\"没有找到符合的数据!\"}".getBytes("UTF-8"));
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
	 * 通过推特ID，批量修改推特表+推特信息表
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void MODIFY_TWTTERANDINFO_BY_TID(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			{//应答
				out.write("{\"state\":\"701\",\"msg\":\"可以接收数据了!\"}".getBytes("utf-8"));
				out.flush();
			}
			byte []bytes=new byte[4*1024];
			String Datas="";//接收数据
			int nLen=0;
			while(((nLen=in.read(bytes))!=-1)){				
				Datas+=new String(bytes,0,nLen,"utf-8");
				if(in.available()==0){
					break;
				}
			}
			JSONObject json = JSONObject.fromObject(Datas);// 解析	
			
				String datas=json.getString("datas"); //批量获取要修改的数据
				String[] dataList=datas.split("&&&&");    //切割数据						
				int nSize=dataList.length;			
				//json循环取出每条数据的值	
				if(nSize>0){
				int i=0;int successnum=0,errornums=0;//成功修改的数量
				while(i<nSize){
					 json = JSONObject.fromObject(dataList[i]);// 解析		
					//构建要修改的对象数据
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
				String send="{\"state\":700,\"msg\":\"操作成功! 成功修改"+successnum+"条数据,失败"+errornums+"条数据\"}";
				out.write(send.getBytes("UTF-8"));
				out.flush();
				}else{
					out.write("{\"state\":031,\"msg\":\"操作失败，没有修改的数据!\"}".getBytes("UTF-8"));
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
	 * 登陆用户批量导入推特数据
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
				JSONObject	json = JSONObject.fromObject(datas);// 解析	
				//批量取出每条推特具体数据		
 
				if((tuser_accountService.insertTwtterData(new Tuser_Account(json.getString("AccUser"),json.getString("AccPass"),
						json.getString("Mail"),json.getString("MailPass"),
						json.getString("AccType"),uid,
						json.getString("platform"),json.getString("language"),
						json.getString("state"),"未养护",json.getString("phone"),
						json.getString("Main_page"))))==2){
					
					out.write("{\"state\":700,\"msg\":\"成功导入数据!\"}".getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":701,\"msg\":\"导入数据失败!\"}".getBytes("UTF-8"));
					out.flush();
				}
			}else{
				out.write("{\"state\":730,\"msg\":\"找不到要导入的数据!\"}".getBytes("UTF-8"));
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
//				{//应答
//					out.write("{\"state\":\"701\",\"msg\":\"可以接收数据了!\"}".getBytes("utf-8"));
//					out.flush();
//				}
//				byte []bytes=new byte[4*1024];
//				String Datas="";//接收数据
//				int nLen=0;
//				while(((nLen=in.read(bytes))!=-1)){				
//					Datas+=new String(bytes,0,nLen,"utf-8");
//					if(in.available()==0){
//						break;
//					}
//				}
//				JSONObject json = JSONObject.fromObject(Datas);// 解析			
//				String datas=json.getString("datas");
//				String datas=jsoncmd.getString("datas");
//				if(datas!=null&&datas.length()>0){			
//					String []dataArr=datas.split("&&&&");
//					System.out.println("上传推特的数据："+dataArr);
//					int nSize=dataArr.length;
//					if(nSize>0){
//						int successnum=0,errornums=0;//成功导入的数量
//						int i=0;
//						while(i<nSize){
//							json = JSONObject.fromObject(dataArr[i]);// 解析	
							//批量取出每条推特具体数据						
//							if((tuser_accountService.insertTwtterData(new Tuser_Account(jsoncmd.getString("AccUser"),jsoncmd.getString("AccPass"),
//									jsoncmd.getString("Mail"),jsoncmd.getString("MailPass"),
//									jsoncmd.getString("AccType"),uid,
//									jsoncmd.getString("platform"),jsoncmd.getString("language"),
//									jsoncmd.getString("state"),"未使用")))==2){
////								successnum++;
//							}else{
////								errornums++;
//							}
////							i++;
//						}
//						String send="{\"state\":700,\"msg\":\"导入操作成功! 成功导入"+successnum+"条数据,失败"+errornums+"条数据\"}";
//						out.write(send.getBytes("UTF-8"));
//						out.flush();
//					}else{
//						out.write("{\"state\":730,\"msg\":\"找不到要导入的数据!\"}".getBytes("UTF-8"));
//						out.flush();
//					}
//				}else{
//					out.write("{\"state\":730,\"msg\":\"找不到要导入的数据!\"}".getBytes("UTF-8"));
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
	 * 批量删除推特账号的拥有者
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETETWTTERUID_INFO_BY_TID(Socket socket,String uid,JSONObject jsoncmd){
		try {
				String TidList=jsoncmd.getString("tid"); //获取推特账号
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
					String send="{\"state\":700,\"msg\":\"成功执行删除!,成功删除"+successnum+"条数据,失败"+errornum+"条数据\"}";
					out.write(send.getBytes("UTF-8"));
					out.flush();
				}else{
					out.write("{\"state\":731,\"msg\":\"找不到你要删除的推特账号!\"}".getBytes("UTF-8"));
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
	 * 通过图片id批量删除图片
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void DELETE_PICTURE_BYPID(Socket socket,String uid,JSONObject jsoncmd){		
		try {
				String Pid=jsoncmd.getString("pid");
				int flag=picture_Service.deletePicture_ByPid(Pid);//删除图片			
				if(flag>0){
					String send="{\"state\":\"700\",\"msg\":\"成功删除"+flag+"张图片!\"}";
					out.write(send.getBytes("utf-8"));
					out.flush();
				}else{				
					out.write("{\"state\":\"730\",\"msg\":\"删除图片失败!\"}".getBytes("utf-8"));
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
	 * 打开监控
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void OPENMONITORING(Socket socket,String uid,JSONObject jsoncmd){	
		
		try {
				out=socket.getOutputStream();
			if(true==StartMonitor.getStartMonitor().isStart()){
				out.write("{\"state\":\"802\",\"msg\":\"监控处于打开状态!\"}".getBytes("utf-8"));
				out.flush();
			}else{
				StartMonitor.getStartMonitor().setStart(true);//打开监控
				out.write("{\"state\":\"800\",\"msg\":\"成功打开状态!\"}".getBytes("utf-8"));
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * 关闭监控
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void CLOSEMONITORING(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			out=socket.getOutputStream();
			if(true==StartMonitor.getStartMonitor().isStart()){
				StartMonitor.getStartMonitor().setStart(false);//关闭
				out.write("{\"state\":\"800\",\"msg\":\"成功关闭监控!\"}".getBytes("utf-8"));
				out.flush();
			}else{
				
				out.write("{\"state\":\"802\",\"msg\":\"监控已经是关闭状态!\"}".getBytes("utf-8"));
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 添加监控目标列表
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	private void ADDMONITORING(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
				in=socket.getInputStream();
				out=socket.getOutputStream();		  
			  {
				out.write("{\"state\":\"850\",\"msg\":\"可以接受监控列表了!\"}".getBytes("utf-8"));
				out.flush();  
			  }
			  // 等待客户端信息
			  List<UserMoniter> infoList=new ArrayList<>();
				byte[] bytes = new byte[4*1024];
				int len = in.read(bytes);
				String json_str = new String(bytes, 0, len,"utf-8");
				
				System.out.println("接收到的数据:"+json_str);
				JSONObject json=JSONObject.fromObject(json_str);
				JSONArray Date=json.getJSONArray("datas");
				
				System.out.println("datas:"+Date);
				
				if(Date!=null&&Date.size()>0){
					for(int i=0;i<Date.size();i++) {
						UserMoniter s= (UserMoniter) JSONObject.toBean(Date.getJSONObject(i), UserMoniter.class);
						infoList.add(s);
					}		
					
					
					System.out.println("Date循环结束");
				StartMonitor.getStartMonitor().setList_Time(infoList, 2);
				out.write("{\"state\":\"800\",\"msg\":\"成功添加监控名单!\"}".getBytes("utf-8"));
				out.flush(); 
				System.out.println("out.write 成功添加监控名单!\"}");
				
				}else{		
					out.write("{\"state\":\"830\",\"msg\":\"添加监控名单失败!\"}".getBytes("utf-8"));
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
	 * 移除监控目标
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
						StartMonitor.getStartMonitor().removeMonitor(name);//删除监控对象
					}

					/*for(UserMoniter name:str){
						StartMonitor.getStartMonitor().removeMonitor(name);//删除监控对象
					}*/
					out.write("{\"state\":800,\"msg\":\"移除对象成功!\"}".getBytes("UTF-8"));
					out.flush();	
				}else{
					out.write("{\"state\":830,\"msg\":\"移除对象失败!\"}".getBytes("UTF-8"));
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
	 * 上传一条盖楼记录
	 * @param socket
	 * @param uid
	 * @param jsoncmd
	 */
	public void ADD_BUILD_RECORD(Socket socket,String uid,JSONObject jsoncmd){
		
		try {
			  in=socket.getInputStream();
			  out=socket.getOutputStream();
		//	  String data_str=jsoncmd.getString("data"); //接受数据
			  
			  if(jsoncmd!=null&&!jsoncmd.equals("")){
				 
				  Build_record bc=new Build_record();  //构建Build_record对象数据
					bc.setAccount_Num(jsoncmd.getString("account_num"));
					bc.setFloor(jsoncmd.getInt("floor"));
					bc.setNext_floor(jsoncmd.getInt("next_floor"));
					bc.setInterlinking(jsoncmd.getString("interlinking"));
					bc.setContent(jsoncmd.getString("content"));
					String screenshot=jsoncmd.getString("screenshot"); //截图可为空
					//if(screenshot!=null&&screenshot.length()>0)
					bc.setScreenshot(screenshot);
					
					bc.setUserID(uid);
					bc.setSend_time(new Date());
					if(build_recordDao.addBuild_record(bc)>0){ //成功
						out.write("{\"state\":800,\"msg\":\"记录成功\"}".getBytes("UTF-8"));
						out.flush();		
					}else{
						out.write("{\"state\":830,\"msg\":\"记录失败!\"}".getBytes("UTF-8"));
						out.flush();		
					}
			  }else{
				  out.write("{\"state\":820,\"msg\":\"空数据!\"}".getBytes("UTF-8"));
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
