package com.bigdata.spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	public static String getPageInfoByRegex(String context,
			Pattern pattern,int groupnunmber){
		
		//提取公共的部分，将代码抽出，写成工具类，优化代码
		Matcher matcher1=pattern.matcher(context);
		
		if(matcher1.find()){
			return matcher1.group(groupnunmber);
		}
		//如果没有匹配到需要的内容，就将它设置为0
		return " ";
	}
	
	public static void main1(String[] args) {
		String text="总播放量:4545454544";
		Pattern pattern=Pattern.compile("[\\d,]+" );
		Matcher matcher1=pattern.matcher(text);
		
		if(matcher1.find()){
			System.out.println(matcher1.group(0)); 
		}
	}
	
	//http://list.youku.com/show/id_zcbffdc80962411de83b1.html
	public static void main(String[] args) {
		String text="http://list.youku.com/show/id_zcbffdc80962411de83b1.html";
		Pattern pattern=Pattern.compile("http://list.youku.com/show/([\\S]+)\\.html" );
		Matcher matcher1=pattern.matcher(text);
		
		if(matcher1.find()){
			System.out.println(matcher1.group(1)); 
		}
	}
}
