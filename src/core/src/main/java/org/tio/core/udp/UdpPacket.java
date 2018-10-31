Package  org . tio . core . udp ;

Import  org.tio.core.Node ;
Import  org.tio.utils.SystemTimer ;

/**
 * @author tanyaowu
 *  
 */
Public  class  UdpPacket  {

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	/**
	 *
	 */
	Private  byte []  data ;

	/**
	 * Peer Node
	 */
	Private  Node  remote ;

	/**
	 * Time when the message was received
	 */
	Private  long  time  =  SystemTimer . currentTimeMillis ();

	/**
	 *
	 * @author tanyaowu
	 */
	Public  UdpPacket ()  {
	}

	Public  UdpPacket ( byte []  data ,  Node  remote )  {
		Super ();
		the this . Data  =  Data ;
		the this . Remote  =  Remote ;
	}
