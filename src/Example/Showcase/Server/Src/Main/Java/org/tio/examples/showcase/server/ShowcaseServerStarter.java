Package  org . tio . examples . showcase . server ;

Import  java.io.IOException ;

Import  org.tio.server.AioServer ;
Import  org.tio.server.ServerGroupContext ;
Import  org.tio.server.intf.ServerAioHandler ;
Import  org.tio.server.intf.ServerAioListener ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  ShowcaseServerStarter  {
	Static  ServerAioHandler  aioHandler  =  new  ShowcaseServerAioHandler ();
	Static  ServerAioListener  aioListener  =  new  ShowcaseServerAioListener ();
	Static  ServerGroupContext  serverGroupContext  =  new  ServerGroupContext ( aioHandler ,  aioListener );
