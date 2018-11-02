Package  org . tio . server ;

Import  java.util.concurrent.atomic.AtomicLong ;

Import  org.tio.core.stat.GroupStat ;

/**
 *
 * @author tanyaowu
 *
 */
Public  class  ServerGroupStat  extends  GroupStat  {

	Private  static  final  long  serialVersionUID  =  - 139100692961946342L ;
	/**
	 * How many connections were accepted
	 */
	Private  AtomicLong  accepted  =  new  AtomicLong ();

	/**
	 *
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	Public  ServerGroupStat ()  {
	}

	/**
	 * @return the accepted
	 */
	Public  AtomicLong  getAccepted ()  {
		Return  accepted ;
	}

	/**
	 * @param accepted the accepted to set
	 */
	Public  void  setAccepted ( AtomicLong  accepted )  {
		the this . accepted  =  accepted ;
	}
