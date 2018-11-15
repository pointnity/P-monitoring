Package  org . tio . examples . showcase . client ;

Import  java.util.HashMap ;
Import  java.util.Map ;

Import  org.tio.client.intf.ClientAioHandler ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.examples.showcase.client.handler.GroupMsgRespHandler ;
Import  org.tio.examples.showcase.client.handler.JoinGroupRespHandler ;
Import  org.tio.examples.showcase.client.handler.LoginRespHandler ;
Import  org.tio.examples.showcase.client.handler.P2PRespHandler ;
Import  org.tio.examples.showcase.common.ShowcaseAbsAioHandler ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.Type ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  ShowcaseClientAioHandler  extends  ShowcaseAbsAioHandler  implements  ClientAioHandler  {

	Private  static  Map < Byte ,  AbsShowcaseBsHandler <?>>  handlerMap  =  new  HashMap <>();
	Static  {
		handlerMap . put ( Type . GROUP_MSG_RESP ,  new  GroupMsgRespHandler ());
		handlerMap . put ( Type . JOIN_GROUP_RESP ,  new  JoinGroupRespHandler ());
		handlerMap . PUT ( the Type . LOGIN_RESP ,  new new  LoginRespHandler ());
		handlerMap . put ( Type . P2P_RESP ,  new  P2PRespHandler ());
	}

	Private  static  ShowcasePacket  heartbeatPacket  =  new  ShowcasePacket ( Type . HEART_BEAT_REQ ,  null );

	/**
	 * Processing messages
	 */
	@Override
	Public  void  handler ( Packet  packet ,  ChannelContext  channelContext )  throws  Exception  {
		ShowcasePacket  showcasePacket  =  ( ShowcasePacket )  packet ;
		Byte  type  =  showcasePacket . getType ();
