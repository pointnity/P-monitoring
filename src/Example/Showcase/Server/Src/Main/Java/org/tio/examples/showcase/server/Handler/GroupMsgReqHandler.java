Package  org . tio . examples . showcase . server . handler ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Aio ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.Type ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.GroupMsgReqBody ;
Import  org.tio.examples.showcase.common.packets.GroupMsgRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
Public  class  GroupMsgReqHandler  extends  AbsShowcaseBsHandler < GroupMsgReqBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GroupMsgReqHandler . class );

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
	Public  GroupMsgReqHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Class < GroupMsgReqBody >  bodyClass ()  {
		Return  GroupMsgReqBody . class ;
	}

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	Public  Object  handler ( ShowcasePacket  packet ,  GroupMsgReqBody  bsBody ,  ChannelContext  channelContext )  throws  Exception  {
		Log . info ( "Receive group chat request message: {}" ,  Json . toJson ( bsBody ));
		GroupMsgRespBody  groupMsgRespBody  =  new  GroupMsgRespBody ();
		groupMsgRespBody . setText ( bsBody . getText ());
		groupMsgRespBody . setToGroup ( bsBody . getToGroup ());
