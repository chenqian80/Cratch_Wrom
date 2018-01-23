package com.bigdata.spider.DownThread;

import com.bigdata.spider.entity.Page;
import com.bigdata.spider.service.IDownLoadService;
import com.bigdata.spider.service.IProcessService;
import com.bigdata.spider.service.IStoreService;
import com.bigdata.spider.service.impl.HbaseStoreServiceImpl;
import com.bigdata.spider.service.impl.IDownLoadServiceImpl;
import com.bigdata.spider.service.impl.IProcessServiceImpl;
import com.bigdata.spider.service.impl.RedisRepositoryServiceImpl;
//import com.bigdata.spider.util.ThreadUtil;

public class ParseThread extends Thread{
	
	static String faurl="";
	static IDownLoadService idownLoadService=new IDownLoadServiceImpl();
	static IProcessService iProcessServiceImpl = new IProcessServiceImpl();
	static IStoreService iStoreServiceImpl = new HbaseStoreServiceImpl();
	
	static RedisRepositoryServiceImpl redisGet=new RedisRepositoryServiceImpl();
	
	static String websiteurl =  "";
	

	//当前ID号 
	public int ID;
	
	public boolean exit=false;
	
	public ParseThread(int id){
	
		ID=id;
	}
	
	@Override
	public void run() {
		
		while(!exit){
			//从任务列表中读取一个没有被下载的任务
			websiteurl= redisGet.getWebsiteurl();
			
			if(websiteurl!=null){
				//解析页面
				Page process = iProcessServiceImpl.process(idownLoadService.download(websiteurl));
				if(process!=null){
					// 数据存储hbase
					iStoreServiceImpl.store(process);
				}
			}
			else{
				System.out.println("我是第"+ID+"个线程，我现在没有任务");
				//没有任务，休息一下
				try {
					System.out.println("目前没有需要解析的页面");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}
