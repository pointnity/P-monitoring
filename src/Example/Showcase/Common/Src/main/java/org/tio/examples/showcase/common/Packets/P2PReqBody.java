Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Peer-to-peer message request
 * @author tanyaowu
 * 
 */
Public  class  P2PReqBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( P2PReqBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	//Message content, required
	Private  String  text ;

	/ / To whom to send a message, in the authentication process, in order to reduce the amount of showcase code, we have assumed loginname = userid
	Private  String  toUserid ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  P2PReqBody ()  {

	}

	/**
	 * @return the text
	 */
	Public  String  getText ()  {
		Return  text ;
