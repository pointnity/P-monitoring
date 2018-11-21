Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Group message request
 * @author tanyaowu
 *  
 */
Public  class  GroupMsgReqBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GroupMsgReqBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	//Message content, required
	Private  String  text ;

	//Which group to send a message to, can be empty
	Private  String  toGroup ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  GroupMsgReqBody ()  {

	}

	/**
	 * @return the text
	 */
	Public  String  getText ()  {
		Return  text ;
	}

	/**
	 * @return the toGroup
	 */
