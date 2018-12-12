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
	 * @author tanyaowu
	 */
	Public  static  RedisCache  register ( RedissonClient  redisson ,  String  cacheName ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds )  {
		RedisExpireUpdateTask . start ();

		RedisCache  redisCache  =  map . get ( cacheName );
		If  ( redisCache  ==  null )  {
			Synchronized  ( RedisCache . class )  {
				redisCache  =  map . get ( cacheName );
				If  ( redisCache  ==  null )  {
					redisCache  =  new  RedisCache ( redisson ,  cacheName ,  timeToLiveSeconds ,  timeToIdleSeconds );
					Map . put ( cacheName ,  redisCache );
				}
			}
		}
		Return  redisCache ;
	}

	Private  RedissonClient  redisson  =  null ;

	Private  String  cacheName  =  null ;

	Private  Long  timeToLiveSeconds  =  null ;

	Private  Long  timeToIdleSeconds  =  null ;

	Private  Long  timeout  =  null ;

	Private  RedisCache ( RedissonClient  redisson ,  String  cacheName ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds )  {
		the this . redisson  =  redisson ;
		the this . CacheName  =  CacheName ;
		the this . timeToLiveSeconds  =  timeToLiveSeconds ;
		the this . timeToIdleSeconds  =  timeToIdleSeconds ;
		the this . timeout  =  the this . timeToLiveSeconds  ==  null  ?  the this . timeToIdleSeconds  :  the this . timeToLiveSeconds ;

	}

	@Override
	Public  void  clear ()  {
		Long  start  =  SystemTimer . currentTimeMillis ();
		
		RKeys  keys  =  redisson . getKeys ();
		Keys . deleteByPattern ( keyPrefix ( cacheName )  +  "*" );
		
		Long  end  =  SystemTimer . currentTimeMillis ();
		Long  iv  =  end  -  start ;
		Log . info ( "clear cache {}, cost {}ms" ,  cacheName ,  iv );
	}

	@Override
	Public  Serializable  get ( String  key )  {
		If  ( StringUtils . isBlank ( key ))  {
			Return  null ;
		}
		RBucket < Serializable >  bucket  =  getBucket ( key );
		Serializable  ret  =  bucket . get ();
		If  ( timeToIdleSeconds  !=  null )  {
			// bucket.expire(timeout, TimeUnit.SECONDS);
			RedisExpireUpdateTask . add ( cacheName ,  key ,  timeout );
		}
		Return  ret ;
	}

	Public  RBucket < Serializable >  getBucket ( String  key )  {
		Key  =  cacheKey ( cacheName ,  key );
