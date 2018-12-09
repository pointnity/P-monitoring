Package  org . tio . utils . cache ;

Import  java.io.Serializable ;
Import  java.util.Collection ;

/**
 * @author tanyaowu
 *  
 */
Public  interface  ICache  {

	/**
	 *
	 * Clear all caches
	 * @author tanyaowu
	 */
	Void  clear ();

	/**
	 * Get value based on key
	 * @param key
