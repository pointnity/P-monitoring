Package  org . tio . utils . cache . redis ;

Import  java.io.Serializable ;
Import  java.util.Collection ;
Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.concurrent.TimeUnit ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.redisson.api.RBucket ;
Import  org.redisson.api.RKeys ;
Import  org.redisson.api.RedissonClient ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.SystemTimer ;
Import  org.tio.utils.cache.ICache ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  RedisCache  implements  ICache  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( RedisCache . class );
	Private  static  Map < String ,  RedisCache >  map  =  new  HashMap <>();

	Public  static  String  cacheKey ( String  cacheName ,  String  key )  {
		Return  keyPrefix ( cacheName )  +  key ;
	}

	Public  static  RedisCache  getCache ( String  cacheName )  {
		RedisCache  redisCache  =  map . get ( cacheName );
		If  ( redisCache  ==  null )  {
			Log . error ( "cacheName[{}] has not been registered yet, please call when initialized: {}.register(redisson, cacheName, timeToLiveSeconds, timeToIdleSeconds)" ,  cacheName ,  RedisCache . class . getSimpleName ());
		}
		Return  redisCache ;
	}

	Public  static  String  keyPrefix ( String  cacheName )  {
		Return  cacheName  +  ":" ;
	}

	Public  static  void  main ( String []  args )  {
	}

	/**
	 * timeToLiveSeconds and timeToIdleSeconds are not allowed to be null at the same time
	 * @param cacheName
	 * @param timeToLiveSeconds
	 * @param timeToIdleSeconds
	 * @return
