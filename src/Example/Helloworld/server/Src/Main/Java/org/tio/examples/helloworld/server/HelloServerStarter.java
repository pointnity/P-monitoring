Package  org . tio . examples . helloworld . server ;

Import  java.io.IOException ;

Import  org.tio.examples.helloworld.common.Const ;
Import  org.tio.server.AioServer ;
Import  org.tio.server.ServerGroupContext ;
Import  org.tio.server.intf.ServerAioHandler ;
Import  org.tio.server.intf.ServerAioListener ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  HelloServerStarter  {
	//handler, including encoding, decoding, message processing
	Public  static  ServerAioHandler  aioHandler  =  new  HelloServerAioHandler ();

	/ / Event listener, can be null, but it is recommended to implement the interface, you can refer to showcase to understand some interfaces
	Public  static  ServerAioListener  aioListener  =  null ;

	/ / A set of connection shared context objects
	Public  static  ServerGroupContext  serverGroupContext  =  new  ServerGroupContext ( aioHandler ,  aioListener );

	//aioServer object
	Public  static  AioServer  aioServer  =  new  AioServer ( serverGroupContext );

	/ / Sometimes need to bind ip, no need to be null
