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
		This . setServerNode ( new  Node ( null ,  port ));
	}

	Public  int  getReadBufferSize ()  {
		Return  readBufferSize ;
	}

	Public  UdpHandler  getUdpHandler ()  {
		Return  udpHandler ;
	}

	Public  void  setReadBufferSize ( int  readBufferSize )  {
		the this . ReadBufferSize  =  ReadBufferSize ;
	}

	Public  void  setUdpHandler ( UdpHandler  udpHandler )  {
		the this . udpHandler  =  udpHandler ;
	}
}
