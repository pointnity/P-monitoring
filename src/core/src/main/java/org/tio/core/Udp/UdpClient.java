Package  org . tio . core . udp ;

Import  java.io.UnsupportedEncodingException ;
Import  java.net.DatagramPacket ;
Import  java.net.InetSocketAddress ;
Import  java.util.concurrent.LinkedBlockingQueue ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Node ;
Import  org.tio.core.udp.task.UdpSendRunnable ;

/**
 * @author tanyaowu
 *  
 */
Public  class  UdpClient  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( UdpClient . class );

	// private static final int TIMEOUT = 5000; //Set the timeout for receiving data

	Public  static  void  main ( String []  args )  {
		UdpClientConf  udpClientConf  =  new  UdpClientConf ( "127.0.0.1" ,  3000 ,  5000 );
		UdpClient  udpClient  =  new  UdpClient ( udpClientConf );
		udpClient . start ();

		Long  start  =  System . currentTimeMillis ();
		For  ( int  i  =  0 ;  i  <  1000000 ;  i ++)  {
			String  str  =  i  +  ", "  +  "somewhat meaning" ;
			udpClient . send ( str . getBytes ());
		}
		Long  end  =  System . currentTimeMillis ();
		Long  iv  =  end  -  start ;
		System . out . println ( "Time consuming:"  +  iv  +  "ms" );
	}

	Private  LinkedBlockingQueue < DatagramPacket >  queue  =  new  LinkedBlockingQueue <>();

	Private  UdpClientConf  udpClientConf  =  null ;

	/**
	 * server address
	 */
	Private  InetSocketAddress  inetSocketAddress  =  null ;

	Private  UdpSendRunnable  udpSendRunnable  =  null ;

	Public  UdpClient ( UdpClientConf  udpClientConf )  {
		Super ();
		the this . udpClientConf  =  udpClientConf ;
		Node  node  =  this . udpClientConf . getServerNode ();
		inetSocketAddress  =  new  InetSocketAddress ( node . getIp (),  node . getPort ());
		udpSendRunnable  =  new  UdpSendRunnable ( queue ,  udpClientConf ,  null );
	}

	Public  void  send ( byte []  data )  {
		DatagramPacket  datagramPacket  =  new  DatagramPacket ( data ,  data . length ,  inetSocketAddress );
		Queue . add ( datagramPacket );
	}

	Public  void  send ( String  str )  {
		Send ( str ,  null );
	}

	Public  void  send ( String  data ,  String  charset )  {
		If  ( StringUtils . isBlank ( data ))  {
			Return ;
		}
		Try  {
			If  ( StringUtils . isBlank ( charset ))  {
				Charset  =  udpClientConf . getCharset ();
			}
			Byte []  bs  =  data . getBytes ( charset );
			Send ( bs );
		}  catch  ( UnsupportedEncodingException  e )  {
			Log . error ( e . toString ( ),  e );
		}
	}

	Public  void  start ()  {
		Thread  thread  =  new  Thread ( udpSendRunnable ,  "tio-udp-client-send" );
		Thread . setDaemon ( false );
		Thread . start ();
	}
}
