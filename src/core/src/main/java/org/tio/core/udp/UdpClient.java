package org.tio.core.udp;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Node;
import org.tio.core.udp.task.UdpSendRunnable;

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
