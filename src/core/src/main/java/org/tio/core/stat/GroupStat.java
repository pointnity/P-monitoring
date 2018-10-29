Package  org . tio . core . stat ;

Import  java.util.concurrent.atomic.AtomicLong ;

/**
 * 
 * @author tanyaowu 
 *  
 */
Public  class  GroupStat  implements  java . io . Serializable   {
	Private  static  final  long  serialVersionUID  =  - 6988655941470121164L ;
	/**
	 * How many connections are closed
	 */
	Private  AtomicLong  closed  =  new  AtomicLong ();
	/**
