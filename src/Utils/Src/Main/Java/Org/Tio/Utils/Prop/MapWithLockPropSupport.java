Package  org . tio . utils . prop ;

Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.concurrent.locks.Lock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.lock.MapWithLock ;

/**
 * @author tanyaowu
 * 
 */
Public  class  MapWithLockPropSupport  implements  IPropSupport  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( MapWithLockPropSupport . class );

	/**
