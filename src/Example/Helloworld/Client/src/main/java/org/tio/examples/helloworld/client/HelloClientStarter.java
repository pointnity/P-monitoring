Package  org . tio . examples . helloworld . client ;

Import  org.tio.client.AioClient ;
Import  org.tio.client.ClientChannelContext ;
Import  org.tio.client.ClientGroupContext ;
Import  org.tio.client.ReconnConf ;
Import  org.tio.client.intf.ClientAioHandler ;
Import  org.tio.client.intf.ClientAioListener ;
Import  org.tio.core.Aio ;
Import  org.tio.core.Node ;
Import  org.tio.examples.helloworld.common.Const ;
Import  org.tio.examples.helloworld.common.HelloPacket ;

/**
 *
 * @author tanyaowu
 *
 */
Public  class  HelloClientStarter  {
	/ / Server node
	Public  static  Node  serverNode  =  new  Node ( Const . SERVER ,  Const . PORT );

	//handler, including encoding, decoding, message processing
	Public  static  ClientAioHandler  aioClientHandler  =  new  HelloClientAioHandler ();

	/ / Event listener, can be null, but it is recommended to implement the interface, you can refer to showcase to understand some interfaces
	Public  static  ClientAioListener  aioListener  =  null ;

	/ / Automatically connected after the broken chain, do not want to automatically connect, please set to null
	Private  static  ReconnConf  reconnConf  =  new  ReconnConf ( 5000L );

	/ / A set of connection shared context objects
	Public  static  ClientGroupContext  clientGroupContext  =  new  ClientGroupContext ( aioClientHandler ,  aioListener ,  reconnConf );

	Public  static  AioClient  aioClient  =  null ;
	Public  static  ClientChannelContext  clientChannelContext  =  null ;

	/**
	 * Launcher entry
	 */
	Public  static  void  main ( String []  args )  throws  Exception  {
		clientGroupContext . setHeartbeatTimeout ( Const . TIMEOUT );
		aioClient  =  new  AioClient ( clientGroupContext );
		clientChannelContext  =  aioClient . connect ( serverNode );
