Package  org . tio . utils . thread . pool ;

Import  java.util.concurrent.locks.ReadWriteLock ;

/**
 * 
 * @author tanyaowu 
 * 
 */
Public  interface  ISynRunnable  extends  Runnable  {
	/**
	 * Has the task been cancelled?
	 * @return
	 * @author: tanyaowu
	 */
	Public  boolean  isCanceled ();

	/**
	 * Is it still necessary to perform?
	 * @return
	 * @author: tanyaowu
	 */
	Public  boolean  isNeededExecute ();

	/**
	 * Run lock
	 * @return
	 * @author: tanyaowu
	 */
	Public  ReadWriteLock  runningLock ();

	/**
	 * Perform tasks
	 * 
	 * @author: tanyaowu
