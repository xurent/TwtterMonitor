package com.xj.Server.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {
	 public static String Read(InputStream is)throws Exception{
	        ByteArrayOutputStream bos=new ByteArrayOutputStream();
	        byte []Buffer=new byte[1024];
	        int len=-1;
	        while ((len=is.read(Buffer))!=-1){
	            bos.write(Buffer,0,len);
	        }
	        bos.close();
	        String temp=bos.toString("utf-8");

	        return  temp;

	    }
}
