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
	 * @param address
	 * @param clientDecorator
	 * @author tanyaowu
	 */
	Public  static  void  init ( String  address ,  ClientDecorator  clientDecorator )  {
		// String zkhost = "192.168.1.41:2181";//AppConfig.getInstance().getString("zk.address", null);//"192.168.1.41:2181";//ZK host
		// zkhost = AppConfig.getInstance().getString("zk.address", null);

		If  ( StringUtils . isBlank ( address ))  {
			Log . error ( "zk address is null" );
			Throw  new  RuntimeException ( "zk address is null" );
		}

		RetryPolicy  rp  =  new  ExponentialBackoffRetry ( 500 ,  Integer . MAX_VALUE ); //Retry mechanism
		Builder  builder  =  CuratorFrameworkFactory . builder (). connectString ( address ). connectionTimeoutMs ( 5000 ). sessionTimeoutMs ( 5000 ). retryPolicy ( rp );
		// builder.namespace(nameSpace);
		Zkclient  =  builder . build ();
		
		If  ( clientDecorator  !=  null )  {
			clientDecorator . decorate ( zkclient );
		}

// zkclient.start();
	}
	
	/**
	 * Start the client. Most mutator methods will not work until the client is started
	 * @author tanyaowu
	 */
	Public  static  void  start ()  {
		Zk . zkclient . start ();
	}

	Public  static  void  main ( String []  args )  throws  Exception  {
		String  path  =  "/192.168.0.0" ;
		String  s  =  Zk . createOrUpdate ( path ,  ( byte [])  null ,  CreateMode . PERSISTENT );
		System . out . println ( s );
		s  =  Zk . createOrUpdate ( path ,  "hello1" ,  CreateMode . EPHEMERAL );
		System . out . println ( s );

		addPathChildrenCacheListener ( path ,  new  PathChildrenCacheListener ()  {

			@Override
			Public  void  childEvent ( CuratorFramework  client ,  PathChildrenCacheEvent  event )  throws  Exception  {
				Switch  ( event . getType ())  {
				Case  CHILD_ADDED:  {
					Log . error ( "Node added: path:"  +  event . getData (). getPath ()  +  ", data:"  +  new  String ( event . getData (). getData ()));
					Break ;
				}

				Case  CHILD_UPDATED:  {
					Log . error ( "Node changed: path:"  +  event . getData (). getPath ()  +  ", data:"  +  new  String ( event . getData (). getData ()));
					Break ;
				}

				Case  CHILD_REMOVED:  {
					Log . error ( "Node removed: path:"  +  event . getData (). getPath ()  +  ", data:"  +  new  String ( event . getData (). getData ()));
					Break ;
				}
				
				Default :{
					Log . error ( "not found correct event type" );
					Break ;
				}
				}
			}
		});

		Byte []  b  =  Zk . getBytes ( path );
		If  ( b  !=  null )  {
			System . out . println ( new  String ( b ,  CHARSET ));
		}

		Zk . createOrUpdate ( path  +  "/children" ,  "children-data" ,  CreateMode . EPHEMERAL );
		List < String >  children  =  Zk . getChildren ( path );
		System . out . println ( "children:"  +  Json . toJson ( children ));

		Zk . delete ( path );
	}

	/**
	 * 
	 * @param path
	 * @param content
	 * @param createMode
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 *  
	 */
	Public  static  String  createOrUpdate ( String  path ,  String  content ,  CreateMode  createMode )  throws  Exception  {
		If  ( content  !=  null )  {
			Return  createOrUpdate ( path ,  content . getBytes ( CHARSET ),  createMode );
		}
		Return  createOrUpdate ( path ,  ( byte [])  null ,  createMode );

	}

	/**
	 * 
	 * @param path
	 * @param content
	 * @param createMode
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 * @Created on: August 3, 2013 at 11:06:54 AM
	 */
	Public  static  String  createOrUpdate ( String  path ,  byte []  content ,  CreateMode  createMode )  throws  Exception  {
		If  (! createMode . isSequential ())  {
			If  ( exists ( path ))  {
				Log . info ( "The node already exists: {}" ,  path );
				If  ( content  !=  null )  {
					setData ( path ,  content );
				}
				Return  path ;
			}
		}
		String  str  =  zkclient . create (). creatingParentsIfNeeded (). withMode ( createMode ). forPath ( path ,  content );

		Return  str ;
	}
