package com.bigdata.spider.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.bigdata.spider.util.EmailUtil;


/**
 * 监视器
 * @author ibf
 * http://blog.csdn.net/dc_726/article/details/46475633
 *
 */
public class ConfigWatcher implements Watcher {
	CuratorFramework client;
	List<String> oldChildrenList = new ArrayList<String>();

	//构造方法
	public ConfigWatcher() {
		//重试策略:重试3次，每次间隔时间指数增长(有具体增长公式)	
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//zk地址
		String hosts = ZKUtil.ZOOKEEPER_HOSTS;
		client = CuratorFrameworkFactory.newClient(hosts,
				retryPolicy);
		//建立连接
		client.start();

		try {
			//获取子节点集合
			oldChildrenList = client.getChildren().usingWatcher(this)
					.forPath(ZKUtil.PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//具体业务实现
	public void displayConfig() throws InterruptedException, KeeperException {
		try {
			List<String> currentChildrenList = client.getChildren()
					.usingWatcher(this).forPath(ZKUtil.PATH);
			for (String child : currentChildrenList) {
				if (!oldChildrenList.contains(child)) {
					System.out.println("新增加的爬虫节点为：" + child);
				}
			}
			for (String child : oldChildrenList) {
				if (!currentChildrenList.contains(child)) {
					System.out.println("挂掉的爬虫节点为：" + child);
					String subject = "爬虫项目执行异常提醒";
					String message = "ip为"+child+"服务器上的爬虫项目执行异常，请及时处理！！！";
					EmailUtil.sendEmail(subject, message);
				}
			}
			//子节点集合更新
			this.oldChildrenList = currentChildrenList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//监控节点变化
	public void process(WatchedEvent event) {
		if (event.getType() == Event.EventType.NodeChildrenChanged) {
			try {
				//调用具体业务代码
				displayConfig();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} catch (KeeperException e) {
				
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		ConfigWatcher configWatcher = new ConfigWatcher();
		Thread.sleep(Long.MAX_VALUE);//然后一直监控
	}

}
