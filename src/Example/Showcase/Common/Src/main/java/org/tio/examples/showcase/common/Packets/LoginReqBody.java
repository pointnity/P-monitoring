Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

 /**
 * Login request
 * @author tanyaowu
 *  
 */
 Public  class  LoginReqBody  extends  BaseBody  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( LoginReqBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  String  loginname ;

	Private  String  password ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  LoginReqBody ()  {

	}

	/**
	 * @return the loginname
	 */
	Public  String  getLoginname ()  {
		Return  loginname ;
	}

	/**
	 * @return the password
	 */
	Public  String  getPassword ()  {
		Return  password ;
	}

	/**
	 * @param loginname the loginname to set
	 */
	Public  void  setLoginname ( String  loginname )  {
		the this . LoginName  =  LoginName ;
	}

	/**
	 * @param password the password to set
	 */
	Public  void  setPassword ( String  password )  {
		the this . password  =  password ;
	}
}
