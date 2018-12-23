Package  org . tio . utils . resp ;

Import  java.util.Objects ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 * 
 */
Public  enum  RespResult  {
	/**
	 * Successful response
	 */
	OK ( 1 ), 
	/**
	 * failed response
	 */
	FAIL ( 2 ), 
	/**
	 * Unknown response
	 */
	UNKNOWN ( 3 );

	Public  static  RespResult  from ( int  value )  {
		RespResult []  values  =  RespResult . values ();
		For  ( RespResult  v  :  values )  {
			If  ( Objects . equals ( v . value ,  value ))  {
				Return  v ;
