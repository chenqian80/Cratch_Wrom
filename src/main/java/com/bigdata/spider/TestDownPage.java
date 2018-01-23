package com.bigdata.spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;

public class TestDownPage {
	
	//通过HTTPCLIENT获取整个页面
	public static String getPageContent(String url){
		//在方法里创建一个client   
		HttpClientBuilder builder= HttpClients.custom();
		
		CloseableHttpClient client = builder.build();
		
		HttpGet request=new HttpGet(url);
		
		String content = null;
		
		try {
			
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0");
			//Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400
			CloseableHttpResponse response=client.execute(request);
			
			//提供了接收方
			//获取响应消息实体
			HttpEntity entity=response.getEntity();
			
			content=EntityUtils.toString(entity);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static void ParsePage(String content) throws UnsupportedEncodingException {

		Document doc = Jsoup.parse(content);
        String selector="#syncad_0 > ul.list-a.news_top > li:nth-child(2) > a:nth-child(1)";
		Element select = doc.select(selector).first();
		String attr = select.attr("href");
		System.out.println(attr);

		//String vedioUrl=attr;
		String vedioContent=getPageContent(attr);
        Document detailDoc = Jsoup.parse(vedioContent);
        String detailSelect="#artibody";
        String detailselect = detailDoc.select(detailSelect).text();
        String detailText =new String(detailselect.getBytes("ISO-8859-1"),"UTF-8");
        String[] aaaa= detailText.split(" ");
        for (String s:aaaa) {
            System.out.println(s);
        }

        //System.out.println(detailText);
        
        //String detailUrl="http:"+detailselect.attr("href");
        //System.out.println("detailUrl"+detailUrl);
		//String detailContent=getPageContent(detailUrl);
        //Document detailUrlDoc = Jsoup.parse(detailContent);
        //String detailUrlSelector="body > div.s-body > div > div.mod.mod-new > div.mod.fix > div.p-base > ul > li.p-performer > a:nth-child(1)";
		//String detailUrlSelector="#block-B > div > div > div.msg-hd.mb10.clearfix > div.msg-hd-lt.fl > div:nth-child(1) > p:nth-child(1)";
		//String detailUrlSelect = detailDoc.select(detailUrlSelector).first().text();
		//String app = new String(detailUrlSelect.getBytes("UTF-8"),"iso-8859-1");
	    //System.out.println(app);
		//System.out.println(detailUrlSelect);

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content=getPageContent("http://www.sina.com.cn/");
		//System.out.println(content);
		ParsePage(content);
	}

}
