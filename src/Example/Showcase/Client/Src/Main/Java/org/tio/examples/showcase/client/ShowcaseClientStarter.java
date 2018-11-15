Package  org . tio . examples . showcase . client ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.tio.client.AioClient ;
Import  org.tio.client.ClientChannelContext ;
Import  org.tio.client.ClientGroupContext ;
Import  org.tio.client.ReconnConf ;
Import  org.tio.client.intf.ClientAioHandler ;
Import  org.tio.client.intf.ClientAioListener ;
Import  org.tio.core.Aio ;
Import  org.tio.core.Node ;
Import  org.tio.examples.showcase.common.Const ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.Type ;
Import  org.tio.examples.showcase.common.packets.GroupMsgReqBody ;
Import  org.tio.examples.showcase.common.packets.JoinGroupReqBody ;
Import  org.tio.examples.showcase.common.packets.LoginReqBody ;
Import  org.tio.examples.showcase.common.packets.P2PReqBody ;
Import  org.tio.utils.json.Json ;

/**
 *
 * @author tanyaowu
 */
Public  class  ShowcaseClientStarter  {
	Static  String  serverIp  =  "127.0.0.1" ;
	Static  int  serverPort  =  Const . PORT ;

	Private  static  Node  serverNode  =  new  Node ( serverIp ,  serverPort );

	/ / used to automatically connect, do not want to automatically connect, please set to null
	Private  static  ReconnConf  reconnConf  =  new  ReconnConf ( 5000L );

	Private  static  ClientAioHandler  aioClientHandler  =  new  ShowcaseClientAioHandler ();
	Private  static  ClientAioListener  aioListener  =  new  ShowcaseClientAioListener ();
	Private  static  ClientGroupContext  clientGroupContext  =  new  ClientGroupContext ( aioClientHandler ,  aioListener ,  reconnConf );

	Private  static  AioClient  aioClient  =  null ;

	Static  ClientChannelContext  clientChannelContext ;

	Public  static  void  command ()  throws  Exception  {
		@SuppressWarnings ( "resource" )
		Java . util . Scanner  sc  =  new  java . util . Scanner ( System . in );
		Int  i  =  1 ;
		StringBuilder  sb  =  new  StringBuilder ();
		Sb . append ( "Usage Guide: \r\n" );
		Sb . append ( i ++  +  ", need help, type '?'.\r\n" );
		Sb . append ( i ++  +  ", login, type 'login loginname password'.\r\n" );
		Sb . append ( i ++  +  ", enter the group, type 'join group1'.\r\n" );
		Sb . append ( i ++  +  ", group chat, type 'groupMsg group1 text'.\r\n" );
		Sb . append ( i ++  +  ", peer-to-peer chat, type 'p2pMsg loginname text'.\r\n" );

		Sb . append ( i ++  +  ", exit the program, type 'exit'.\r\n" );

		System . out . println ( sb );

		String  line  =  sc . nextLine ();  // This is the data entered by the user.
		While  ( true )  {
			If  ( "exit" . equalsIgnoreCase ( line ))  {
