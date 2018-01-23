package com.bigdata.spider.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.bigdata.spider.service.impl.RedisRepositoryServiceImpl;

/**
 * getNewsUrlList这里是用来获取一个url页面包含的其他url集合
 * getEncoding是用来查看你乱码的文字属于哪种乱码(这里列举的都是常见的编码)
 * 
 * @author 11725
 *
 */
public class UrlUtil {
	
	static RedisRepositoryServiceImpl redisStore=new RedisRepositoryServiceImpl();
	//军事新闻
    // 只获取对应url页面中的子url列表
	public static Set<String> getNewsUrlList(String url){
		
		Set<String> urlList=new HashSet<String>();
		//把传入url获得内容的代码写成一个util类，方便调用
        String content=PageDownLoadUtil.getPageContent(url);
        //.*?是正则的一种，表示的匹配尽量少的内容
        String urlRegex="http://.*?sina.com.cn/.*?shtml";
		//String urlRegex="http://.*?qq.com/.*?htm";

        if(Pattern.compile(urlRegex)!=null){
        	Pattern pattern=Pattern.compile(urlRegex);

    		if(pattern.matcher(content)!=null){
    			Matcher matcher1=pattern.matcher(content);
    			while(matcher1.find()){
    				
    				//这边是对解析的url进行过滤，防止非法的url进入存储，妨碍我们下面的解析
    				//下面都是过滤条件
					String group = matcher1.group(0);
        			/*if(matcher1.group(0).contains("\"")
        					||matcher1.group(0).contains("$")
        					|| matcher1.group(0).contains("/a")
        					|| matcher1.group(0).contains("width")
        					|| matcher1.group(0).contains("/div")
        					|| matcher1.group(0).contains("jpg")
        					|| matcher1.group(0).contains("gif")
        					|| matcher1.group(0).contains("php")
        					|| !matcher1.group(0).contains("doc")){
        				//新闻文章都是包含doc的，不包含过滤掉
        				//其他都是可以的非法的字符
        				continue;
        			}*/
        			if(group.contains("\"")
        					||group.contains("$")
        					|| group.contains("/a")
        					|| group.contains("width")
        					|| group.contains("/div")
        					|| group.contains("jpg")
        					|| group.contains("gif")
        					|| group.contains("php")
        					|| !group.contains("doc")){
        				//新闻文章都是包含doc的，不包含过滤掉
        				//其他都是可以的非法的字符
        				continue;
        			}

       			System.out.println("存入的url是："+group);
                    // 讲url的保存放到调用方
//        			urlStore(group);
        			urlList.add(group);
        		}
        		return urlList;
    		}else{
    			return null;
    		}
    		
        }else{
        	return null;
        }
		
	}
	
	
	//判断你爬取数据乱码的形式,然后根据你的乱码形式解码
	public static String getEncoding(String str) {      
	      String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
	               String s = encode;
	               System.out.println("编码是GB2312");
	              return s;      //是的话，返回“GB2312“，以下代码同理
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
	               String s1 = encode;
	               System.out.println("编码是ISO-8859-1");
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
	               String s2 = encode; 
	               System.out.println("编码是UTF-8");
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
	               String s3 = encode;  
	               System.out.println("编码是GBK");
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "哪个都不是";        //如果都不是，说明输入的内容不属于常见的编码格式。
	   }

	public static void urlStore(String url){
		
		redisStore.addWebsiteurl(url);

		 /*
		  * 集群模式下的url去重
		  * JedisCluster jedisCluster = RedisUtils.getJedisCluster(
	                new HostAndPort("192.168.2.241", 16389),
	                new HostAndPort("192.168.2.242", 16389),
	                new HostAndPort("192.168.2.243", 16389),
	                new HostAndPort("192.168.2.245", 16389),
	                new HostAndPort("192.168.2.246", 16389)
	        );


        BloomFilter<String> bloomFilter = new BloomFilter<String>(0.000001, (int)(173070*1.5));
        bloomFilter.bind(jedisCluster, "redisbitname");
        if (!bloomFilter.contains(url)) {
        	bloomFilter.add(url);
        }*/
	}
	
	

	public static void main(String[] args) {
		/*String urlRegex="\\d";
		String word="我很6";
		Pattern path=Pattern.compile(urlRegex);
		Matcher matcher1=path.matcher(word);
		if(matcher1.find()){
			String group = matcher1.group(0);
			System.out.println(group);
		}*/
		//getNewsUrlList("http://mil.news.sina.com.cn/");
		//getEncoding("ããä¹ è¿å¹³å¯¹ç¾¤å¢æ¹é©å·¥ä½ä½åºéè¦");
	}

}
