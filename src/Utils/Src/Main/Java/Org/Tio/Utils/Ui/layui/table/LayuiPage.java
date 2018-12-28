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
	Public  static  void  main ( String []  args )  {

	}

	Public  static  LayuiPage  ok ( Collection <?>  data ,  long  count )  {
		Return  new  LayuiPage ( data ,  count );
	}

	Private  int  code  =  0 ;

	Private  String  msg  =  null ;

	Private  long  count  =  0 ;  // total data

	Private  Collection <?>  data  =  null ;

	Public  LayuiPage ()  {
	}

	/**
	 *
	 * @author: tanyaowu
	 */
	Public  LayuiPage ( Collection <?>  data ,  long  count )  {
		the this . Data  =  Data ;
		the this . COUNT  =  COUNT ;
	}

	Public  LayuiPage ( int  code )  {
		the this . code  =  code ;
	}

	Public  int  getCode ()  {
		Return  code ;
	}

	Public  long  getCount ()  {
		Return  count ;
	}

	Public  Collection <?>  getData ()  {
		Return  data ;
	}

	Public  String  getMsg ()  {
