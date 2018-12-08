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

	Private  final  static  ScheduledExecutorService  EXECUTOR  =  new  ScheduledThreadPoolExecutor ( 1 ,  new  ThreadFactory ()  {
		@Override
		Public  Thread  newThread ( Runnable  runnable )  {
			Thread  thread  =  new  Thread ( runnable ,  "SystemTimer" );
			Thread . setDaemon ( true );
