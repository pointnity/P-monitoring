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
