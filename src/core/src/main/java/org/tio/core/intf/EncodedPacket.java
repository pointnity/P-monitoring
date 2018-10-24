Package  org . tio . core . intf ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  EncodedPacket  extends  Packet  {

	Private  static  final  long  serialVersionUID  =  1014364783783749718L ;
	Private  byte []  bytes ;

	/**
	 *
	 *
	 * @author tanyaowu
	 *
	 */
	Public  EncodedPacket ( byte []  bytes )  {
		the this . bytes  =  bytes ;
	}

	/**
	 * @return the bytes
	 */
	Public  byte []  getBytes ()  {
		Return  bytes ;
	}
