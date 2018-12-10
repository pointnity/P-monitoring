Package  org . tio . utils . cache . guavaredis ;

Import  java.io.Serializable ;
Import  java.util.UUID ;

/**
 * @author tanyaowu
 *  
 */
Public  class  CacheChangedVo  implements  Serializable  {

	Private  static  final  long  serialVersionUID  =  1546804469064012259L ;

	Public  static  final  String  CLIENTID  =  UUID . randomUUID (). toString ();

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  String  cacheName ;

	Private  String  key ;

	Private  String  clientId  =  CLIENTID ;

	Private  CacheChangeType  type ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  CacheChangedVo ()  {
		Super ();
	}

	// private

	/**
	 * @param cacheName
	 * @param type
	 * @author tanyaowu
	 */
	Public  CacheChangedVo ( String  cacheName ,  CacheChangeType  type )  {
		This ();
		the this . CacheName  =  CacheName ;
		the this . type  =  type ;
	}

	/**
	 * @param cacheName
	 * @param key
	 * @param type
	 * @author tanyaowu
	 */
	Public  CacheChangedVo ( String  cacheName ,  String  key ,  CacheChangeType  type )  {
		This ();
		the this . CacheName  =  CacheName ;
		the this . Key  =  Key ;
		the this . type  =  type ;
	}

	/**
	 * @return the cacheName
	 */
	Public  String  getCacheName ()  {
		Return  cacheName ;
	}

	/**
	 * @return the clientId
	 */
	Public  String  getClientId ()  {
		Return  clientId ;
	}

	/**
	 * @return the key
	 */
	Public  String  getKey ()  {
		Return  key ;
	}

	/**
	 * @return the type
	 */
	Public  CacheChangeType  getType ()  {
		Return  type ;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	Public  void  setCacheName ( String  cacheName )  {
		the this . CacheName  =  CacheName ;
	}

	/**
	 * @param clientId the clientId to set
	 */
	Public  void  setClientId ( String  clientId )  {
		the this . clientId  =  clientId ;
	}

	/**
	 * @param key the key to set
	 */
	Public  void  setKey ( String  key )  {
		the this . Key  =  Key ;
	}

	/**
	 * @param type the type to set
	 */
	public  void  setType ( CacheChangeType  type )  {
		the this . type  =  type ;
	}
}
