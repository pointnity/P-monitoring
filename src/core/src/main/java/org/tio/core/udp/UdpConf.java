Package  org . tio . core . udp ;

Import  org.tio.core.Node ;

/**
 * @author tanyaowu
 * 
 */
Public  class  UdpConf  {
	// private static Logger log = LoggerFactory.getLogger(UdpConf.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  int  timeout  =  5000 ;

	Private  Node  serverNode  =  null ;

	Private  String  charset  =  "utf-8" ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  UdpConf ( int  timeout )  {
		This . setTimeout ( timeout );
	}

	Public  String  getCharset ()  {
		Return  charset ;
	}

	Public  Node  getServerNode ()  {
		Return  serverNode ;
	}

	Public  int  getTimeout ()  {
		Return  timeout ;
	}

	Public  void  setCharset ( String  charset )  {
		the this . charset  =  charset ;
	}

	Public  void  setServerNode ( Node  serverNode )  {
		the this . ServerNode  =  ServerNode ;
	}

	Public  void  setTimeout ( int  timeout )  {
		the this . timeout  =  timeout ;
	}
}
