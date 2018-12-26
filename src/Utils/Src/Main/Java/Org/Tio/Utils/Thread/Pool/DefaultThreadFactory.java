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
