Package  org . tio . examples . showcase . common ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * In a general production project, you need to define a SessionContext to hold the connected session data.
 * @author tanyaowu
 *  
 */
Public  class  ShowcaseSessionContext  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( ShowcaseSessionContext . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  String  token  =  null ;

	Private  String  userid  =  null ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  ShowcaseSessionContext ()  {
	}

	/**
	 * @return the token
	 */
	Public  String  getToken ()  {
		Return  token ;
	}

	/**
	 * @return the userid
	 */
	Public  String  getUserid ()  {
		Return  userid ;
	}

	/**