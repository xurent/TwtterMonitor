package com.xj.Server.model;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class tProxy {
	private static String address=null;
	private static int port;
	private static Proxy proxy;
	public tProxy() {
		
	}
	
	public static Proxy getProxy() {
		if(address==null){
			return null;
		}
		SocketAddress sa = new InetSocketAddress(address, port);
		proxy = new Proxy(Proxy.Type.HTTP, sa);
		return proxy;
	}
	
	public static void setProxy(String addr,int ports) {
		address=addr;
		port=ports;
	}
	
	public String getAdddress() {
		if(address==null){
			return null;
		}
		return address;
	}
	public void setAdddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	

}
