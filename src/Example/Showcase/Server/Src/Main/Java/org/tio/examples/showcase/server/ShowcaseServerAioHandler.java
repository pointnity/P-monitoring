Package  org . tio . examples . showcase . server ;

Import  java.util.HashMap ;
Import  java.util.Map ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.examples.showcase.common.ShowcaseAbsAioHandler ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.Type ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.server.handler.GroupMsgReqHandler ;
Import  org.tio.examples.showcase.server.handler.HeartbeatReqHandler ;
Import  org.tio.examples.showcase.server.handler.JoinGroupReqHandler ;
Import  org.tio.examples.showcase.server.handler.LoginReqHandler ;
Import  org.tio.examples.showcase.server.handler.P2PReqHandler ;
Import  org.tio.server.intf.ServerAioHandler ;

/**
 *
 * @author tanyaowu
 *
*/
Public  class  ShowcaseServerAioHandler  extends  ShowcaseAbsAioHandler  implements  ServerAioHandler  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( ShowcaseServerAioHandler . class );

	Private  static  Map < Byte ,  AbsShowcaseBsHandler <?>>  handlerMap  =  new  HashMap <>();
	Static  {
		handlerMap . put ( Type . GROUP_MSG_REQ ,  new  GroupMsgReqHandler ());
		handlerMap . put ( Type . HEART_BEAT_REQ ,  new  HeartbeatReqHandler ());
		handlerMap . put ( Type . JOIN_GROUP_REQ ,  new  JoinGroupReqHandler ());
		handlerMap . put ( Type . LOGIN_REQ ,  new  LoginReqHandler ());
		handlerMap . put ( Type . P2P_REQ ,  new  P2PReqHandler ());
	}

	/**
	 * Processing messages
	 */
	@Override
	Public  void  handler ( Packet  packet ,  ChannelContext  channelContext )  throws  Exception  {
		ShowcasePacket  showcasePacket  =  ( ShowcasePacket )  packet ;
		Byte  type  =  showcasePacket . getType ();
		AbsShowcaseBsHandler <?>  showcaseBsHandler  =  handlerMap . get ( type );
		If  ( showcaseBsHandler  ==  null )  {
