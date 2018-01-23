package com.bigdata.spider.DownThread;

import java.util.HashSet;
import java.util.Set;

import com.bigdata.spider.UrlFactory.UrlFactoryStore;
import com.bigdata.spider.util.RedisUtil;

public class DownloadThread extends Thread{
	
	static Set<String> fatherUrl = UrlFactoryStore.getFatherUrl();
	
	RedisUtil redisutil=new RedisUtil();

	//当前ID号 
	public int ID;
	
	public boolean exit=false;
	
	public DownloadThread(int id){
	
		ID=id;
	}
	
	@Override
	public void run() {
		//super.run();
		// 获取初始的url
        // 现在的实现：在次过程中，会将最初始的url的页面中的子url添加到redis中，但是这里没有讲子url添加到带运行的列表中
		// 代码改成：只获取当前页面url，不获取子页面url
        Set<String> target = getFaUrl();
        // 这里获取子页面url，并讲子url保存(分别保存: 带爬取url列表、newsindex和websiteurl13列表)
		UrlFactoryStore.getUrl(target);
		while(!exit){
			//从任务列表中读取一个没有被下载的任务
			Set<String> newsUrlList =new HashSet<String>();
			while(redisutil.poll(RedisUtil.tmpWebsiteurl)!=null){
				//下载
				newsUrlList.add(redisutil.poll(RedisUtil.tmpWebsiteurl));
			}

			System.out.println("列表:" + newsUrlList.size());
            //获取页面的url并且存储到tmpWebsiteurl中，供下一次循环获取
			 UrlFactoryStore.getUrl(newsUrlList);
			/*else{
				System.out.println("我是第"+ID+"个线程，我现在没有任务");
				//没有任务，休息一下
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}*/
			
		}
	}
	
	public static Set<String> getFaUrl(){
		Set<String> newsUrlList =new HashSet<String>();
		for (String string : fatherUrl) {
		    // 在这里不获取子页面的url
//			newsUrlList.addAll(UrlUtil.getNewsUrlList(string));
            newsUrlList.add(string);
		}
		return newsUrlList;
	}
	
	/*public static Set<String> getDiGUrl(Set<String> newsUrlList){
		Set<String> newsDiGUrlList =new HashSet<String>();
		for (String string : newsUrlList) {
			newsDiGUrlList.addAll(UrlUtil.getNewsUrlList(string));
		}
		return newsDiGUrlList;
	}*/

}
