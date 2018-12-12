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
