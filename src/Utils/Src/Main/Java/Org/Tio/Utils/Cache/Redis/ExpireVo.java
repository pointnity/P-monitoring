Package  org . tio . utils . cache . redis ;

Import  java.util.Objects ;

/**
 * @author tanyaowu
 *  
 */
Public  class  ExpireVo  {

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

		// Set<ExpireVo> set = new HashSet<>();
		//
		// ExpireVo expireVo1 = new ExpireVo("x", "1", 1000);
		// ExpireVo expireVo2 = new ExpireVo("x", "1", 1000);
		// ExpireVo expireVo3 = new ExpireVo("x", "2", 1000);
		//
		// boolean x = set.add(expireVo1);
		// boolean y = set.add(expireVo2);
		// boolean z = set.add(expireVo3);
		//
		// System.out.println(set.size());

	}

	Private  String  cacheName ;

	Private  String  key ;

	Private  long  expire ;

	Public  ExpireVo ( String  cacheName ,  String  key ,  long  expire )  {
		Super ();
		the this . CacheName  =  CacheName ;
		the this . Key  =  Key ;
		the this . The expire  =  The expire ;
		// this.expirable = expirable;
	}

	@Override
	Public  boolean  equals ( Object  obj )  {
		If  ( this  ==  obj )  {
			Return  true ;
		}
		If  ( obj  ==  null )  {
			Return  false ;
		}
		If  ( getClass ()  !=  obj . getClass ())  {
			Return  false ;
		}
		ExpireVo  other  =  ( ExpireVo )  obj ;

		Return  Objects . equals ( cacheName ,  other . cacheName )  &&  Objects . equals ( key ,  other . key );
	}

	Public  String  getCacheName ()  {
		Return  cacheName ;
	}

	Public  long  getExpire ()  {
		Return  expire ;
	}

	Public  String  getKey ()  {
		Return  key ;
	}

	@Override
	Public  int  hashCode ()  {
