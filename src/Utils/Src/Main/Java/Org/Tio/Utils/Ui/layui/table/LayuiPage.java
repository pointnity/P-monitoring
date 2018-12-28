Package  org . tio . utils . ui . layui . table ;

Import  java.util.Collection ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 * 
 */
Public  class  LayuiPage  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( LayuiPage . class );

	Public  static  LayuiPage  fail ()  {
		Return  new  LayuiPage ( 2 );
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
