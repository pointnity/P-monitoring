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
