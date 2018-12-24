Package  org . tio . utils . thread ;

/**
 * 
 * @author tanyaowu 
 * 
 */
Public  class  ThreadUtils  {
	Public  static  String  stackTrace ()  {
		StackTraceElement []  elements  =  Thread . currentThread (). getStackTrace ();
		StringBuilder  buf  =  new  StringBuilder ();
		For  ( StackTraceElement  element  :  elements )  {
			buf . the append ( "\ R & lt \ n-" ). the append ( Element . the getClassName ()). the append ( "." ). the append ( Element . getMethodName ()). the append ( "(" ). the append ( Element . getFileName ( )). append ( ":" )
					. append ( element . getLineNumber ()). append ( ")" );
		}
		Return  buf . toString ();
	}
}
