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
	 * Number of times the decoding is abnormal
	 */
	Private  AtomicInteger  decodeErrorCount  =  new  AtomicInteger ();

	/**
	 * The number of times the IP connection request was received
	 */
	Private  AtomicInteger  requestCount  =  new  AtomicInteger ();

	/**
	 * The number of currently connected
	 */
// private static MapWithLock<String, AtomicInteger> activatedCount = new MapWithLock<>(new HashMap<String, AtomicInteger>());

	/**
	 * The number of bytes sent by this IP
	 */
	Private  AtomicLong  sentBytes  =  new  AtomicLong ();

	/**
	 * The number of packets sent by this IP
	 */
	Private  AtomicLong  sentPackets  =  new  AtomicLong ();

	/**
	 * The number of bytes processed by this IP
	 */
	Private  AtomicLong  handledBytes  =  new  AtomicLong ();

	/**
	 * The number of packets processed by this IP
	 */
	Private  AtomicLong  handledPackets  =  new  AtomicLong ();

	/**
	 * The number of bytes received by this IP
	 */
	Private  AtomicLong  receivedBytes  =  new  AtomicLong ();

	/**
	 * How many times TCP packets have been received by this IP?
	 */
	Private  AtomicLong  receivedTcps  =  new  AtomicLong ();

	/**
	 * The number of packets received by this IP
	 */
	Private  AtomicLong  receivedPackets  =  new  AtomicLong ();

	Public  IpStat ( String  ip ,  Long  durationType )  {
		the this . IP  =  IP ;
		the this . durationType  =  durationType ;
	}

// /**
// * @return the activatedCount
// */
// public static AtomicInteger getActivatedCount(String ip, boolean forceCreate) {
// AtomicInteger atomicInteger = activatedCount.getObj().get(ip);
// if (atomicInteger == null && forceCreate) {
// Lock lock = activatedCount.getLock().writeLock();
// try {
// lock.lock();
// atomicInteger = activatedCount.getObj().get(ip);
// if (atomicInteger == null) {
// atomicInteger = new AtomicInteger();
// activatedCount.getObj().put(ip, atomicInteger);
// }
