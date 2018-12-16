Package  org . tio . utils . lock ;

Import  java.util.List ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 * 
 */
Public  class  ListWithLock < T >  extends  ObjWithLock < List < T >>  {
	Private  static  final  long  serialVersionUID  =  8549668315606224029L ;
