Package  org . tio . utils ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

Import  com.google.common.base.Objects ;
Import  com.xiaoleilu.hutool.util.RandomUtil ;

/**
 * @author tanyaowu 
 *  
 */
Public  class  Uuid  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( Uuid . class );

	/**
	 * 
	 * @author: tanyaowu
	 */
	Public  Uuid ()  {
	}


	/**
	 * When the system starts, reset these two values, only
	 */
	Private  static  Integer  workid  =  RandomUtil . randomInt ( 0 ,  31 );
	Private  static  boolean  workidSetted  =  false ;
	
	/**
	 * 
	 */
	Private  static  Integer  datacenterid  =  RandomUtil . randomInt ( 0 ,  31 );
	Private  static  boolean  datacenteridSetted  =  false ;

	Public  static  int  getWorkid ()  {
		Return  workid ;
	}

	Public  static  void  setWorkid ( Integer  workid )  {
		Synchronized  ( log )  {
			If  ( workidSetted )  {
				If  (! Objects . equal ( workid ,  Uuid . workid ))  {
					Log . error ( "workid is only allowed to be set once" );
				}
				Return ;
			}
			If  ( workid  ==  null )  {
				Log . error ( "workid is not allowed to be null" );
				Return ;
