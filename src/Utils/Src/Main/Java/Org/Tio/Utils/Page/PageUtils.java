Package  org . tio . utils . page ;

Import  java.util.ArrayList ;
Import  java.util.List ;
Import  java.util.Set ;
Import  java.util.concurrent.locks.Lock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.lock.ObjWithLock ;

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

	Public  static  < T >  Page < T >  fromSet ( Set < T >  set ,  int  pageIndex ,  int  pageSize )  {
		If  ( set  ==  null )  {
			Return  null ;
		}

		Page < T >  page  =  pre ( set ,  pageIndex ,  pageSize );

		List < T >  pageData  =  page . getPageData ();
		If  ( pageData  ==  null )  {
			Return  page ;
		}

		Int  startIndex  =  Math . min (( page . getPageIndex ()  -  1 )  *  page . getPageSize (),  set . size ());
		Int  endIndex  =  Math . min ( page . getPageIndex ( )  *  page . getPageSize (),  set . size ());

		Int  i  =  0 ;
		For  ( T  t  :  set )  {
			If  ( i  >=  endIndex )  {
				Break ;
			}
			If  ( i  <  startIndex )  {
				i ++;
				Continue ;
			}

			pageData . add ( t );
			i ++;
			Continue ;
		}
		Page . setPageData ( pageData );
		Return  page ;
	}

	Public  static  < T >  Page < T >  fromSetWithLock ( ObjWithLock < Set < T >>  setWithLock ,  int  pageIndex ,  int  pageSize )  {
		If  ( setWithLock  ==  null )  {
			Return  null ;
		}
		Lock  lock  =  setWithLock . getLock (). readLock ();
		Try  {
			Lock . lock ();
			Set < T >  set  =  setWithLock . getObj ();
			Return  fromSet ( set ,  pageIndex ,  pageSize );
		}  finally  {
			Lock . unlock ();
		}

	}

	Private  static  < T >  Page < T >  pre ( java . util . Collection < T >  list ,  int  pageIndex ,  int  pageSize )  {
		If  ( list  ==  null )  {
			Return  new  Page <>( null ,  pageIndex ,  pageSize ,  0 );
		}

		pageSize  =  processPageSize ( pageSize );
		pageIndex  =  processPageIndex ( pageIndex );

		Int  recordCount  =  list . size ();
		If  ( pageSize  >  recordCount )  {
			pageSize  =  recordCount ;
		}

		List < T >  pageData  =  new  ArrayList <>( pageSize );
