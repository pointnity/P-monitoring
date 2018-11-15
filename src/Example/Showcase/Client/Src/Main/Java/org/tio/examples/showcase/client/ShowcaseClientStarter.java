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
				System . out . println ( "Thanks for using! bye bye." );
				Break ;
			}  else  if  ( "?" . equals ( line ))  {
				System . out . println ( sb );
			}

			processCommand ( line );

			Line  =  sc . nextLine ();  // This is the data entered by the user.
		}

		aioClient . stop ();
		System . exit ( 0 );
	}

	Public  static  void  main ( String []  args )  throws  Exception  {
		aioClient  =  new  AioClient ( clientGroupContext );
		clientChannelContext  =  aioClient . connect ( serverNode );
		Command ();
	}

	Public  static  void  processCommand ( String  line )  throws  Exception  {
		If  ( StringUtils . isBlank ( line ))  {
			Return ;
		}

		String []  args  =  StringUtils . split ( line ,  " " );
		String  command  =  args [ 0 ];

		If  ( "login" . equalsIgnoreCase ( command ))  {
			String  loginname  =  args [ 1 ];
			String  password  =  args [ 2 ];

			LoginReqBody  loginReqBody  =  new  LoginReqBody ();
			loginReqBody . setLoginname ( loginname );
			loginReqBody . setPassword ( password );

			ShowcasePacket  reqPacket  =  new  ShowcasePacket ();
			reqPacket . setType ( the Type . LOGIN_REQ );
			reqPacket . setBody ( Json . toJson ( loginReqBody ). getBytes ( ShowcasePacket . CHARSET ));

			Aio . send ( clientChannelContext ,  reqPacket );

		}  else  if  ( "join" . equals ( command ))  {
			String  group  =  args [ 1 ];

			JoinGroupReqBody  joinGroupReqBody  =  new  JoinGroupReqBody ();
			joinGroupReqBody . setGroup ( group );

			ShowcasePacket  reqPacket  =  new  ShowcasePacket ();
			reqPacket . setType ( the Type . JOIN_GROUP_REQ );
			reqPacket . setBody ( Json . toJson ( joinGroupReqBody ). getBytes ( ShowcasePacket . CHARSET ));

			Aio . send ( clientChannelContext ,  reqPacket );
		}  else  if  ( "groupMsg" . equals ( command ))  {
			String  group  =  args [ 1 ];
			String  text  =  args [ 2 ];

			GroupMsgReqBody  groupMsgReqBody  =  new  GroupMsgReqBody ();
			groupMsgReqBody . setToGroup ( group );
			groupMsgReqBody . setText ( text );

			ShowcasePacket  reqPacket  =  new  ShowcasePacket ();
			reqPacket . setType ( the Type . GROUP_MSG_REQ );
			reqPacket . setBody ( Json . toJson ( groupMsgReqBody ). getBytes ( ShowcasePacket . CHARSET ));

			Aio . send ( clientChannelContext ,  reqPacket );
		}  else  if  ( "p2pMsg" . equals ( command ))  {
			String  toUserid  =  args [ 1 ];
			String  text  =  args [ 2 ];

			P2PReqBody  p2pReqBody  =  new  P2PReqBody ();
			p2pReqBody . setToUserid ( toUserid );
			p2pReqBody . setText ( text );

			ShowcasePacket  reqPacket  =  new  ShowcasePacket ();
			reqPacket . setType ( the Type . P2P_REQ );
			reqPacket . setBody ( Json . toJson ( p2pReqBody ). getBytes ( ShowcasePacket . CHARSET ));

			Aio . send ( clientChannelContext ,  reqPacket );
		}

	}
}
