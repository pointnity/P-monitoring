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
