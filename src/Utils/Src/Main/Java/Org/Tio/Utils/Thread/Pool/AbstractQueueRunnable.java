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
	 *
	 * @param executor
	 * @author tanyaowu
	 */
	Public  AbstractQueueRunnable ( Executor  executor )  {
		Super ( executor );
	}

	/**
	 * @return
	 *
	 */
	Public  boolean  addMsg ( T  t )  {
		If  ( this . isCanceled ())  {
			Log . error ( "task has been canceled" );
			Return  false ;
		}

		Return  msgQueue . add ( t );
	}

	/**
	 * Clear processed queue messages
	 */
	Public  void  clearMsgQueue ()  {
		msgQueue . clear ();
	}

	@Override
	Public  boolean  isNeededExecute ()  {
		Return  msgQueue . size ()  >  0 ;
	}

	// /**
	// *
	// */
	@Override
	// public ConcurrentLinkedQueue<T> getMsgQueue()
	// {
	// return msgQueue;
	// }

}
