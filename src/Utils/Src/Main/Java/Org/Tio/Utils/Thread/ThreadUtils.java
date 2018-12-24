Package  org . tio . utils . thread ;

/**
 * 
 * @author tanyaowu 
 * 
 */
Public  class  ThreadUtils  {
	Public  static  String  stackTrace ()  {
		StackTraceElement []  elements  =  Thread . currentThread (). getStackTrace ();
