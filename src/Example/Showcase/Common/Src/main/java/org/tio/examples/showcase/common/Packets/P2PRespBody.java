Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Point-to-point message response
 * @author tanyaowu
 *  
 */
Public  class  P2PRespBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( P2PRespBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	//Message content, required
	Private  String  text ;
