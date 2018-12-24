Package  org . tio . utils . thread . pool ;

Import  java.util.concurrent.ConcurrentLinkedQueue ;
Import  java.util.concurrent.Executor ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  abstract  class  AbstractQueueRunnable < T >  extends  AbstractSynRunnable  {
	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( AbstractQueueRunnable . class );

	/** The msg queue. */
	Protected  ConcurrentLinkedQueue < T >  msgQueue  =  new  ConcurrentLinkedQueue <>();

	/**
