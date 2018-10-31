Package  org . tio . core . udp . task ;

Import  java.net.DatagramSocket ;
Import  java.util.concurrent.LinkedBlockingQueue ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.udp.UdpPacket ;
Import  org.tio.core.udp.intf.UdpHandler ;

/**
 * @author tanyaowu
 *  
 */
Public  class  UdpHandlerRunnable  implements  Runnable  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( UdpHandlerRunnable . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  UdpHandler  udpHandler ;
	Private  LinkedBlockingQueue < UdpPacket >  queue ;

	Private  DatagramSocket  datagramSocket ;

	Private  boolean  isStopped  =  false ;
