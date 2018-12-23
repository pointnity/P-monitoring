Package  org . tio . utils . resp ;

/**
 * @author tanyaowu
 *  
 */
Public  class  RespVo  implements  java . io . Serializable  {
	Private  static  final  long  serialVersionUID  =  7492427869347211588L ;
	// private static Logger log = LoggerFactory.getLogger(RespVo.class);

	Public  static  RespVo  fail ()  {
		RespVo  resp  =  new  RespVo ( RespResult . FAIL );
		Return  resp ;
	}

	Public  static  RespVo  fail ( String  msg )  {
		Return  fail (). msg ( msg );
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {
		RespVo . fail (). code ( null ). data ( null ). msg ( null );
	}

	Public  static  RespVo  ok ()  {
