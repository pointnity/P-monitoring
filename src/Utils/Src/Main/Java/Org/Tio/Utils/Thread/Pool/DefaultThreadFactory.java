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
