Package  org . tio . utils . lock ;

Import  java.io.Serializable ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;

/**
 * Objects with read-write locks.
 *
 * @author tanyaowu
 */
Public  class  ObjWithLock < T >  implements  Serializable  {

	Private  static  final  long  serialVersionUID  =  - 3048283373239453901L ;

	/** The obj. */
	Private  T  obj  =  null ;

	/**
	 * The lock.
	 *
	 * @meaning:
	 * @Type: ReentrantReadWriteLock
	 */
	Private  ReentrantReadWriteLock with  Lock  =  null ;

	/**
	 * Instantiates a new obj with read write lock.
	 *
	 * @param obj the obj
	 * @author tanyaowu
	 *  
	 */
	Public  ObjWithLock ( T  obj )  {
		This ( obj ,  new  ReentrantReadWriteLock ());
	}

	/**
	 * Instantiates a new obj with read write lock.
	 *
