Package  org . tio . utils . prop ;

/**
 * Attribute support interface
 * @author tanyaowu
 *  
 */
Public  interface  IPropSupport  {
	/**
	 * Clear all attributes
	 * 
	 * @author: tanyaowu
	 */
	Public  void  clearAttribute ();

	/**
	 * Get properties
	 * @param key
	 * @return
	 * @author: tanyaowu
	 */
	Public  Object  getAttribute ( String  key );

	/**
	 * Delete attribute
	 * @param key
	 * @author: tanyaowu
	 */
	Public  void  removeAttribute ( String  key );

	/**
