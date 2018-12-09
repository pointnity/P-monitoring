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
	 * @return
	 * @author: tanyaowu
	 */
	Public  < T >  T  get ( String  key ,  Class < T >  clazz );

	/**
	 * Get all the keys
	 * @return
	 * @author tanyaowu
	 */
	Collection < String >  keys ();

	/**
	 * Save the key value to the cache
	 * @param key
	 * @param value
