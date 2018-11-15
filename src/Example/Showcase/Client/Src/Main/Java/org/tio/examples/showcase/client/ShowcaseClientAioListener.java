Package  org . tio . examples . showcase . client ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.client.intf.ClientAioListener ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.examples.showcase.common.ShowcaseSessionContext ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 * 
 */
Public  class  ShowcaseClientAioListener  implements  ClientAioListener  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( ShowcaseClientAioListener . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	Public  ShowcaseClientAioListener ()  {
	}

	/**
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	Public  void  onAfterClose ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark ,  boolean  isRemove )  throws  Exception  {
		Log . info ( "onAfterClose channelContext:{}, throwable:{}, remark:{}, isRemove:{}" ,  channelContext ,  throwable ,  remark ,  isRemove );
	}

	/**
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	Public  void  onAfterConnected ( ChannelContext  channelContext ,  boolean  isConnected ,  boolean  isReconnect )  throws  Exception  {
		Log . info ( "onAfterConnected channelContext:{}, isConnected:{}, isReconnect:{}" ,  channelContext ,  isConnected ,  isReconnect );

		/ / After connecting, you need to set the connection session object to the channelContext
		channelContext . setAttribute ( new  ShowcaseSessionContext ());

	}

	/**
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	Public  void  onAfterReceived ( ChannelContext  channelContext ,  Packet  packet ,  int  packetSize )  throws  Exception  {
		Log . info ( "onAfterReceived channelContext:{}, packet:{}, packetSize:{}" ,  channelContext ,  Json . toJson ( packet ),  packetSize );
	}

	/**
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
