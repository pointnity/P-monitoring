Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Login response
 * @author tanyaowu
 *  
 */
public  class  LoginRespBody  the extends  BaseBody  {
	Public  static  interface  Code  {
		Integer  SUCCESS  =  1 ;
		Integer  FAIL  =  2 ;
	}

	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  of LoggerFactory . the getLogger ( LoginRespBody . class );

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  String  token ;
	Private  Integer  code ;

	Private  String  msg ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  LoginRespBody ()  {

	}

	/**
	 * @return the code
	 */
	Public  Integer  getCode ()  {
		Return  code ;
	}

	/**
	 * @return the msg
	 */
	Public  String  getMsg ()  {
		Return  msg ;
	}

	/**
	 * @return the token
	 */
	Public  String  getToken ()  {
		Return  token ;
	}

	/**
	 * @param code the code to set
	 */
	Public  void  setCode ( Integer  code )  {
		the this . code  =  code ;
	}

	/**
	 * @param msg the msg to set
	 */
	Public  void  setMsg ( String  msg )  {
		the this . MSG  =  MSG ;
	}
