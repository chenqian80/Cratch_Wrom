package com.bigdata.spider.solr;
import org.apache.commons.lang.StringUtils;

import com.bigdata.spider.entity.Page;
import com.bigdata.spider.util.HbaseUtil;
import com.bigdata.spider.util.RedisUtil;
import com.bigdata.spider.util.SolrUtil;
import com.bigdata.spider.util.ThreadUtil;



/**
 * 建立索引
 *
 */
public class SolrJob{
	//spider.tv_index是跟Hbase的rowkey一模一样的，在建立详情页面的时候就一起建立
	private static final String SOLR_NEWS_INDEX = "newsIndex";
	static RedisUtil redis = new RedisUtil();
	
	//这里是从redis里面取出newsindex里的url，再根据url从hbase里面获取数据、
	//获取完之后建立solr的索引供我们查询
	public static void buildIndex(){
		//从newsindex里每次poll出一个url都赋值给url
		String url = "";
		try {
			System.out.println("开始建立索引！！！");
			HbaseUtil hbaseUtil = new HbaseUtil();
			//取出url
			url = redis.getSet(SOLR_NEWS_INDEX);
			//线程不停止，一直循环
			while (!Thread.currentThread().isInterrupted()) {
				//判断取出来的url是否为空，不为空的话就进入建立索引
				System.out.println("当前url是:"+url);
				if(StringUtils.isNotBlank(url)){
					Page page = hbaseUtil.get(HbaseUtil.TABLE_NAME, url);
					if(page !=null){
						SolrUtil.addIndex(page);
					}
					//一个url索引完成再取出另外一个url，再次进入循环
					//因为我们是sadd，所以，取出的时候要spop，不然报错
					url = redis.getSet(SOLR_NEWS_INDEX);
				//为空的话就休息5秒钟，再进入循环	
				}else{
					System.out.println("目前没有需要索引的数据，休息一会再处理！");
					ThreadUtil.sleep(5000);
				}
			}
		} catch (Exception e) {
			//redis.add(SOLR_NEWS_INDEX, url);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		buildIndex();
	}
}
