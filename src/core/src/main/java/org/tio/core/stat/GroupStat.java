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
	 * Received message packet
	 */
	Private  AtomicLong  receivedPackets  =  new  AtomicLong ();
	
	/**
	 * Number of messages received
	 */
	Private  AtomicLong  receivedBytes  =  new  AtomicLong ();
	
	/**
	 * How many times TCP packets have been received by this IP?
	 */
	Private  AtomicLong  receivedTcps  =  new  AtomicLong ();
	
	/**
	 * Average number of bytes received per TCP, this can be used to monitor slow attacks, configure PacketsPerTcpReceive to locate slow attacks
	 */
	Public  double  getBytesPerTcpReceive ()  {
		If  ( receivedTcps . get ()  ==  0 )  {
			Return  0 ;
		}
		Double  ret  =  ( double ) receivedBytes . get ()  /  ( double ) receivedTcps . get ();
		Return  ret ;
	}
	
	/**
	 * Average number of service packets received per TCP, this can be used to monitor slow attacks. The smaller the value, the more suspected the attack is.
	 */
	Public  double  getPacketsPerTcpReceive ()  {
		If  ( receivedTcps . get ()  ==  0 )  {
			Return  0 ;
		}
		Double  ret  =  ( double ) receivedPackets . get ()  /  ( double ) receivedTcps . get ();
		Return  ret ;
	}
	
	
	/**
	 * Number of message packets processed
	 */
	Private  AtomicLong  handledPacket  =  new  AtomicLong ();

	Private  AtomicLong  handledBytes  =  new  AtomicLong ();

	/**
	 * Number of messages sent
	 */
	Private  AtomicLong  sentPacket  =  new  AtomicLong ();

	/**
	 * The number of bytes sent
	 */
	Private  AtomicLong  sentBytes  =  new  AtomicLong ();

	/**
	 * @return the closed
	 */
	Public  AtomicLong  getClosed ()  {
		Return  closed ;
	}

	/**
	 * @return the handledBytes
	 */
	Public  AtomicLong  getHandledBytes ()  {
		Return  handledBytes ;
	}

	/**
	 * @return the handledPacket
	 */
	Public  AtomicLong  getHandledPacket ()  {
		Return  handledPacket ;
	}
