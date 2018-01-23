package com.bigdata.spider;


import redis.clients.jedis.*;

import java.util.List;

@SuppressWarnings("deprecation")
public class RedisTest {
	
	String host = "ibeifeng.com";
	int port = 6379;
	Jedis jedis = new Jedis(host, port);
	
	/**
	 * 单机单链接方式
	 * 使用java代码操作redis
	 * 一般只用于测试代码
	 * @throws Exception
	 */
	public void test1() throws Exception {
		jedis.set("baidu", "www.baidu.com");
		String string = jedis.get("baidu");
		System.out.println(string);
	}
	
	
	/**
	 * 单机连接池方式
	 * @throws Exception
	 */
	public void test2() throws Exception {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(100);//总连接数
		poolConfig.setMaxIdle(10);//空闲链接数
		poolConfig.setMaxWaitMillis(3000);//创建连接的超时时间
		poolConfig.setTestOnBorrow(true);//在创建连接的时候是否会测试
		@SuppressWarnings("resource")
		JedisPool jedisPool = new JedisPool(poolConfig, host, port);
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get("name");
		System.out.println(string);
		jedisPool.returnResourceObject(jedis);
	}
	
	
	/**
	 * 限制用户访问频率
	 * set bf 1
	 * expire bf 20
	 * ttl bf
	 * @throws Exception
	 */
	public void test3() throws Exception {
		
		for(int i=0;i<20;i++){
			boolean checkLogin = checkLogin("haha");
			System.out.println(checkLogin);
		}
	}
	
	
	public boolean checkLogin(String ip){
		String value = jedis.get(ip);
		System.out.println(value);
		if(value==null){
			jedis.set(ip, 0+"");
			jedis.expire(ip, 60);
		}else{
			int parseInt = Integer.parseInt(value);
			if(parseInt>=10){
				System.out.println("访问受限。。。。"+value);
				return false;
			}
		}
		jedis.incr(ip);
		return true;
	}
	
	
	//事务
	public void test4() throws Exception {
		String value = jedis.get("name");
		System.out.println("休息一会。。。");
		Thread.sleep(5000);
		Transaction multi = jedis.multi();
		int parseInt = Integer.parseInt(value);
		parseInt++;
		multi.set("baidu", parseInt+"");
		List<Object> exec = multi.exec();
		System.err.println(exec);
		
	}
	
	public void test5() throws Exception {
		jedis.watch("baidu");
		jedis.unwatch();
		String value = jedis.get("baidu");
		System.out.println("休息一会。。。");
		Thread.sleep(5000);
		Transaction multi = jedis.multi();
		int parseInt = Integer.parseInt(value);
		parseInt++;
		multi.set("baidu", parseInt+"");
		List<Object> exec = multi.exec();
		
		if(exec==null||exec.size()==0){
			System.err.println("值被修改，执行失败");
			test5();
		}
	}
	
	
	
	
	public void test7() throws Exception {
		long starttime = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			jedis.set("x"+i, i+"");
		}
		System.out.println(System.currentTimeMillis()-starttime);
	}
	
	public void test8() throws Exception {
		//Pipeline批量提交(一次性的提交多个)
		long starttime = System.currentTimeMillis();
		Pipeline pipelined = jedis.pipelined();
		for(int i=0;i<1000;i++){
			pipelined.set("y"+i, i+"");
		}
		pipelined.syncAndReturnAll();
		pipelined.close();
		System.out.println(System.currentTimeMillis()-starttime);
	}
	
	public static void main(String[] args) throws Exception {
		RedisTest redistest=new RedisTest();
		redistest.test3();
	}
}

	

