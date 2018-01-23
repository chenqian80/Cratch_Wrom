package com.bigdata.spider.util;

import java.io.UnsupportedEncodingException;

public class ByteUtil {
	
	public static String getUTF(String key) throws UnsupportedEncodingException{
		
		String keywords1=new String(key.getBytes("iso-8859-1"),"UTF-8");
		
		return  keywords1;
		
	}

}
