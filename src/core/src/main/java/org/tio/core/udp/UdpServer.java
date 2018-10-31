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
		};
		UdpServerConf  udpServerConf  =  new  UdpServerConf ( 3000 ,  udpHandler ,  5000 );

		udpServer  =  new  UdpServer ( udpServerConf );

		udpServer . start ();
	}

	Private  LinkedBlockingQueue < UdpPacket >  handlerQueue  =  new  LinkedBlockingQueue <>();

	Private  LinkedBlockingQueue < DatagramPacket >  sendQueue  =  new  LinkedBlockingQueue <>();

	Private  DatagramSocket  datagramSocket  =  null ;

	Private  byte []  readBuf  =  null ;

	Private  boolean  isStopped  =  false ;

	Private  UdpHandlerRunnable  udpHandlerRunnable ;

	Private  UdpSendRunnable  udpSendRunnable  =  null ;

	Private  UdpServerConf  udpServerConf ;

	/**
	 *
	 * @author tanyaowu
	 * @throws SocketException
	 */
	Public  UdpServer ( UdpServerConf  udpServerConf )  throws  SocketException  {
		the this . udpServerConf  =  udpServerConf ;
		datagramSocket  =  new  DatagramSocket ( this . udpServerConf . getServerNode (). getPort ());
		readBuf  =  new  byte [ this . udpServerConf . getReadBufferSize ()];
		udpHandlerRunnable  =  new  UdpHandlerRunnable ( udpServerConf . getUdpHandler (),  handlerQueue ,  datagramSocket );

		udpSendRunnable  =  new  UdpSendRunnable ( sendQueue ,  udpServerConf ,  datagramSocket );
	}

	Public  void  send ( byte []  data ,  Node  remoteNode )  {
		InetSocketAddress  inetSocketAddress  =  new  InetSocketAddress ( remoteNode . getIp (),  remoteNode . getPort ());
		DatagramPacket  datagramPacket  =  new  DatagramPacket ( data ,  data . length ,  inetSocketAddress );
		sendQueue . add ( datagramPacket );
	}

	Public  void  send ( String  str ,  Node  remoteNode )  {
