Package  org . tio . core . udp ;

Import  java.io.IOException ;
Import  java.io.UnsupportedEncodingException ;
Import  java.net.DatagramPacket ;
Import  java.net.DatagramSocket ;
Import  java.net.InetSocketAddress ;
Import  java.net.SocketException ;
Import  java.util.concurrent.LinkedBlockingQueue ;
Import  java.util.concurrent.atomic.AtomicLong ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Node ;
Import  org.tio.core.udp.intf.UdpHandler ;
Import  org.tio.core.udp.task.UdpHandlerRunnable ;
Import  org.tio.core.udp.task.UdpSendRunnable ;

/**
 * @author tanyaowu
 *  
*/
Public  class  UdpServer  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( UdpServer . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  throws  IOException  {
		Final  AtomicLong  count  =  new  AtomicLong ();
		UdpServer  udpServer  =  null ;
		UdpHandler  udpHandler  =  new  UdpHandler ()  {
			@Override
			Public  void  handler ( UdpPacket  udpPacket ,  DatagramSocket  datagramSocket )  {
				Byte []  data  =  udpPacket . getData ();
				Node  remote  =  udpPacket . getRemote ();
				Long  c  =  count . incrementAndGet ();
				If  ( c  %  100000  ==  0 )  {
					String  str  =  "["  +  new  String ( data )  +  " ] from "  +  remote ;
					Log . error ( str );
				}

				Log . error ( udpPacket . getRemote ()  +  "" );
				DatagramPacket  datagramPacket  =  new  DatagramPacket ( data ,  data . length ,  new  InetSocketAddress ( udpPacket . getRemote (). getIp (),  udpPacket . getRemote (). getPort ()));
				Try  {
					datagramSocket . send ( datagramPacket );
				}  catch  ( Throwable  e )  {
					Log . error ( e . toString ( ),  e );
				}

			}
