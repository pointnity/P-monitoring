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
	 * @param token the token to set
	 */
	Public  void  setToken ( String  token )  {
		the this . token  =  token ;
	}

	/**
	 * @param userid the userid to set
	 */
	Public  void  setUserid ( String  userid )  {
		the this . the userid  =  the userid ;
	}
}
