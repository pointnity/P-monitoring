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
	 * @param timeToLiveSeconds Set the expiration time after write cache (unit: second)
	 * @param timeToIdleSeconds Sets the expiration time after reading the cache (unit: second)
	 * @param initialCapacity
	 * @param maximumSize
	 * @param recordStats
	 * @return
	 */
	Public  static  < K ,  V >  LoadingCache < K ,  V >  createLoadingCache ( Integer  concurrencyLevel ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds ,  Integer  initialCapacity ,
			Integer  maximumSize ,  boolean  recordStats )  {
		Return  createLoadingCache ( concurrencyLevel ,  timeToLiveSeconds ,  timeToIdleSeconds ,  initialCapacity ,  maximumSize ,  recordStats ,  null );
	}

	/**
	 *
	 * @param concurrencyLevel
	 * @param timeToLiveSeconds Set the expiration time after write cache (unit: second)
	 * @param timeToIdleSeconds Sets the expiration time after reading the cache (unit: second)
	 * @param initialCapacity
	 * @param maximumSize
	 * @param recordStats
	 * @param removalListener
	 * @return
	 */
	Public  static  < K ,  V >  LoadingCache < K ,  V >  createLoadingCache ( Integer  concurrencyLevel ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds ,  Integer  initialCapacity ,
			Integer  maximumSize ,  boolean  recordStats ,  RemovalListener < K ,  V >  removalListener )  {

		If  ( removalListener  ==  null )  {
			removalListener  =  new  RemovalListener < K ,  V >()  {
				@Override
				Public  void  onRemoval ( RemovalNotification < K ,  V >  notification )  {
					Log . info ( notification . getKey ()  +  " was removed" );
				}
			};
		}

		CacheBuilder < K ,  V >  cacheBuilder  =  CacheBuilder . newBuilder (). removalListener ( removalListener );

		/ / Set the concurrency level to 8, concurrency level refers to the number of threads that can write cache at the same time
		cacheBuilder . concurrencyLevel ( concurrencyLevel );
