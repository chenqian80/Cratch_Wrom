package com.bigdata.spider.util;

import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtil {
	
	public static String getFieldByRegex(TagNode rootnode,
			String Xpath,String regex){
		
		String number="0";
		Object[] evaluateXpath=null;
		try {
			evaluateXpath=rootnode.evaluateXPath(Xpath);
			
			if(evaluateXpath.length>0){
				//判断结果是否存在，存在的话就祛除需要的内容并且强制转换
				//播放量
				TagNode tagNode=(TagNode)evaluateXpath[0];
				
				//匹配到我们写的数字
				Pattern pattern=Pattern.compile(regex);
				number=RegexUtil.getPageInfoByRegex(tagNode.getText().toString(), pattern, 0);
			}
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return number;

    }
	
	public static String getContentByRegex(TagNode rootnode,
			String Xpath){
		String name="";
		Object[] evaluateXpath=null;
		try {
			evaluateXpath=rootnode.evaluateXPath(Xpath);
			
			if(evaluateXpath.length>0){
				//判断结果是否存在，存在的话就祛除需要的内容并且强制转换
				//播放量
				TagNode tagNode=(TagNode)evaluateXpath[0];
				
				String parseContent= tagNode.getText().toString();
				String[] split = parseContent.split("/");
		    	
		        for (String string : split) {
		        	name+=string.trim()+"/";
				}
		        return name;
			}
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return "";

    }
	
	public static String getDescByRegex(TagNode rootnode,
			String Xpath){
		
		Object[] evaluateXpath=null;
		try {
			evaluateXpath=rootnode.evaluateXPath(Xpath);
			
			if(evaluateXpath.length>0){
				//判断结果是否存在，存在的话就祛除需要的内容并且强制转换
				//播放量
				TagNode tagNode=(TagNode)evaluateXpath[0];
				
				return tagNode.getText().toString();
			}
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return "";

    }
}
