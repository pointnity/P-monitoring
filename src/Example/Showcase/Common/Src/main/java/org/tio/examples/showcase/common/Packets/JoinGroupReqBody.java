Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Incoming group request
 * @author tanyaowu
 *  
 */
Public  class  JoinGroupReqBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( JoinGroupReqBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  String  group ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  JoinGroupReqBody ()  {

	}

	/**
	 * @return the group
	 */
	Public  String  getGroup ()  {
		Return  group ;
	}

	/**
	 * @param group the group to set
	 */
	Public  void  setGroup ( String  group )  {
		the this . Group  =  Group ;
	}
}
