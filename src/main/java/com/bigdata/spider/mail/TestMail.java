package com.bigdata.spider.mail;

import com.bigdata.spider.util.EmailUtil;

public class TestMail {
	
	public static void main(String[] args) {  
		String html = "<!DOCTYPE html>";
	    html += "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
	    html += "<title>Insert title here</title>";
	    html += "</head><body>";
	    html += "<div style=\"width:600px;height:400px;margin:50px auto;\">";
	    html += "<h1>我来看看邮件是否发送成功呢</h1><br/><br/>";
	    html += "<p>下面是通过该协议可以创建一个指向电子邮件地址的超级链接，通过该链接可以在Internet中发送电子邮件</p><br/>";
	    html += "<a href=\"www.baidu.com\">send mail</a>";
	    html += "</div>";
	    html += "</body></html>";
		EmailUtil.sendEmail("爬虫项目节点异常", html);
	}
}
