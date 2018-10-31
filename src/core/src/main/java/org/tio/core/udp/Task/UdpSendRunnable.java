Package  org . tio . core . udp . task ;

Import  java.net.DatagramPacket ;
Import  java.net.DatagramSocket ;
Import  java.util.concurrent.LinkedBlockingQueue ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.udp.UdpConf ;

/**
 * @author tanyaowu
 *  
 */
Public  class  UdpSendRunnable  implements  Runnable  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( UdpSendRunnable . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  LinkedBlockingQueue < DatagramPacket >  queue ;

	Private  UdpConf  udpConf ;

	Private  boolean  isStopped  =  false ;

	Private  DatagramSocket  datagramSocket ;

	/**
	 *
	 * @author tanyaowu
	 */
