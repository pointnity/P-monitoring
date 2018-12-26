Package  org . tio . utils . thread . pool ;

Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.concurrent.ThreadFactory ;
Import  java.util.concurrent.atomic.AtomicInteger ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  DefaultThreadFactory  implements  ThreadFactory  {

	/** The cacheMap of name and thread factory. */
	Private  static  Map < String ,  DefaultThreadFactory >  mapOfNameAndThreadFactory  =  new  HashMap <>();

	/** The cacheMap of name and atomic integer. */
	Private  static  Map < String ,  AtomicInteger >  mapOfNameAndAtomicInteger  =  new  HashMap <>();

	Public  static  DefaultThreadFactory  getInstance ( String  threadName )  {
		Return  getInstance ( threadName ,  Thread . NORM_PRIORITY );
	}

	/**
	 * Gets the single INSTANCE of DefaultThreadFactory.
	 *
	 * @param threadName the thread name
	 * @param priority the priority
	 * @return single INSTANCE of DefaultThreadFactory
	 */
	Public  static  DefaultThreadFactory  getInstance ( String  threadName ,  Integer  priority )  {
		DefaultThreadFactory  defaultThreadFactory  =  mapOfNameAndThreadFactory . get ( threadName );
		If  ( defaultThreadFactory  ==  null )  {
			defaultThreadFactory  =  new  DefaultThreadFactory ();
			If  ( priority  !=  null )  {
				defaultThreadFactory . priority  =  priority ;
			}

			defaultThreadFactory . setThreadName ( threadName );
			mapOfNameAndThreadFactory . put ( threadName ,  defaultThreadFactory );
			mapOfNameAndAtomicInteger . put ( threadName ,  new  AtomicInteger ());
		}
		Return  defaultThreadFactory ;
	}

	/** The thread pool name. */
	Private  String  threadPoolName  =  null ;

	/** The priority. */
	Private  int  priority  =  Thread . NORM_PRIORITY ;

	/**
	 * Instantiates a new default thread factory.
	 */
	Private  DefaultThreadFactory ()  {

	}

	/**
	 * Gets the thread pool name.
	 *
	 * @return the thread pool name
	 */
	Public  String  getThreadPoolName ()  {
		Return  threadPoolName ;
	}

	/**
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 *
	 * @param r
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	Public  Thread  newThread ( Runnable  r )  {
		Thread  thread  =  new  Thread ( r );
		Thread . setName ( this . getThreadPoolName ( )  +  "-"  +  mapOfNameAndAtomicInteger . get ( this . getThreadPoolName ()). incrementAndGet ());
		Thread . setPriority ( priority );
		Return  thread ;
	}

	/**
	 * Sets the thread name.
	 *
	 * @param threadName the new thread name
	 */
	Public  void  setThreadName ( String  threadName )  {
		the this . threadPoolName  =  threadName ;
	}
