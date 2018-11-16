Package  org . tio . examples . showcase . client . handler .

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Aio ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.JoinGroupRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
Public  class  JoinGroupRespHandler  extends  AbsShowcaseBsHandler < JoinGroupRespBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( JoinGroupRespHandler . class );

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
	Public  JoinGroupRespHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Class < JoinGroupRespBody >  bodyClass ()  {
		Return  JoinGroupRespBody . class ;
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
	Public  Object  handler ( ShowcasePacket  packet ,  JoinGroupRespBody  bsBody ,  ChannelContext  channelContext )  throws  Exception  {
		System . out . println ( "received group response message:"  +  Json . toJson ( bsBody ));

		If  ( JoinGroupRespBody . Code . SUCCESS . equals ( bsBody . getCode ()))  {
			Aio . bindGroup ( channelContext ,  bsBody . getGroup ());
