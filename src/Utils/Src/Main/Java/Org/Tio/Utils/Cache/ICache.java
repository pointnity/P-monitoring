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
	 * @return
	 * @author tanyaowu
	 */
	Public  Serializable  get ( String  key );
	
	/**
	 * Get value based on key
	 * @param key
	 * @param clazz
