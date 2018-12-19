Package  org . tio . utils . page ;

Import  java.util.ArrayList ;
Import  java.util.List ;
Import  java.util.Set ;
Import  java.util.concurrent.locks.Lock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 *  
 */
Public  class  PageUtils  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( PageUtils . class );

	Public  static  < T >  Page < T >  fromList ( List < T >  list ,  int  pageIndex ,  int  pageSize )  {
		If  ( list  ==  null )  {
			Return  null ;
		}
