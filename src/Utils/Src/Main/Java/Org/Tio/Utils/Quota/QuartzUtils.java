Package  org . tio . utils . quartz ;

Import  static  org . quartz . CronScheduleBuilder . cronSchedule ;
Import  static  org . quartz . TriggerBuilder . newTrigger ;

Import  java.util.ArrayList ;
Import  java.util.Date ;
Import  java.util.List ;
Import  java.util.Map.Entry ;
Import  java.util.Set ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.quartz.CronTrigger ;
Import  org.quartz.Job ;
Import  org.quartz.JobBuilder ;
Import  org.quartz.JobDetail ;
Import  org.quartz.Scheduler ;
Import  org.quartz.SchedulerException ;
Import  org.quartz.impl.StdSchedulerFactory ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

Import  com.xiaoleilu.hutool.setting.dialect.Props ;

/**
 * For Quartz packaging, use: <a href="https://my.oschina.net/talenttan/blog/1550826">https://my.oschina.net/talenttan/blog/1550826</a >
 * @author tanyaowu 
 *  
 */
Public  class  QuartzUtils  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( QuartzUtils . class );

	/**
	 * Default configuration file
	 */
	Private  static  final  String  DEFAULT_FILE  =  "classpath:config/tio-quartz.properties" ;
