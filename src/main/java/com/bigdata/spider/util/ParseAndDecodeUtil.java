package com.bigdata.spider.util;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.bigdata.spider.constant.UrlConstant;

public class ParseAndDecodeUtil {

	//获取新闻种类
	public static String getCatgory(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			//新浪有两种模板，如果只用一种，可能出问题
			String selector1=LoadPropertyUtil.getSina(UrlConstant.CARGOTY1);
			String selector2=LoadPropertyUtil.getSina(UrlConstant.CARGOTY2);;
			String select=null;
			if(doc.select(selector1).first() != null){
				select= doc.select(selector1).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}else if(doc.select(selector2).first() != null){
				select= doc.select(selector2).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
			System.out.println("出错的是str啦");
		}
		return " ";
		
	}
	
	//获取新闻标题 #main_title
	public static String getTitle(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			String selector1=LoadPropertyUtil.getSina(UrlConstant.Title1);//#artibodyTitle
			String selector2=LoadPropertyUtil.getSina(UrlConstant.Title2);
			String select=null;
			String keywords1="";
			if(doc.select(selector1).first() != null){
				select= doc.select(selector1).first().text();
				//keywords1 = new String(select.getBytes("iso-8859-1"),"utf-8");
				keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}else if(doc.select(selector2).first() != null){
				select= doc.select(selector2).first().text();
				//keywords1 = new String(select.getBytes("iso-8859-1"),"utf-8");
				keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
		}
		return " ";
		
	}
	
	//获取新闻时间
	public static String getTime(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			String selector1=LoadPropertyUtil.getSina(UrlConstant.Time1);//
			String selector2=LoadPropertyUtil.getSina(UrlConstant.Time2);
			String select=null;
			if(doc.select(selector1).first() != null){
				select= doc.select(selector1).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}else if(doc.select(selector2).first() != null){
				select= doc.select(selector2).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
		}
			return " ";
	}
	
	
	//获取新闻来源
	public static String getNewsFrom(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			String selector1=LoadPropertyUtil.getSina(UrlConstant.NewsFrom1);
			String selector2=LoadPropertyUtil.getSina(UrlConstant.NewsFrom1);
			String select=null;
			//获取页面selector对应的a标签
			//doc.select(selector).first().text()之前用的这个，判断为null直接报错
			if(doc.select(selector1).first() != null){
				select= doc.select(selector1).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}else if(doc.select(selector2).first() != null){
				select= doc.select(selector2).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			return "新浪";
		
		//Page newsPage=new Page();

	}
	
	//获取新闻评论总数
	public static String getCommentCount(String url){
		String commentFUrl = GetCommentUtil.getCommentFUrl(url);
		if(commentFUrl==null){
			return "0";
		}
		return commentFUrl;
	}
	
	//获取新闻内容
	public static String getContent(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			String selector=LoadPropertyUtil.getSina(UrlConstant.Content1);
			String select=null;
			if(doc.select(selector).first() != null){
				select= doc.select(selector).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			return " ";
	}
	
	//获取新闻关键词
	public static String getKeywords(String str) throws UnsupportedEncodingException{
		try {
			Document doc = Jsoup.parse(str);
			String selector1=LoadPropertyUtil.getSina(UrlConstant.Keywords1);
			//#articleContent > div.left > div.article-info.clearfix > div.article-keywords
			String selector2=LoadPropertyUtil.getSina(UrlConstant.Keywords2);
			String select=null;
			if(doc.select(selector1).first() != null){
				select= doc.select(selector1).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}else if(doc.select(selector2).first() != null){
				select= doc.select(selector2).first().text();
				String keywords1 = ByteUtil.getUTF(select);
				System.out.println(keywords1);
				return keywords1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			return " ";
	}
	
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		String url="http://ent.sina.com.cn/tv/zy/2017-07-27/doc-ifyinvwu2611410.shtml";
		String pageContent = PageDownLoadUtil.getPageContent(url);
		System.out.println(pageContent);
		//String title = ParseAndDecodeUtil.getTitle(pageContent);
		Page page=new Page();
		//page.setTitle(title);
		String Catgory =ParseAndDecodeUtil.getCatgory(pageContent);
		page.setCatgory(Catgory);
	}*/
	
}
