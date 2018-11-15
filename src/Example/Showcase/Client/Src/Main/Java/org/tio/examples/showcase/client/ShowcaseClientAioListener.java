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
