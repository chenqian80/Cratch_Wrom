package com.bigdata.spider.web;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bigdata.spider.entity.Page;
import com.bigdata.spider.entity.PageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/15.
 */
@Controller
public class SolrController {
	static long count=0;
	// solr服务器地址
	private static final String SOLR_URL = "http://localhost:8081/solr/"; 
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
	@RequestMapping("/search.do")
	public static String search(int num,String keywords,Model model) throws Exception {
		String keywords1="";
		if(num==1){
			keywords1=new String(keywords.getBytes("iso-8859-1"),"UTF-8");
		}else{
			keywords1=keywords;
		}
		
		SolrQuery params = new SolrQuery();
		if (StringUtils.isNotBlank(keywords1)) {
			System.out.println("查询的是： "+keywords1);
		
			params.set("q","content:"+keywords1);
			params.setParam("hl", "true");
			params.setParam("hl.fl", "content");
			params.setHighlightSimplePre("<font color=\'red\'>");
			params.setHighlightSimplePost("</font>");
			
		} else {
			params.set("q", "*:*");
		}
		//params.addFilterQuery("tvId:3");
		
		//从哪里开始
		params.setStart((num-1)*10);
		//每页多少条数据
		params.setRows(10);

		if(StringUtils.isNotBlank("")){
			if("asc".equals("asc")){
				params.setSort("content", SolrQuery.ORDER.asc);
			}else{
				params.setSort("content", SolrQuery.ORDER.desc);
			}
		}
		QueryResponse response = server.query(params);
		SolrDocumentList docs = response.getResults();
		int getTime=response.getQTime();
		
		count=docs.getNumFound();

		List<Page> results = response.getBeans(Page.class);
		if(StringUtils.isNotBlank(keywords1)){
			Map<String,Map<String,List<String>>> map=response.getHighlighting();
		    List<Page> list=new ArrayList<Page>();
		    Page page=null;
		    for (int i = 0; i < results.size(); i++) {
				page=results.get(i);
				//返回的是查询的包含高亮字段的结果
				page.setContent(map.get(page.getUrl()).get("content").get(0));
				page.setUrl(page.getUrl());
				
				list.add(page);
			}
		    PageUtil<Page> pageutil = new PageUtil<Page>(num+"",null,count);
		    pageutil.setList(list);
		    model.addAttribute("page",pageutil);
		    model.addAttribute("keywords",keywords1);
		    model.addAttribute("getTime",getTime);
		    System.out.println("ok");
		    return "search.jsp";
		}else{
			return "search.jsp";
		}
	}
}
