Package  org . tio . utils ;

Import  java.util.concurrent.ScheduledExecutorService ;
Import  java.util.concurrent.ScheduledThreadPoolExecutor ;
Import  java.util.concurrent.ThreadFactory ;
Import  java.util.concurrent.TimeUnit ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  SystemTimer  {
	Private  static  class  TimerTask  implements  Runnable  {
		@Override
		Public  void  run ()  {
			Time  =  System . currentTimeMillis ();
		}
	}
