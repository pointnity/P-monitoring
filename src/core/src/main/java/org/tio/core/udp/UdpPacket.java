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

	Public  byte []  getData ()  {
		Return  data ;
	}

	Public  Node  getRemote ()  {
		Return  remote ;
	}

	Public  long  getTime ()  {
		Return  time ;
	}

	Public  void  setData ( byte []  data )  {
		the this . Data  =  Data ;
	}

	Public  void  setRemote ( Node  remote )  {
		the this . Remote  =  Remote ;
	}

	Public  void  setTime ( long  time )  {
		the this . Time  =  Time ;
	}
}
