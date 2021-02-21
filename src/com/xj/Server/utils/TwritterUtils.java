package com.xj.Server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import com.xj.Server.model.Link_Time;
import com.xj.Server.model.tProxy;

public class TwritterUtils {

	private String path=null;
	private Document doc=null;
	private URL url=null;
	private String tuiwen;
	private String foucusing;
	private String foucuser;
	private String like;
	private boolean Frozen=false;
	private List<Link_Time>links=new ArrayList<>();//����+ʱ��
	public  TwritterUtils(String path)  {	
		this.path=path;	
		try {
			getAll();
		} catch(ConnectException e){
			System.out.println("OVPNû�п������ߵ�ǰ���粻���ã�info:"+e.getMessage());
		}catch (IOException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ����ҳ��
	 * @return
	 * @throws IOException
	 */
	private void getAll() throws IOException {
		url=new URL(path);
		Proxy proxy=tProxy.getProxy();
		HttpsURLConnection conn;
		if(proxy==null){
			conn=(HttpsURLConnection) url.openConnection();
		}else{
			conn=(HttpsURLConnection) url.openConnection(proxy);
		} 
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
		conn.setRequestProperty("Connection","keep-alive");
		conn.setRequestProperty("Host", "twitter.com");
		conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setConnectTimeout(5000);
		int code=conn.getResponseCode();
		System.out.println("Code:"+code);
		if(code==200) {
			System.out.println("���ʳɹ�!");
			InputStream is=conn.getInputStream();
			try {
				String result=StreamTool.Read(is);
				doc = Jsoup.parse(result);
				if(doc.select("div[id=page-container]").select("h1").text().contains("�˺��Ѷ���")) {
					Frozen=true;
					return;
				}
				//������
				 tuiwen=doc.select("div[class=ProfileNav]").select("li[class=ProfileNav-item ProfileNav-item--tweets is-active]").select("span[class=ProfileNav-value]").attr("data-count");
				//���ڹ�ע
				foucusing=doc.select("div[class=ProfileNav]").select("li[class=ProfileNav-item ProfileNav-item--following]").select("span[class=ProfileNav-value]").attr("data-count");			
				//��ע��
				 foucuser=doc.select("div[class=ProfileNav]").select("li[class=ProfileNav-item ProfileNav-item--followers]").select("span[class=ProfileNav-value]").attr("data-count");
				//ϲ��
				 like=doc.select("div[class=ProfileNav]").select("li[class=ProfileNav-item ProfileNav-item--favorites]").select("span[class=ProfileNav-value]").attr("data-count");
				//��������
				Elements link=doc.select("div[class=stream]").select("li[class=js-stream-item stream-item stream-item]");
				for(int i=0;i<link.size();i++) {
					String Link="https://twitter.com"+link.get(i).select("div[class^=tweet]").attr("data-permalink-path");
					String time=doc.select("small[class=time]").select("span[class=_timestamp js-short-timestamp]").get(i).attr("data-time-ms");
					if(doc.select("div[class=content]").text().contains("�ö�����")) {
						time=doc.select("small[class=time]").select("span[class=_timestamp js-short-timestamp]").get(i+1).attr("data-time-ms");
					}
					time=stampToDate(time);
					Link_Time lt= new Link_Time(Link, time);
					links.add(lt);	
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
		}else if(code==404) {
			Frozen=true;
		}
				
		
	}
	
	/**
	 * �ж��˺��Ƿ񱻷�
	 * @return
	 */
	
	
public boolean isFrozen() {
		return Frozen;
	}

/**
 * ��ȡ��������
 * @return
 */
	public int getTuiwen() {
		if(tuiwen==null||tuiwen.equals("")) {
			return 0;
		}
		return Integer.parseInt(tuiwen);
	}

	/**
	 * ��ȡ���ڹ�ע����
	 * @return
	 */
	public int getFoucusing() {
		if(foucusing==null||tuiwen.equals("")) {
			return 0;
		}
		return Integer.parseInt(foucusing);
	}

/**
 * ��ȡ��ע������
 * @return
 */
	public int getFoucuser() {
		if(foucuser==null||foucuser.equals("")) {
			return 0;
		}
		return Integer.parseInt(foucuser);
	}
/**
 * ϲ������
 * @return
 */
	public int getLike() {
		if(like==null) {
			return 0;
		}
		return Integer.parseInt(like);
	}


	
	/**
	 * ��ȡ���Ӻ�ʱ��
	 * @return
	 */

	public List<Link_Time> getLinks() {
	return links;
}

	/**
	 * ʱ���ת����ʱ��
	 * @param s
	 * @return
	 */
	public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	
}
