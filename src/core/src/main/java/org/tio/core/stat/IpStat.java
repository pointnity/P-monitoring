Package  org . tio . core . stat ;

Import  java.util.Date ;
Import  java.util.concurrent.atomic.AtomicInteger ;
Import  java.util.concurrent.atomic.AtomicLong ;

Import  org.tio.utils.SystemTimer ;

Import  com.xiaoleilu.hutool.date.BetweenFormater ;
Import  com.xiaoleilu.hutool.date.BetweenFormater.Level ;

/**
 * This is for the server, mainly used to monitor the IP situation, black and malicious attack IP at any time
 * @author tanyaowu
 *  
 */
Public  class  IpStat  implements  java . io . Serializable  {

	Private  static  final  long  serialVersionUID  =  - 6942731710053482089L ;

	Private  Date  start  =  new  Date ();

	/**
	 * How long is the current statistics, in milliseconds
	 */
	Private  long  duration ;

	/**
	 * Duration type, unit: second, such as 60, 3600, etc.
	 */
	Private  Long  durationType ;

	/**
	 * Client ip
	 */
	Private  String  ip ;

	/**
