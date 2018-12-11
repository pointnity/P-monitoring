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
