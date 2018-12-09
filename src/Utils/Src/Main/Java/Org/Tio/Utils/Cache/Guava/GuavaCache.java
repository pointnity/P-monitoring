Package  org . tio . utils . cache . guava ;

Import  java.io.Serializable ;
Import  java.util.Collection ;
Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.concurrent.ConcurrentMap ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.GuavaUtils ;
Import  org.tio.utils.cache.ICache ;

Import  com.google.common.cache.LoadingCache ;
Import  com.google.common.cache.RemovalListener ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  GuavaCache  implements  ICache  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GuavaCache . class );

	Public  static  Map < String ,  GuavaCache >  map  =  new  HashMap <>();

	Public  static  GuavaCache  getCache ( String  cacheName )  {
		GuavaCache  guavaCache  =  map . get ( cacheName );
		If  ( guavaCache  ==  null )  {
			Log . error ( "cacheName[{}] has not been registered yet, please call: {}.register(cacheName, timeToLiveSeconds, timeToIdleSeconds)" ,  cacheName ,  GuavaCache . class . getSimpleName ());
		}
		Return  guavaCache ;
	}

	/**
	 * timeToLiveSeconds and timeToIdleSeconds are not allowed to be null at the same time
	 * @param cacheName
	 * @param timeToLiveSeconds
	 * @param timeToIdleSeconds
	 * @return
	 * @author tanyaowu
	 */
	Public  static  GuavaCache  register ( String  cacheName ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds )  {
		GuavaCache  guavaCache  =  register ( cacheName ,  timeToLiveSeconds ,  timeToIdleSeconds ,  null );
		Return  guavaCache ;
	}

	Public  static  GuavaCache  register ( String  cacheName ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds ,  RemovalListener < String ,  Serializable >  removalListener )  {
		GuavaCache  guavaCache  =  map . get ( cacheName );
		If  ( guavaCache  ==  null )  {
			Synchronized  ( GuavaCache . class )  {
				guavaCache  =  map . get ( cacheName );
				If  ( guavaCache  ==  null )  {
					Integer  concurrencyLevel  =  8 ;
					Integer  initialCapacity  =  10 ;
					Integer  maximumSize  =  1000000000 ;
					Boolean  recordStats  =  false ;
					LoadingCache < String ,  the Serializable >  loadingCache  =  GuavaUtils . CreateLoadingCache ( concurrencyLevel ,  timeToLiveSeconds ,  timeToIdleSeconds ,  initialCapacity ,
							maximumSize ,  recordStats ,  removalListener );
					guavaCache  =  new  GuavaCache ( loadingCache );
					Map . put ( cacheName ,  guavaCache );
				}
			}
		}
		Return  guavaCache ;
	}

	//

	Private  LoadingCache < String ,  Serializable >  loadingCache  =  null ;

	Private  GuavaCache ( LoadingCache < String ,  Serializable >  loadingCache )  {
		the this . loadingCache  =  loadingCache ;
	}

	@Override
	Public  void  clear ()  {
		loadingCache . invalidateAll ();
	}

	@Override
	Public  Serializable  get ( String  key )  {
		If  ( StringUtils . isBlank ( key ))  {
			Return  null ;
		}
		Return  loadingCache . getIfPresent ( key );
	}

	@Override
	Public  Collection < String >  keys ()  {
		ConcurrentMap < String ,  Serializable >  map  =  loadingCache . asMap ();
		Return  map . keySet ();
	}

	@Override
	Public  void  put ( String  key ,  Serializable  value )  {
		If  ( StringUtils . isBlank ( key ))  {
			Return ;
		}
		loadCache . put ( key ,  value );
	}

	@Override
	Public  void  remove ( St
