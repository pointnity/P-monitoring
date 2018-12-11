Package  org . tio . utils . cache . guavaredis ;

Import  java.io.Serializable ;
Import  java.util.Collection ;
Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.Objects ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.redisson.api.RTopic ;
Import  org.redisson.api.RedissonClient ;
Import  org.redisson.api.listener.MessageListener ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.cache.ICache ;
Import  org.tio.utils.cache.guava.GuavaCache ;
Import  org.tio.utils.cache.redis.RedisCache ;
Import  org.tio.utils.cache.redis.RedisExpireUpdateTask ;

/**
 * @author tanyaowu
 *  
 */
Public  class  GuavaRedisCache  implements  ICache  {

	Public  static  final  String  CACHE_CHANGE_TOPIC  =  "TIO_CACHE_CHANGE_TOPIC" ;

	Private  static  Logger  log  =  LoggerFactory . getLogger ( GuavaRedisCache . class );
	Public  static  Map < String ,  GuavaRedisCache >  map  =  new  HashMap <>();
	
	/**
	 * The maximum expiration time in the local, this can prevent the memory from bursting, the unit: seconds
	 */
	Public  static  int  MAX_EXPIRE_IN_LOCAL  =  1800 ;

	Static  RTopic < CacheChangedVo >  topic ;

	Private  static  boolean  inited  =  false ;

	Public  static  GuavaRedisCache  getCache ( String  cacheName )  {
		GuavaRedisCache  guavaRedisCache  =  map . get ( cacheName );
		If  ( guavaRedisCache  ==  null )  {
			Log . error ( "cacheName[{}] is not registered yet, please call when initialized: {}.register(cacheName, timeToLiveSeconds, timeToIdleSeconds)" ,  cacheName ,  GuavaRedisCache . class . getSimpleName ());
		}
		Return  guavaRedisCache ;
	}

	Private  static  void  init ( RedissonClient  redisson )  {
		If  (! inited )  {
			Synchronized  ( GuavaRedisCache . class )  {
				If  (! inited )  {
					Topic  =  redisson . getTopic ( CACHE_CHANGE_TOPIC );
					Topic . the addListener ( new new  the MessageListener < CacheChangedVo > ()  {
						@Override
						Public  void  onMessage ( String  channel ,  CacheChangedVo  cacheChangedVo )  {
							String  clientid  =  cacheChangedVo . getClientId ();
							If  ( StringUtils . isBlank ( clientid ))  {
								Log . error ( "clientid is null" );
								Return ;
							}
							If  ( Objects . equals ( CacheChangedVo . CLIENTID ,  clientid ))  {
								Log . info ( "self-published message, {}" ,  clientid );
								Return ;
							}

							String  cacheName  =  cacheChangedVo . getCacheName ();
							GuavaRedisCache  guavaRedisCache  =  GuavaRedisCache . getCache ( cacheName );
							If  ( guavaRedisCache  ==  null )  {
								Log . warn ( "Cannot find GuavaRedisCache object based on cacheName[{}]" ,  cacheName );
								Return ;
							}

							CacheChangeType  type  =  cacheChangedVo . getType ();
							If  ( type  ==  CacheChangeType . PUT  ||  type  ==  CacheChangeType . UPDATE  ||  type  ==  CacheChangeType . REMOVE )  {
								String  key  =  cacheChangedVo . getKey ();
								guavaRedisCache . guavaCache . remove ( key );
							}  else  if  ( type  ==  CacheChangeType . CLEAR )  {
								guavaRedisCache . guavaCache . clear ();
							}
						}
					});
					Inited  =  true ;
				}
			}
		}
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Public  static  GuavaRedisCache  register ( RedissonClient  redisson ,  String  cacheName ,  Long  timeToLiveSeconds ,  Long  timeToIdleSeconds )  {
		Init ( redisson );

		GuavaRedisCache  guavaRedisCache  =  map . get ( cacheName );
		If  ( guavaRedisCache  ==  null )  {
			Synchronized  ( GuavaRedisCache . class )  {
				guavaRedisCache  =  map . get ( cacheName );
				If  ( guavaRedisCache  ==  null )  {
					RedisCache  redisCache  =  RedisCache . register ( redisson ,  cacheName ,  timeToLiveSeconds ,  timeToIdleSeconds );
					
					Long  timeToLiveSecondsForGuava  =  timeToLiveSeconds ;
					Long  timeToIdleSecondsForGuava  =  timeToIdleSeconds ;
					
					If  ( timeToLiveSecondsForGuava  !=  null )  {
						timeToLiveSecondsForGuava  =  Math . min ( timeToLiveSecondsForGuava ,  MAX_EXPIRE_IN_LOCAL );
					}
					If  ( timeToIdleSecondsForGuava  !=  null )  {
						timeToIdleSecondsForGuava  =  Math . min ( timeToIdleSecondsForGuava ,  MAX_EXPIRE_IN_LOCAL );
					}
					GuavaCache  guavaCache  =  GuavaCache . register ( cacheName ,  timeToLiveSecondsForGuava ,  timeToIdleSecondsForGuava );

					guavaRedisCache  =  new  GuavaRedisCache ( cacheName ,  guavaCache ,  redisCache );
					Map . put ( cacheName ,  guavaRedisCache );
				}
			}
		}
		Return  guavaRedisCache ;
	}

	GuavaCache  guavaCache ;

	RedisCache  redisCache ;

	String  cacheName ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  GuavaRedisCache ()  {
	}

	/**
	 * @param guavaCache
	 * @param redisCache
	 * @author tanyaowu
	 */
	Public  GuavaRedisCache ( String  cacheName ,  GuavaCache  guavaCache ,  RedisCache  redisCache )  {
		Super ();
		the this . CacheName  =  CacheName ;
		the this . guavaCache  =  guavaCache ;
		the this . redisCache  =  redisCache ;
	}

	/**
	 *
	 * @author tanyaowu
	 */
	@Override
	Public  void  clear ()  {
		guavaCache . clear ();
		redisCache . clear ();

		CacheChangedVo  cacheChangedVo  =  new  CacheChangedVo ( cacheName ,  CacheChangeType . CLEAR );
		Topic . publish ( cacheChangedVo );
	}

	/**
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Serializable  get ( String  key )  {
		If  ( StringUtils . isBlank ( key ))  {
			Return  null ;
		}
		
		Serializable  ret  =  guavaCache . get ( key );
		If  ( ret  ==  null )  {
			Ret  =  redisCache . get ( key );
			If  ( ret  !=  null )  {
				guavaCache . put ( key ,  ret );
			}
		}  else  {
			Long  timeToIdleSeconds  =  redisCache . getTimeToIdleSeconds ();
			If  ( timeToIdleSeconds  !=  null )  {
				RedisExpireUpdateTask . add ( cacheName ,  key ,  timeToIdleSeconds );
			}
		}
		Return  ret ;
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Collection < String >  keys ()  {
		Return  redisCache . keys ();
	}

	/**
	 * @param key
	 * @param value
	 * @author tanyaowu
	 */
	@Override
	Public  void  put ( String  key ,  Serializable  value )  {
		guavaCache . put ( key ,  value );
		redisCache . put ( key ,  value );

		CacheChangedVo  cacheChangedVo  =  new  CacheChangedVo ( cacheName ,  key ,  CacheChangeType . PUT );
		Topic . publish ( cacheChangedVo );
	}

	/**
	 * @param key
	 * @author tanyaowu
	 */
	@Override
	Public  void  remove ( String  key )  {
		If  ( StringUtils . isBlank ( key ))  {
			Return ;
		}
		
		guavaCache . remove ( key );
		redisCache . remove ( key );
