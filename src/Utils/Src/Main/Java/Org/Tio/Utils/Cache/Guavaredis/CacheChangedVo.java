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
