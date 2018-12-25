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
	 */
	Protected  AbstractSynRunnable ( Executor  executor )  {
		This . setExecutor ( executor );
	}

	/**
	 * @return the executor
	 */
	Public  Executor  getExecutor ()  {
		Return  executor ;
	}

	@Override
	Public  boolean  isCanceled ()  {
		Return  isCanceled ;
	}

	@Override
	Public  final  void  run ()  {
		If  ( isCanceled ())  //The task has been canceled
		{
			Return ;
		}

		ReadWriteLock  runningLock  =  runningLock ();
		Lock  writeLock  =  runningLock . writeLock ();
		Boolean  trylock  =  writeLock . tryLock ();
		If  (! trylock )  {
			Return ;
		}

		Try  {
			runTask ();
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
		}  finally  {
			writeLock . unlock ();
			If  ( isNeededExecute ())  {
				getExecutor (). execute ( this );
			}
		}
	}

	/**
	 * @see org.tio.core.threadpool.intf.ISynRunnable#runningLock()
	 *
	 * @return
	 * @author tanyaowu
	 * 
	 *
	 */
	@Override
