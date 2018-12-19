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

		Page < T >  page  =  pre ( list ,  pageIndex ,  pageSize );

		List < T >  pageData  =  page . getPageData ();
		If  ( pageData  ==  null )  {
			Return  page ;
		}

		Int  startIndex  =  Math . min (( page . getPageIndex ()  -  1 )  *  page . getPageSize (),  list . size ());
		Int  endIndex  =  Math . min ( page . getPageIndex ( )  *  page . getPageSize (),  list . size ());

		For  ( int  i  =  startIndex ;  i  <  endIndex ;  i ++)  {
			pageData . add ( list . get ( i ));
		}
		Page . setPageData ( pageData );
		Return  page ;
	}
		Return  page ;
	}
