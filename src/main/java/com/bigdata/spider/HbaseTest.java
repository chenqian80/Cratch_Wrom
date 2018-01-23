package com.bigdata.spider;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@SuppressWarnings("deprecation")
public class HbaseTest {
	
	//Configuration conf=null;
	//HBaseAdmin admin=null;
	
	static HTablePool hTablePool = null;
	
	public HbaseTest(){
		//读取配置文件
		Configuration conf = new  Configuration();
		conf.set("hbase.zookeeper.quorum", "ibeifeng.com:2181");
		conf.set("hbase.rootdir", "hdfs://ibeifeng.com:8020/hbase");
		
		try {
			HBaseAdmin admin=new HBaseAdmin(conf);
			hTablePool=new HTablePool(conf,1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void put(String tableName,String row,String columnFamily,String coloumn,String data){
		HTableInterface table=hTablePool.getTable(tableName);
		//HBase在插入的时候都要转化为Bytes
		Put p1=new Put(Bytes.toBytes(row));
		p1.add(Bytes.toBytes(columnFamily),Bytes.toBytes(coloumn),Bytes.toBytes(data));
	    //System.out.println("插入成功");
		try {
			table.put(p1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void get(String tableName, String row) throws IOException {
		
		@SuppressWarnings("resource")
		HTableInterface table = hTablePool.getTable(tableName);
		//System.out.println(table);
		Get get = new Get(row.getBytes());
		Result result = table.get(get);
		//System.out.println(result);
		// 列簇：news
		// 列名: author
		//byte[] authorOfBytes = result.getValue(Bytes.toBytes("news"), Bytes.toBytes("author"));
		//System.out.println(Bytes.toString(authorOfBytes));
		String author = Bytes.toString(result.getValue(Bytes.toBytes("news"), Bytes.toBytes("newsFrom")));
		System.out.println(author);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		HbaseTest hb=new HbaseTest();
		//hb.put("sina","sinanews","news","author","zhangsan");
		//System.out.println("插入成功");
		hb.get("sina","http://sports.sina.com.cn/l/2017-02-17/doc-ifyarrcf4354620.shtml");
	}

}
