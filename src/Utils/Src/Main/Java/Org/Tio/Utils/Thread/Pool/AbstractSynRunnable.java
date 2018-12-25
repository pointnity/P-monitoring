Package  org . tio . utils . thread . pool ;

Import  java.util.concurrent.Executor ;
Import  java.util.concurrent.locks.Lock ;
Import  java.util.concurrent.locks.ReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 *
 * @author tanyaowu
 *
 */
Public  abstract  class  AbstractSynRunnable  implements  ISynRunnable  {

	/** The log. */
	Private  static  Logger  log  =  LoggerFactory . getLogger ( AbstractSynRunnable . class );

	Private  ReadWriteLock  runningLock  =  new  ReentrantReadWriteLock ();

	Private  Executor  executor ;

	Private  boolean  isCanceled  =  false ;

	/**
	 * Instantiates a new abstract syn runnable.
