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

	/ / In general, you need to bring the user's nickname and other information to send the message, skipped in showcase

	//Which is the message?
	Private  String  fromUserid ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  P2PRespBody ()  {

	}

	/**
	 * @return the fromUserid
	 */
	Public  String  getFromUserid ()  {
		Return  fromUserid ;
	}

	/**
	 * @return the text
	 */
	Public  String  getText ()  {
		Return  text ;
	}

	/**
	 * @param fromUserid the fromUserid to set
	 */
	Public  void  setFromUserid ( String  fromUserid )  {
		This . fromUserid  =  fromUserid ;
	}

	/**
	 * @param text the text to set
	 */
	Public  void  setText ( String  text )  {
		the this . text  =  text ;
	}

}
