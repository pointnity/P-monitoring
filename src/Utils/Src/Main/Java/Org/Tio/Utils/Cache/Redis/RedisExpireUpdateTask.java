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
			Return ;
		}
		Synchronized  ( RedisExpireUpdateTask . class )  {
			If  ( started )  {
				Return ;
			}
			Started  =  true ;
		}

		New  Thread ( new  Runnable ()  {
			@Override
			Public  void  run ()  {
				While  ( true )  {
					WriteLock  writeLock  =  setWithLock . getLock (). writeLock ();
					writeLock . lock ();
					Try  {
						Set < ExpireVo >  set  =  setWithLock . getObj ();
						For  ( ExpireVo  expireVo  :  set )  {
						For  ( ExpireVo  expireVo  :  set )  {
							Log . info ( "Update cache expiration time, cacheName:{}, key:{}, expire:{}" ,  expireVo . getCacheName (),  expireVo . getKey (),  expireVo . getExpire ());

							RedisCache . getCache ( expireVo . getCacheName ()). getBucket ( expireVo . getKey ()). expire ( expireVo . getExpire (),  TimeUnit . SECONDS );
							// expireVo.getExpirable().expire(expireVo.getExpire(), TimeUnit.SECONDS);
						}
						Set . clear ();
					}  catch  ( Throwable  e )  {
						Log . error ( e . getMessage (),  e );
					}  finally  {
						writeLock . unlock ();
