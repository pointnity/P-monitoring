Package  org . tio . utils . thread . pool ;

Import  java.util.concurrent.BlockingQueue ;
Import  java.util.concurrent.Future ;
Import  java.util.concurrent.ThreadFactory ;
Import  java.util.concurrent.ThreadPoolExecutor ;
Import  java.util.concurrent.TimeUnit ;
Import  java.util.concurrent.locks.Lock ;
Import  java.util.concurrent.locks.ReadWriteLock ;

/**
 *
 * @author tanyaowu
 * 
 */
Public  class  SynThreadPoolExecutor  extends  ThreadPoolExecutor  {
	// private static Logger log = LoggerFactory.getLogger(SynThreadPoolExecutor.class);

	/** The name. */
	Private  String  name  =  null ;

	/**
	 *
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime Unit: second
	 * @param runnableQueue
	 * @param threadFactory
	 * @param name
	 * @author tanyaowu
	 */
	Public  SynThreadPoolExecutor ( int  corePoolSize ,  int  maximumPoolSize ,  long  keepAliveTime ,  BlockingQueue < Runnable >  runnableQueue ,  ThreadFactory  threadFactory ,  String  name )  {
		Super ( corePoolSize ,  maximumPoolSize ,  keepAliveTime ,  TimeUnit . SECONDS ,  runnableQueue ,  threadFactory );
		the this . name  =  name ;
	}

	/**
	 *
	 * @param runnable
	 * @return
	 * @author tanyaowu
	 */
	Private  boolean  checkBeforeExecute ( Runnable  runnable )  {
		If  ( runnable  instanceof  ISynRunnable )  {
			ISynRunnable  synRunnableIntf  =  ( ISynRunnable )  runnable ;
			ReadWriteLock  runningLock  =  synRunnableIntf . runningLock ();
			Lock  writeLock  =  runningLock . writeLock ();
			Boolean  tryLock  =  false ;
			Try  {
				tryLock  =  writeLock . tryLock ();
				Return  tryLock ;
			}  finally  {
				If  ( tryLock )  {
					writeLock . unlock ();
