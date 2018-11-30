Package  org . tio . examples . showcase . server . handler ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.GroupMsgReqBody ;

/**
 * Heartbeat processing
 * @author tanyaowu
 *  
 */
Public  class  HeartbeatReqHandler  extends  AbsShowcaseBsHandler < GroupMsgReqBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( HeartbeatReqHandler . class );

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
	Public  HeartbeatReqHandler ()  {
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
		//Heartbeat message, you don’t have to do it.
		Return  null ;
	}
}
