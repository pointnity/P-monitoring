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
	Public  UdpSendRunnable ( LinkedBlockingQueue < DatagramPacket >  queue ,  UdpConf  udpConf ,  DatagramSocket  datagramSocket )  {
		the this . Queue  =  Queue ;
		the this . udpConf  =  udpConf ;
		the this . DatagramSocket  =  DatagramSocket ;
	}

	@Override
	Public  void  run ()  {
		DatagramSocket  datagramSocket  =  this . datagramSocket ;
		While  (! isStopped )  {
			Try  {
				DatagramPacket  datagramPacket  =  queue . take ();
				If  ( datagramSocket  ==  null )  {
					datagramSocket  =  new  DatagramSocket ();
					datagramSocket . setSoTimeout ( udpConf . getTimeout ());
				}
				datagramSocket . send ( datagramPacket );

			}  catch  ( Throwable  e )  {
				Log . error ( e . toString ( ),  e );
			}  finally  {
				If  ( queue . size ()  ==  0 )  {
					If  ( this . datagramSocket  ==  null  &&  datagramSocket  !=  null )  {
						datagramSocket . close ();
						datagramSocket  =  null ;
					}
				}
			}
