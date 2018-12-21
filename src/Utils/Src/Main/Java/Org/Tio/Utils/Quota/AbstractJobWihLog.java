Package  org . tio . utils . quartz ;

Import  org.quartz.Job ;
Import  org.quartz.JobExecutionContext ;
Import  org.quartz.JobExecutionException ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.SystemTimer ;

Import  com.xiaoleilu.hutool.date.DateUtil ;

/**
 * @author tanyaowu 
 * 
 */
Public  abstract  class  AbstractJobWihLog  implements  Job  {
	Private  Logger  log  =  LoggerFactory . getLogger ( AbstractJobWihLog . class );

	/**
	 * 
	 * @author: tanyaowu
	 */
	Public  AbstractJobWihLog ()  {
		Log  =  LoggerFactory . getLogger ( AbstractJobWihLog . class );
	}

	/** 
	 * @param context
	 * @throws JobExecutionException
	 * @author: tanyaowu
	 */
	@Override
	Public  void  execute ( JobExecutionContext  context )  throws  JobExecutionException  {
		Log . info ( "Timed task [{}] runs start, this time id:{}, this execution time: {}, last execution time: {}, next execution time: {}" ,  this . getClass ( ). getName (),  context . getFireInstanceId (),  DateUtil . FormatDateTime ( context . getFireTime ()),
				DateUtil . formatDateTime ( context . getPreviousFireTime ()),  DateUtil . formatDateTime ( context . getNextFireTime ()));
		Long  start  =  SystemTimer . currentTimeMillis ();
		Try  {
			Run ( context );
		}  catch  ( JobExecutionException  e )  {
			Throw  e ;
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
		}
		Long  end  =  SystemTimer . currentTimeMillis ();
		Long  iv  =  end  -  start ;
		Log . info ( "Timed task [{}] has finished running, this time id:{}, this execution time: {}, time-consuming: {}ms" ,  this . getClass (). getName (),  context . getFireInstanceId (),  DateUtil . formatDateTime ( context . getFireTime ()),  iv );

	}

	/**
