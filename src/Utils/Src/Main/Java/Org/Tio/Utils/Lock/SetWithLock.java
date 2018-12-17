Package  org . tio . utils . lock ;

Import  java.util.Set ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 * 
 */
Public  class  SetWithLock < T >  extends  ObjWithLock < Set < T >>  {
	Private  static  final  long  serialVersionUID  =  - 2305909960649321346L ;
	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( SetWithLock . class );

	/**
	 * @param set
