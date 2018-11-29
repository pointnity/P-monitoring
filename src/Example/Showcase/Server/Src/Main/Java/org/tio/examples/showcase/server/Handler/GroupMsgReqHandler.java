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
