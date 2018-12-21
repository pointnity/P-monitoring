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
