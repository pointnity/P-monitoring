Package  org . tio . utils . cache . guavaredis ;

Import  java.util.Objects ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  enum  CacheChangeType  {
	/**
	 * key level clear local cache
	 */
	REMOVE ( 1 ),
	/**
	 * key level clear local cache
	 */
	UPDATE ( 2 ),
	/**
	 * key level clear local cache
	 */
	PUT ( 3 ),
	/**
	 * cacheName level clears the local cache
	 */
	CLEAR ( 4 );

	Public  static  CacheChangeType  from ( Integer  method )  {
		CacheChangeType []  values  =  CacheChangeType . values ();
		For  ( CacheChangeType  v  :  values )  {
			If  ( Objects . equals ( v . value ,  method ))  {
				Return  v ;
			}
		}
		Return  null ;
	}

	Integer  value ;
