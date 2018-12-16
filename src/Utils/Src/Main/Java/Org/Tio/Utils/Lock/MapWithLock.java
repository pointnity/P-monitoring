Package  org . tio . utils . lock ;

Import  java.util.Map ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 *  
 */
Public  class  MapWithLock < K ,  V >  extends  ObjWithLock < Map < K ,  V >>  {
	Private  static  final  long  serialVersionUID  =  - 652862323697152866L ;
	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( MapWithLock . class );

	/**
