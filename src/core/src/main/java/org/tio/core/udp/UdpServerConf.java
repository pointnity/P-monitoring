Package  org . tio . core . udp ;

Import  org.tio.core.Node ;
Import  org.tio.core.udp.intf.UdpHandler ;

/**
 * @author tanyaowu
 *  
 */
Public  class  UdpServerConf  extends  UdpConf  {
	// private static Logger log = LoggerFactory.getLogger(UdpServerConf.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  UdpHandler  udpHandler ;

	Private  int  readBufferSize  =  1024  *  1024 ;

	Public  UdpServerConf ( int  port ,  UdpHandler  udpHandler ,  int  timeout )  {
		Super ( timeout );
		This . setUdpHandler ( udpHandler );
