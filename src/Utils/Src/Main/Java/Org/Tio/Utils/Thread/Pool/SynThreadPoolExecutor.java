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
				}
			}
		}  else  {
			Return  true ;
		}

	}

	@Override
	Public  void  execute ( Runnable  runnable )  {
		If  ( checkBeforeExecute ( runnable ))  {
			Super . execute ( runnable );
		}
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	Public  String  getName ()  {
		Return  name ;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	Public  void  setName ( String  name )  {
		the this . name  =  name ;
	}

	@Override
	Public  < R >  Future < R >  submit ( Runnable  runnable ,  R  result )  {
		If  ( checkBeforeExecute ( runnable ))  {
			Future < R >  ret  =  super . submit ( runnable ,  result );
			Return  ret ;
		}  else  {
			Return  null ;
		}
	}

}
