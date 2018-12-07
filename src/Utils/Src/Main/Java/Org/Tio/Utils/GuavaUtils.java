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
		If  ( timeToLiveSeconds  !=  null  &&  timeToLiveSeconds  >  0 )  {
			/ / Set the write cache 8 seconds after the expiration
			cacheBuilder . expireAfterWrite ( timeToLiveSeconds ,  TimeUnit . SECONDS );
		}
		If  ( timeToIdleSeconds  !=  null  &&  timeToIdleSeconds  >  0 )  {
			/ / Set the access cache 8 seconds after the expiration
			cacheBuilder . expireAfterAccess ( timeToIdleSeconds ,  TimeUnit . SECONDS );
		}

		/ / Set the initial capacity of the cache container to 10
		cacheBuilder . initialCapacity ( initialCapacity );
		/ / Set the maximum buffer size of 100, after more than 100 will follow the LRU least recently used algorithm to remove the cache item
		cacheBuilder . maximumSize ( maximumSize );

		If  ( recordStats )  {
			/ / Set the hit rate to count the cache
			cacheBuilder . recordStats ();
		}
		// CacheLoader can be specified in the build method, and the cache is automatically loaded by the CacheLoader implementation when the cache does not exist.
		LoadingCache < K ,  V >  loadingCache  =  cacheBuilder . build ( new  CacheLoader < K ,  V >()  {
			@Override
			Public  V  load ( K  key )  throws  Exception  {
				Return  null ;
			}
		});
		Return  loadingCache ;

		// for (int i = 0; i < 20; i++)
		// {
		// //Get the data from the cache. Since we haven't set the cache, we need to load the cached data through CacheLoader.
		// Long student = studentCache.get("p");
		// System.out.println(student);
		// //Hibernate for 1 second
		// TimeUnit.SECONDS.sleep(1);
		// }

		// System.out.println("cache stats:");
		/ / Finally print the cache hit rate and so on
		// System.out.println(studentCache.stats().toString());
	}

	Public  static  void  main ( String []  args )  throws  Exception  {
		Integer  concurrencyLevel  =  8 ;
		Long  timeToLiveSeconds  =  1L ;
		Long  timeToIdleSeconds  =  null ;
		Integer  initialCapacity  =  10 ;
		Integer  maximumSize  =  1000 ;
		Boolean  recordStats  =  false ;
		LoadingCache < String ,  Object >  loadingCache  =  GuavaUtils . CreateLoadingCache ( concurrencyLevel ,  timeToLiveSeconds ,  timeToIdleSeconds ,  initialCapacity ,  maximumSize ,
				recordStats );

		loadCache . put ( "1" ,  "1" );
