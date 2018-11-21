Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Send a message response
 * @author tanyaowu
 * 
 */
 Public  class  GroupMsgRespBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GroupMsgRespBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	//Message content, required
	Private  String  text ;

	//Which is the message?
	Private  String  fromUserid ;

	/ / In general, you need to bring the user's nickname and other information to send the message, skipped in showcase

	//Which group to send a message to, can be empty
	Private  String  toGroup ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  GroupMsgRespBody ()  {

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
	 * @return the toGroup
	 */
	Public  String  getToGroup ()  {
		Return  toGroup ;
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

	/**
	 * @param toGroup the toGroup to set
	 */
