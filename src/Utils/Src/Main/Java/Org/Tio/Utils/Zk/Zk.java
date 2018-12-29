Package  org . tio . utils . zk ;

Import  java.io.File ;
Import  java.util.List ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.apache.curator.RetryPolicy ;
Import  org.apache.curator.framework.CuratorFramework ;
Import  org.apache.curator.framework.CuratorFrameworkFactory ;
Import  org.apache.curator.framework.CuratorFrameworkFactory.Builder ;
Import  org.apache.curator.framework.recipes.cache.PathChildrenCache ;
Import  org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent ;
Import  org.apache.curator.framework.recipes.cache.PathChildrenCacheListener ;
Import  org.apache.curator.retry.ExponentialBackoffRetry ;
Import  org.apache.zookeeper.CreateMode ;
Import  org.apache.zookeeper.data.Stat ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.json.Json ;

Import  com.xiaoleilu.hutool.io.FileUtil ;

/**
 * @author tanyaowu 
 * 
 */
Public  class  Zk  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( Zk . class );

	/**
	 * 
	 * @author: tanyaowu
	 */
	Public  Zk ()  {
	}

	Private  static  final  String  CHARSET  =  "utf-8" ;

	Public  static  CuratorFramework  zkclient  =  null ;
	// static String nameSpace = "php";
	// static {
	// String zkhost = "192.168.1.41:2181";//AppConfig.getInstance().getString("zk.address", null);//"192.168.1.41:2181";//ZK host
	// zkhost = AppConfig.getInstance().getString("zk.address", null);
	//
	// if (StringUtils.isBlank(zkhost)) {
	// log.error("Please configure zookeeper address: {}", "zk.address");
	//
	// }
	//
	// RetryPolicy rp = new ExponentialBackoffRetry(500, Integer.MAX_VALUE);//Retry mechanism
	// Builder builder = CuratorFrameworkFactory.builder().connectString(zkhost).connectionTimeoutMs(5000).sessionTimeoutMs(5000).retryPolicy(rp);
	// // builder.namespace(nameSpace);
	// CuratorFramework zclient = builder.build();
	// zkclient = zclient;
	// zkclient.start();// Implemented in the front
	// // zkclient.newNamespaceAwareEnsurePath(nameSpace);
	//
	// }

	/**
	 * 
