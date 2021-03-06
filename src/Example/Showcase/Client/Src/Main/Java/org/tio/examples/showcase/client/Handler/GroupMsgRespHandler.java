Package  org . tio . examples . showcase . client . handler .

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.GroupMsgRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
*/
Public  class  GroupMsgRespHandler  extends  AbsShowcaseBsHandler < GroupMsgRespBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( GroupMsgRespHandler . class );

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
	Public  GroupMsgRespHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Class < GroupMsgRespBody >  bodyClass ()  {
		Return  GroupMsgRespBody . class ;
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
	Public  Object  handler ( ShowcasePacket  packet ,  GroupMsgRespBody  bsBody ,  ChannelContext  channelContext )  throws  Exception  {
		System . out . println ( "Receive group message:"  +  Json . toJson ( bsBody ));
		Return  null ;
	}
}
