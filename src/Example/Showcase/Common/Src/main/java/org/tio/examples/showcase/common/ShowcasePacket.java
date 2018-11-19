Package  org . tio . examples . showcase . common ;

Import  org.tio.core.intf.Packet ;

/**
 *
 * @author tanyaowu
 */
Public  class  ShowcasePacket  extends  Packet  {
	Private  static  final  long  serialVersionUID  =  - 5481926483435771100L ;
	Public  static  final  int  HEADER_LENGHT  =  5 ; //The length of the message header is 1+4
	Public  static  final  String  CHARSET  =  "utf-8" ;

	/**
 * Message type whose value is defined in org.tio.examples.showcase.common.Type
	 */
	Private  byte  type ;

	Private  byte []  body ;

	Public  ShowcasePacket ()  {
		Super ();
	}

	/**
	 * @param type
	 * @param body
	 * @author tanyaowu
	 */
	Public  ShowcasePacket ( byte  type ,  byte []  body )  {
		Super ();
		the this . type  =  type ;
		the this . body  =  body ;
	}

	/**
	 * @return the body
	 */
	Public  byte []  getBody ()  {
		Return  body ;
	}

	/**
