Package  org . tio . utils ;

Import  java.util.concurrent.TimeUnit ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

Import  com.google.common.cache.CacheBuilder ;
Import  com.google.common.cache.CacheLoader ;
Import  com.google.common.cache.LoadingCache ;
Import  com.google.common.cache.RemovalListener ;
Import  com.google.common.cache.RemovalNotification ;

/**
 *
 * @author tanyaowu
 * 
 */
Public  class  GuavaUtils  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GuavaUtils . class );

	/**
	 *
	 * @param concurrencyLevel
