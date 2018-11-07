Package  org . tio . examples . helloworld . common ;

Import  org.tio.core.intf.Packet ;

/**
 * @author tanyaowu
 */
Public  class  HelloPacket  extends  Packet  {
	Private  static  final  long  serialVersionUID  =  - 172060606924066412L ;
	Public  static  final  int  HEADER_LENGHT  =  4 ; // length of the message header
	Public  static  final  String  CHARSET  =  "utf-8" ;
	Private  byte []  body ;

	/**
	 * @return the body
	 */
	Public  byte []  getBody ()  {
		Return  body ;
	}

	/**
	 * @param body the body to set
