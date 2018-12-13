Package  org . tio . utils . cache . redis ;

Import  java.util.HashSet ;
Import  java.util.Set ;
Import  java.util.concurrent.TimeUnit ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.lock.SetWithLock ;

/**
 * Regularly update the expiration time of redis
 * @author tanyaowu
 * 
 */
Public  class  RedisExpireUpdateTask  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( RedisExpireUpdateTask . class );

	Private  static  boolean  started  =  false ;

	Private  static  Set < ExpireVo >  set  =  new  HashSet <>();

	Private  static  SetWithLock < ExpireVo >  setWithLock  =  new  SetWithLock <>( set );

	Public  static  void  add ( String  cacheName ,  String  key ,  long  expire )  {
		ExpireVo  expireVo  =  new  ExpireVo ( cacheName ,  key ,  expire );
		setWithLock . add ( expireVo );
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Public  static  void  start ()  {
		// INSTANCE = new RedisExpireUpdateTask();
		If  ( started )  {
