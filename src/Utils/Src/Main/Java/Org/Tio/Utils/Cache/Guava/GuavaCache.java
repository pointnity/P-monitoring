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
