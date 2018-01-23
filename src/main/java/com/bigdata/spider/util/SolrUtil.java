package com.bigdata.spider.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.ui.Model;

import com.bigdata.spider.entity.Page;

/**
 * @author ibf
 * 给hbase里面的数据建立索引的一些问题
 * 1，怎么样查询出来数据
 * 2，数据有了更新中怎么办，也就是又进来一批新的爬虫数据，该怎么办
 */
public class SolrUtil {
	// solr服务器地址
	private static final String SOLR_URL = "http://localhost:8080/solr/";
	private static HttpSolrServer server = null;
	static {
		try {
			server = new HttpSolrServer(SOLR_URL);
			server.setAllowCompression(true);
			server.setConnectionTimeout(10000);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立索引
	 * 
	 * @throws Exception
	 */
	public static void addIndex(Page page) throws Exception {
		server.addBean(page);
		// 对索引进行优化
		//server.optimize();
		server.commit();
		System.out.println("添加电视剧索引:" + page.getUrl());
	}
	
	/**
	 * 
	 * 删除索引
	 * @throws Exception
	 */
	public static void delIndex() {
		try {
			server.deleteByQuery("*:*");
			// 要记得提交数据
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列表查询
	 * 
	 * @param skey
	 * @param range
	 * @param start
	 * @param sort
	 * @throws Exception
	 */
	public static List<Page> search(int num,String keywords,Model model) throws Exception {
		SolrQuery params = new SolrQuery();
		if (StringUtils.isNotBlank(keywords)) {
			params.set("q","tvname:"+keywords);
			params.setParam("hl", "true");
			params.setParam("hl.fl", "tvname");
			params.setHighlightSimplePre("<font color=\"red\">");
			params.setHighlightSimplePre("</font>");
		} else {
			params.set("q", "*:*");
		}
		//params.addFilterQuery("tvId:3");
		params.set("start", "" + 0);
		params.set("rows", "" + 20);
		
		if(StringUtils.isNotBlank("")){
			if("".equals("asc")){
				params.setSort("", SolrQuery.ORDER.asc);
			}else{
				params.setSort("", SolrQuery.ORDER.desc);
			}
		}
		QueryResponse response = server.query(params);

		List<Page> results = response.getBeans(Page.class);
		if(StringUtils.isNotBlank(keywords)){
			Map<String,Map<String,List<String>>> map=response.getHighlighting();
		    List<Page> list=new ArrayList<Page>();
		    Page page=null;
		    for (int i = 0; i < results.size(); i++) {
				page=results.get(i);
				page.setUrl((map.get(page.getUrl()).get("tvname").get(0)));
				list.add(page);
			}
		    return list;
		}else{
			return results;
		}
	}

	/**
	 * 列表查询
	 * 
	 * @param skey
	 * @param range
	 * @param start
	 * @param sort
	 * @throws Exception
	 */
	public static Page searchPage(String skey) throws Exception {
		SolrQuery params = new SolrQuery();
		params.set("tvId", skey);
		QueryResponse response = server.query(params);
		List<Page> pages = response.getBeans(Page.class);
		return pages.get(0);
	}
	
	/**
	 * 根据条件查询总记录数
	 * @param skey
	 * @return
	 */
	public static int getCount(String skey) {
		int count = 0;
		SolrQuery params = new SolrQuery();
		if (StringUtils.isNotBlank(skey)) {
			params.set("q", "tvname:"+skey);
		} else {
			params.set("q", "*:*");
		}
		
		try {
			QueryResponse response = server.query(params);
			count = (int) response.getResults().getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	/**
	 * 添加测试的page
	 * @param s
	 * @return
	 * @throws Exception 
	 */
	public static void addPage() throws Exception{
		String[] cource={"solr","es","java","scala","spark","jsp",
				"linux","hadoop","mapreduce","hbase","hive","udf","zookeeper",
				"flume","oozie","redis","mysql","kafka","sqoop","hdfs","spark",
				"storm","mahout","impala","yarn"};
		String[] company={"百度","腾讯","谷歌","阿里巴巴","脸书","大疆","蚂蚁金服"};
		for (int i = 0; i < 25; i++) {
			Page page=new Page();
			/*page.setTvId(i+"");
			page.setTvname(company[i/4]+cource[i]+"课程");
			page.setAgainstnumber(i+"");
			page.setAllnumber(i+"");
//			page.setCollectnumber(i+"");
			page.setCommentnumber(i+"");
//			page.setDaynumber(i+"");
			page.setSupportnumber(i+"");*/
			
			
			addIndex(page);
			
		}
	}
	
	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
		int ch = (int) s.charAt(i);
		if (ch > 255)
		str += "\\u" + Integer.toHexString(ch);
		else
		str += "\\" + Integer.toHexString(ch);
		}
		return str;
		}

	//public static void main(String[] args) throws Exception {
		//addPage();
		//try {
//			int count =SolrUtil.getCount("一");
			//Page page =search.searchPage("一");
//			System.out.println(page.getTvname());
//			System.out.println(count);
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		/*List<Page> pageList=SolrUtil.search("一",0,20,"","");
		for (Page page : pageList) {
			System.out.println(page.getTvname());
		}*/
		/*int count = getCount("一");
		System.out.println(count);*/
		//delIndex();
		
    //}
	public static void main(String[] args) throws Exception {
		delIndex();
	}
}
