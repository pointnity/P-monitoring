Package  org . tio . examples . showcase . server . handler ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Aio ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.Type ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.JoinGroupReqBody ;
Import  org.tio.examples.showcase.common.packets.JoinGroupRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 * 
 */
Public  class  JoinGroupReqHandler  extends  AbsShowcaseBsHandler < JoinGroupReqBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( JoinGroupReqHandler . class );

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
	Public  JoinGroupReqHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Class < JoinGroupReqBody >  bodyClass ()  {
		Return  JoinGroupReqBody . class ;
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
	Public  Object  handler ( ShowcasePacket  packet ,  JoinGroupReqBody  bsBody ,  ChannelContext  channelContext )  throws  Exception  {
		Log . info ( "received group request message: {}" ,  Json . toJson ( bsBody ));
		JoinGroupRespBody  joinGroupRespBody  =  new  JoinGroupRespBody ();
		joinGroupRespBody . setCode ( JoinGroupRespBody . Code . SUCCESS );
		joinGroupRespBody . setGroup ( bsBody . getGroup ());

		Aio . bindGroup ( channelContext ,  bsBody . getGroup ());

		ShowcasePacket  respPacket  =  new  ShowcasePacket ();
		respPacket . setType ( the Type . JOIN_GROUP_RESP );
		respPacket . setBody ( Json . toJson ( joinGroupRespBody ). getBytes ( ShowcasePacket . CHARSET ));
