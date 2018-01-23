package com.bigdata.spider.service.impl;


import com.bigdata.spider.entity.Page;
import com.bigdata.spider.service.IDownLoadService;
/**
 * HttpClient页面下载实现类
 * 
 * @author dongxie
 * created by 20170413
 */

public class IDownLoadServiceImpl implements IDownLoadService{
	Page page=null;
    //下载的实现类
	public Page download(String url) {
		//创建实体类
		page=new Page();
		System.out.println(url);
		if(url.length()>40&&url.contains("doc")){
			page.setUrl(url);
			return page;
		}
		page.setUrl(url);
		return page;
	}
}
